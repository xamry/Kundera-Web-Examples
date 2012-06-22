package com.impetus.kundera.webtest.dao;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import dto.SchemaDTO;

public class HomeDAO
{
    
    public List<SchemaDTO> getSchemaDTOList(WebResource wr, String pu) {
        System.out.println("\n\nGetting Schema List for PU :" + pu);
        WebResource.Builder slBuilder = wr.path("kundera/api/metadata/schemaList/" + pu)
                .accept(MediaType.TEXT_PLAIN);
        String slResponse = slBuilder.get(ClientResponse.class).toString();
        String schemaList = slBuilder.get(String.class);
        System.out.println("Response: " + slResponse);
        System.out.println("Schema List:" + schemaList);
        
        SchemaDTO schemaDTO = new SchemaDTO();
        schemaDTO.setSchemaName(schemaList);
        
        return null;
    }

}
