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
package com.impetus.kundera.webtest.common;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import com.impetus.kundera.property.PropertyAccessException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class Utilities
{
    
    public static Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>();
    
    public static WebResource getWebResource(String url) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        URI uri = UriBuilder.fromUri(url).build();
        WebResource webResource = client.resource(uri);   
        return webResource;
    }
    
    public static String getFieldValue(Field field, Object o) {
        if (!field.isAccessible())
        {
            field.setAccessible(true);
        }

        try
        {
            return field.get(o).toString();
        }
        catch (IllegalArgumentException iarg)
        {
            throw new PropertyAccessException(iarg);
        }
        catch (IllegalAccessException iacc)
        {
            throw new PropertyAccessException(iacc);
        }
    }
    
    /**
     * @return
     */
    public static List<String> getList(Set<String> s)
    {
        List l = new ArrayList<String>();
        for(String key : s) {
            l.add(key);
        }
        return l;
    }

}
