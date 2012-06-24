package com.impetus.kundera.webtest.dao;

import java.io.InputStream;

import com.impetus.kundera.rest.common.JAXBUtils;
import com.impetus.kundera.rest.dto.SchemaMetadata;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class HomeDAO
{
    
    public SchemaMetadata getSchemaMetadata(WebResource wr, String pu, String mediaType) {
        System.out.println("\n\nGetting Schema Metadata for PUs :" + pu);
        WebResource.Builder slBuilder = wr.path("rest").path("kundera/api/metadata/schemaList/" + pu)
                .accept(mediaType);
        
        ClientResponse schemaResponse = (ClientResponse)slBuilder.get(ClientResponse.class);
        
        InputStream is = schemaResponse.getEntityInputStream();
        //String schemaList = StreamUtils.toString(is);  
        
        //System.out.println("Schema List:" + schemaList);
        
        SchemaMetadata sm = (SchemaMetadata)JAXBUtils.toObject(is, SchemaMetadata.class, mediaType);        
        return sm;
    }

}
