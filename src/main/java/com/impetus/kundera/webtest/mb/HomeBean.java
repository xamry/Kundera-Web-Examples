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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.impetus.kundera.webtest.common.Constants;
import com.impetus.kundera.webtest.common.Record;
import com.impetus.kundera.webtest.dao.QueryDAO;
import com.impetus.kundera.webtest.dao.SessionDAO;
import com.impetus.kundera.webtest.entities.Book;
import com.impetus.kundera.webtest.entities.Song;
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
    
    String jpaQuery;
    
    String primaryKey;
    
    String bookIsbn;
    String bookAuthor;
    String bookPublication;
    
    String songId;
    String songTitle;
    String songArtist;
    String songAlbum; 
    
    
    public String deleteRecord() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");
        String primaryKey = getPrimaryKey();        
        WebResource webResource = (WebResource)mySession.getAttribute(Constants.WEB_RESOURCE);
        
        String applicationToken = (String) mySession.getAttribute(Constants.APPLICATION_TOKEN);
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session").accept(MediaType.TEXT_PLAIN)
        .header(com.impetus.kundera.rest.common.Constants.APPLICATION_TOKEN_HEADER_NAME, applicationToken);

        String sessionToken = stBuilder.get(String.class);
        
        WebResource.Builder deleteBuilder = webResource.path("rest").path("kundera/api/crud/" + entityClassName + "/delete/" + primaryKey)
                .accept(MediaType.TEXT_PLAIN).header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);
        ClientResponse deleteResponse = (ClientResponse) deleteBuilder.delete(ClientResponse.class);
        
        return "showTableDetails";
    }
    
    public String insertRecord() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");
        
        Map<String, String> currentRecord = (Map<String, String>)mySession.getAttribute("currentRecord");
        
        return "showTableDetails";
    }
    
    public String runJPAQuery() {    
        
        String jpaQuery = getJpaQuery();
        
        String sessionToken = new SessionDAO().getSessionToken();
        
        getRecords().addAll(new QueryDAO().getRecordsForQuery(sessionToken, jpaQuery));
            
        new SessionDAO().closeSession(sessionToken);
        
        return "showTableDetails";
    }
    
    public String findById() {        
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");   
        
        String primaryKey = getPrimaryKey();
        
        if(!StringUtils.isBlank(primaryKey)) {
            primaryKey = primaryKey.trim();
        } else {
            return "showTableDetails";   
        }
        
        String sessionToken = new SessionDAO().getSessionToken();
        
        getRecords().add(new QueryDAO().getRecordForId(entityClassName, sessionToken, primaryKey));
            
        new SessionDAO().closeSession(sessionToken);
        
        return "showTableDetails";    
    }
    
    public String showTableDetails() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        
        String entityClassName = myRequest.getParameter("entityClass");
        String tableName = myRequest.getParameter("table");
        
        setTableName(tableName);
        setEntityClassName(entityClassName);  
        
        String sessionToken = new SessionDAO().getSessionToken();
        
        getRecords().addAll(new QueryDAO().getAllRecords(entityClassName, sessionToken));
            
        new SessionDAO().closeSession(sessionToken);
        
        //mySession.setAttribute("currentRecord", currentRecord);
        
        
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

    /**
     * @return the jpaQuery
     */
    public String getJpaQuery()
    {
        return jpaQuery;
    }

    /**
     * @param jpaQuery the jpaQuery to set
     */
    public void setJpaQuery(String jpaQuery)
    {
        this.jpaQuery = jpaQuery;
    }
    
    /**
     * @return the primaryKey
     */
    public String getPrimaryKey()
    {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(String primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the bookIsbn
     */
    public String getBookIsbn()
    {
        return bookIsbn;
    }

    /**
     * @param bookIsbn the bookIsbn to set
     */
    public void setBookIsbn(String bookIsbn)
    {
        this.bookIsbn = bookIsbn;
    }

    /**
     * @return the bookAuthor
     */
    public String getBookAuthor()
    {
        return bookAuthor;
    }

    /**
     * @param bookAuthor the bookAuthor to set
     */
    public void setBookAuthor(String bookAuthor)
    {
        this.bookAuthor = bookAuthor;
    }

    /**
     * @return the bookPublication
     */
    public String getBookPublication()
    {
        return bookPublication;
    }

    /**
     * @param bookPublication the bookPublication to set
     */
    public void setBookPublication(String bookPublication)
    {
        this.bookPublication = bookPublication;
    }

    /**
     * @return the songId
     */
    public String getSongId()
    {
        return songId;
    }

    /**
     * @param songId the songId to set
     */
    public void setSongId(String songId)
    {
        this.songId = songId;
    }

    /**
     * @return the songTitle
     */
    public String getSongTitle()
    {
        return songTitle;
    }

    /**
     * @param songTitle the songTitle to set
     */
    public void setSongTitle(String songTitle)
    {
        this.songTitle = songTitle;
    }

    /**
     * @return the songArtist
     */
    public String getSongArtist()
    {
        return songArtist;
    }

    /**
     * @param songArtist the songArtist to set
     */
    public void setSongArtist(String songArtist)
    {
        this.songArtist = songArtist;
    }

    /**
     * @return the songAlbum
     */
    public String getSongAlbum()
    {
        return songAlbum;
    }

    /**
     * @param songAlbum the songAlbum to set
     */
    public void setSongAlbum(String songAlbum)
    {
        this.songAlbum = songAlbum;
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
