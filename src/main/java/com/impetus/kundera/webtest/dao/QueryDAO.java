/**
 * Copyright 2012 Impetus Infotech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.impetus.kundera.webtest.dao;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.impetus.kundera.rest.common.JAXBUtils;
import com.impetus.kundera.rest.common.StreamUtils;
import com.impetus.kundera.rest.converters.CollectionConverter;
import com.impetus.kundera.webtest.common.Constants;
import com.impetus.kundera.webtest.common.Record;
import com.impetus.kundera.webtest.common.Utilities;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class QueryDAO
{
    public Record getRecordForId(String entityClassName, String sessionToken, String primaryKey) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        
        WebResource webResource = (WebResource) mySession.getAttribute(Constants.WEB_RESOURCE);
        
        WebResource.Builder findBuilder = webResource.path("rest").path("kundera/api/crud/" + entityClassName + "/" + primaryKey).accept(MediaType.APPLICATION_XML)
                .header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);       

        ClientResponse findResponse = (ClientResponse) findBuilder.get(ClientResponse.class);

        InputStream is = findResponse.getEntityInputStream();         
        
        Class entityClass = Utilities.clazzMap.get(entityClassName);
        Collection c = new ArrayList();
        
        Object record = JAXBUtils.toObject(is, entityClass, MediaType.APPLICATION_XML);
        if(record != null) {
            c.add(record);
        }            
        
        List<Record> records = populateRecords(entityClass, c);
        if(records != null && ! records.isEmpty()) {
            return records.get(0);
        } else {
            return new Record();
        }
        
    }
    
    
    
    public List<Record> getAllRecords(String entityClassName, String sessionToken) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        
        WebResource webResource = (WebResource) mySession.getAttribute(Constants.WEB_RESOURCE);
        WebResource.Builder queryBuilder = webResource.path("rest").path("kundera/api/query/" + entityClassName + "/all").accept(MediaType.APPLICATION_XML)
        .header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);
        ClientResponse queryResponse = (ClientResponse) queryBuilder.get(ClientResponse.class);
        InputStream is = queryResponse.getEntityInputStream();

        String allRecordsStr = StreamUtils.toString(is);
        Class entityClass = Utilities.clazzMap.get(entityClassName);
        Collection c = CollectionConverter.toCollection(allRecordsStr, ArrayList.class, entityClass, MediaType.APPLICATION_XML);
        
        
        Map<String, String> currentRecord = new HashMap<String, String>();
        
        List<Record> records = populateRecords(entityClass, c);
        return records;
    }
    
    public List<Record> getRecordsForQuery(String sessionToken, String jpaQuery) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        
        WebResource webResource = (WebResource) mySession.getAttribute(Constants.WEB_RESOURCE);
        
        
        WebResource.Builder queryBuilder = webResource.path("rest").path("kundera/api/query/" + jpaQuery).accept(MediaType.APPLICATION_XML)
        .header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);
        
        ClientResponse queryResponse = (ClientResponse) queryBuilder.get(ClientResponse.class);
        
        
        List<Record> records = new ArrayList<Record>();
        if(queryResponse.getStatus() == 200) {
            InputStream is = queryResponse.getEntityInputStream();           
            String allRecordsStr = StreamUtils.toString(is);          
            
            Class entityClass = Utilities.clazzMap.get(Utilities.getClassName(allRecordsStr));
            Collection c = CollectionConverter.toCollection(allRecordsStr, ArrayList.class, entityClass, MediaType.APPLICATION_XML);            
            
            //Map<String, String> currentRecord = new HashMap<String, String>();            
            records = populateRecords(entityClass, c);
        }
        
        
        return records;
    }

    /**
     * @param entityClass
     * @param c
     * @return
     */
    private List<Record> populateRecords(Class entityClass, Collection c)
    {
        List<Record> records = new ArrayList<Record>();
        for(Object o : c) {
            Map<String, String> recordMap = new HashMap<String, String>();
            String pk = "";
            for (Field field : entityClass.getDeclaredFields()) {
                String fieldName = field.getName();
                String fieldValue = Utilities.getFieldValue(field, o);
                
                if(field.getAnnotation(Id.class) != null) {
                    pk = fieldValue;
                }
                recordMap.put(fieldName, fieldValue);                 
                
                //currentRecord.put(fieldName, fieldName + "-AAA");
            }
            
            Record record = new Record();
            record.setRecordMap(recordMap);
            record.setKeys(Utilities.getList(recordMap.keySet()));
            record.setPrimaryKey(pk);            
            
            records.add(record);
        }
        return records;
    }

}
