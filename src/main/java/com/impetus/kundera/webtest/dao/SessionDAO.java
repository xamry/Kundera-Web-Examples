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

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.impetus.kundera.webtest.common.Constants;
import com.sun.jersey.api.client.WebResource;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class SessionDAO
{
    public String getSessionToken() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        
        WebResource webResource = (WebResource)mySession.getAttribute(Constants.WEB_RESOURCE);
        String applicationToken = (String) mySession.getAttribute(Constants.APPLICATION_TOKEN);
        
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session").accept(MediaType.TEXT_PLAIN)
        .header(com.impetus.kundera.rest.common.Constants.APPLICATION_TOKEN_HEADER_NAME, applicationToken);
        String sessionToken = stBuilder.get(String.class);
        
        return sessionToken;
    }
    
    public String closeSession(String sessionToken) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        
        WebResource webResource = (WebResource)mySession.getAttribute(Constants.WEB_RESOURCE);
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session").accept(MediaType.TEXT_PLAIN)
        .header(com.impetus.kundera.rest.common.Constants.SESSION_TOKEN_HEADER_NAME, sessionToken);;
        
        String response = stBuilder.delete(String.class);
        return response;
    }

}
