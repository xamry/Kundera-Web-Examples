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

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.impetus.kundera.webtest.common.Constants;
import com.impetus.kundera.webtest.dao.HomeDAO;
import com.sun.jersey.api.client.WebResource;

import dto.SchemaDTO;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class HomeBean
{
    
    private List<SchemaDTO> schemaDTOList;

    /**
     * @return the schemaDTOList
     */
    public List<SchemaDTO> getSchemaDTOList()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        String pu = (String) session.getAttribute(Constants.PERSISTENCE_UNIT);
        WebResource wr = (WebResource) session.getAttribute(Constants.WEB_RESOURCE);
        schemaDTOList = new HomeDAO().getSchemaDTOList(wr, pu);
        
        return schemaDTOList;
    }

    /**
     * @param schemaDTOList the schemaDTOList to set
     */
    public void setSchemaDTOList(List<SchemaDTO> schemaDTOList)
    {
        this.schemaDTOList = schemaDTOList;
    }   

}
