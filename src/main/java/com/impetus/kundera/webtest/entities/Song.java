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
package com.impetus.kundera.webtest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */

@Entity
@Table(name="SONG", schema="KunderaExamples@twissandra")
@XmlRootElement
public class Song
{
    @Id
    @Column(name="ID")
    private int id;
    
    @Column(name="TITLE")
    private String title;
    
    @Column(name="ARTIST")
    private String artist;
    
    @Column(name="ALBUM")
    private String album;

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the artist
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    /**
     * @return the album
     */
    public String getAlbum()
    {
        return album;
    }

    /**
     * @param album the album to set
     */
    public void setAlbum(String album)
    {
        this.album = album;
    }  

}
