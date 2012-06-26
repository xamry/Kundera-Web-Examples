/*******************************************************************************
 * * Copyright 2012 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.kundera.webtest.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author amresh
 *
 */
public class Record
{
    private String primaryKey;
    
    private List<String> keys;
    
    private Map<String, String> recordMap;   

    /**
     * @return the keys
     */
    public List<String> getKeys()
    {        
        return Utilities.getList(getRecordMap().keySet());
    }
    

    /**
     * @param keys the keys to set
     */
    public void setKeys(List<String> keys)
    {
        this.keys = keys;
    }

    /**
     * @return the recordMap
     */
    public Map<String, String> getRecordMap()
    {
        if(recordMap == null) {
            recordMap = new HashMap<String, String>();
        }
        return recordMap;
    }

    /**
     * @param recordMap the recordMap to set
     */
    public void setRecordMap(Map<String, String> recordMap)
    {
        this.recordMap = recordMap;
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

}
