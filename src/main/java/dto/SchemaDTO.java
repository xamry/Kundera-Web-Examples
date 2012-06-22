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
package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class SchemaDTO
{
    private String schemaName;
    
    private List<TableDTO> tables;

    /**
     * @return the schemaName
     */
    public String getSchemaName()
    {
        return schemaName;
    }

    /**
     * @param schemaName the schemaName to set
     */
    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    /**
     * @return the tables
     */
    public List<TableDTO> getTables()
    {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(List<TableDTO> tables)
    {
        this.tables = tables;
    }  
    
    public void addTable(TableDTO tableDTO) {
        if(tables == null) {
            tables = new ArrayList<TableDTO>();
        }
        tables.add(tableDTO);
    }
    
    

}
