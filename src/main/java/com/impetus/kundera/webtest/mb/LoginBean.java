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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.impetus.kundera.rest.dto.SchemaMetadata;
import com.impetus.kundera.webtest.common.Constants;
import com.impetus.kundera.webtest.common.Utilities;
import com.impetus.kundera.webtest.dao.HomeDAO;
import com.impetus.kundera.webtest.dao.LoginDAO;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * <Prove description of functionality provided by this Type>
 * 
 * @author amresh.singh
 */
public class LoginBean
{
    private String webServiceURL;

    private String mediaType;

    private String persistenceUnits;

    /**
     * @return the webServiceURL
     */
    public String getWebServiceURL()
    {
        return webServiceURL;
    }

    /**
     * @param webServiceURL
     *            the webServiceURL to set
     */
    public void setWebServiceURL(String webServiceURL)
    {
        this.webServiceURL = webServiceURL;
    }

    /**
     * @return the mediaType
     */
    public String getMediaType()
    {
        return mediaType;
    }

    /**
     * @param mediaType
     *            the mediaType to set
     */
    public void setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
    }

    /**
     * @return the persistenceUnits
     */
    public String getPersistenceUnits()
    {
        return persistenceUnits;
    }

    /**
     * @param persistenceUnits
     *            the persistenceUnits to set
     */
    public void setPersistenceUnits(String persistenceUnits)
    {
        this.persistenceUnits = persistenceUnits;
    }

    public String authenticate()
    {
        String outcome = null;

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        // Validates Parameters
        if (StringUtils.isBlank(getWebServiceURL()))
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please enter Web Service URL"));
            outcome = Constants.OUTCOME_LOGIN_FAILED;
        }

        if (StringUtils.isBlank(getPersistenceUnits()))
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please enter persistence unit(s)"));
            outcome = Constants.OUTCOME_LOGIN_FAILED;
        }

        if (StringUtils.isNotBlank(outcome))
        {
            return outcome;
        }
        else
        {

            WebResource wr = Utilities.getWebResource(getWebServiceURL());            
            String at = new LoginDAO().getApplicationToken(wr, getPersistenceUnits());    
            session.setAttribute(Constants.APPLICATION_TOKEN, at);
            session.setAttribute(Constants.PERSISTENCE_UNIT, getPersistenceUnits());
            session.setAttribute(Constants.WEB_RESOURCE, wr);
            
            SchemaMetadata schemaMetadata = (SchemaMetadata)session.getAttribute(Constants.SCHEMA_META_DATA);
            if(schemaMetadata == null) {
                
                schemaMetadata = new HomeDAO().getSchemaMetadata(wr, getPersistenceUnits(), MediaType.APPLICATION_XML);
                session.setAttribute(Constants.SCHEMA_META_DATA, schemaMetadata);
            }   
            
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Application Token: " + at));            
            
            outcome = Constants.OUTCOME_LOGIN_SUCCESSFUL;

            return outcome;
        }
    }

    /*public String logOff()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate(); // Invalidate session

        // Close Application

        // TODO: Stop cassandra server if started

        return "loggedOff";
    }*/
    
    

}
