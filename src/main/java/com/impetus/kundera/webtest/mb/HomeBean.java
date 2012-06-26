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
package com.impetus.kundera.webtest.mb;

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
public class HomeBean
{      
    List<Record> records;
    
    //Map<String, String>  currentRecord;
    
    String tableName;
    
    String entityClassName;
    
    
    public String deleteRecord() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");
        String primaryKey = myRequest.getParameter("primaryKey");        
        WebResource webResource = (WebResource)mySession.getAttribute(Constants.WEB_RESOURCE);
        
        String applicationToken = (String) mySession.getAttribute(Constants.APPLICATION_TOKEN);
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session").accept(MediaType.TEXT_PLAIN)
        .header(com.impetus.kundera.rest.common.Constants.APPLICATION_TOKEN_HEADER_NAME, applicationToken);

        String sessionToken = stBuilder.get(String.class);
        
        WebResource.Builder deleteBuilder = webResource.path("rest").path("kundera/api/crud/" + entityClassName + "/delete/" + primaryKey)
                .accept(MediaType.TEXT_PLAIN).header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);
        ClientResponse deleteResponse = (ClientResponse) deleteBuilder.delete(ClientResponse.class);
        
        return showTableDetails();
    }
    
    public String insertRecord() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");
        
        Map<String, String> currentRecord = (Map<String, String>)mySession.getAttribute("currentRecord");
        
        return showTableDetails();
    }
    
    public String showTableDetails() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");
        
        setTableName(tableName);
        setEntityClassName(entityClassName);
        
        WebResource webResource = (WebResource)mySession.getAttribute(Constants.WEB_RESOURCE);
        String applicationToken = (String) mySession.getAttribute(Constants.APPLICATION_TOKEN);
        
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session").accept(MediaType.TEXT_PLAIN)
        .header(com.impetus.kundera.rest.common.Constants.APPLICATION_TOKEN_HEADER_NAME, applicationToken);

        String sessionToken = stBuilder.get(String.class);
        
        
        WebResource.Builder queryBuilder = webResource.path("rest").path("kundera/api/query/" + entityClassName + "/all").accept(MediaType.APPLICATION_XML)
        .header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);
        ClientResponse queryResponse = (ClientResponse) queryBuilder.get(ClientResponse.class);
        InputStream is = queryResponse.getEntityInputStream();

        String allBookStr = StreamUtils.toString(is);
        Class entityClass = Utilities.clazzMap.get(entityClassName);
        Collection c = CollectionConverter.toCollection(allBookStr, ArrayList.class, entityClass, MediaType.APPLICATION_XML);
        
        
        Map<String, String> currentRecord = new HashMap<String, String>();
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
                
                currentRecord.put(fieldName, fieldName + "-AAA");
            }
            
            Record record = new Record();
            record.setRecordMap(recordMap);
            record.setKeys(Utilities.getList(recordMap.keySet()));
            record.setPrimaryKey(pk);            
            
            getRecords().add(record);
        }     
        
        mySession.setAttribute("currentRecord", currentRecord);
        
        String response = stBuilder.delete(String.class);
        return "showTableDetails";        
    }   
    
    /**
     * @return the tableName
     */
    public String getTableName()
    {
        return tableName;
    }
    
    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }   
    

    /**
     * @return the entityClassName
     */
    public String getEntityClassName()
    {
        return entityClassName;
    }

    /**
     * @param entityClassName the entityClassName to set
     */
    public void setEntityClassName(String entityClassName)
    {
        this.entityClassName = entityClassName;
    }

    /**
     * @return the records
     */
    public List<Record> getRecords()
    {
        if(records == null) {
            records = new ArrayList<Record>();
        }
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(List<Record> records)
    {
        this.records = records;
    }

   /* *//**
     * @return the currentRecord
     *//*
    public Map<String, String> getCurrentRecord()
    {
        if(currentRecord == null) {
            currentRecord = new HashMap<String, String>();
        }
        return currentRecord;
    }

    *//**
     * @param currentRecord the currentRecord to set
     *//*
    public void setCurrentRecord(Map<String, String> currentRecord)
    {
        this.currentRecord = currentRecord;
    }  
    */
    
    
}
