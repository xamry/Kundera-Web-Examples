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

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class LoginDAO
{
    
    public String getApplicationToken(WebResource webResource, String pu)
    {
        System.out.println("\n\nGetting Application Token...");
        WebResource.Builder atBuilder = webResource.path("rest").path("kundera/api/application/" + pu)
                .accept(MediaType.TEXT_PLAIN);
        String atResponse = atBuilder.get(ClientResponse.class).toString();
        String applicationToken = atBuilder.get(String.class);
        System.out.println("Response: " + atResponse);
        System.out.println("Application Token:" + applicationToken);
        return applicationToken;
    }

}
