package es.udc.fi.dc.photoalbum.util.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.udc.fi.dc.photoalbum.util.utils.ComparableById;

@XmlRootElement(name = "album")
@XmlType(name = "albumType", propOrder = { "id", "name" })
@XmlAccessorType(XmlAccessType.FIELD)
public class AlbumDto implements ComparableById {
    @XmlElement(name = "album-id", required = true)
    private Integer id;
    @XmlElement(required = true)
    private String name;


    public AlbumDto(){
        
    }
    
    public AlbumDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlbumDto [id=" + id + ", name=" + name + "]";
    }
}
