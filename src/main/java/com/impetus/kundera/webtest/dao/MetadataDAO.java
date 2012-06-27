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

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.impetus.kundera.rest.common.JAXBUtils;
import com.impetus.kundera.rest.dto.SchemaMetadata;
import com.impetus.kundera.webtest.common.Constants;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class MetadataDAO
{
    
    public SchemaMetadata getSchemaMetadata(String pu, String mediaType) {
        System.out.println("\n\nGetting Schema Metadata for PUs :" + pu);
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession mySession = myRequest.getSession(); 
        
        WebResource wr = (WebResource)mySession.getAttribute(Constants.WEB_RESOURCE);
        
        WebResource.Builder builder = wr.path("rest").path("kundera/api/metadata/schemaList/" + pu)
                .accept(mediaType);
        
        ClientResponse schemaResponse = (ClientResponse)builder.get(ClientResponse.class);
        
        InputStream is = schemaResponse.getEntityInputStream();
        //String schemaList = StreamUtils.toString(is);  
        
        //System.out.println("Schema List:" + schemaList);
        
        SchemaMetadata sm = (SchemaMetadata)JAXBUtils.toObject(is, SchemaMetadata.class, mediaType);        
        return sm;
    }

}
