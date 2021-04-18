package com.kirilo.ws;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "tutorial")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlValue
    private String name;

    @XmlAttribute
    private int id;

    @XmlAttribute
    private String availableSince;

    @XmlAttribute
    private boolean available = false;

    public Item() {
    }

    public Item(String name, int id, String availableSince, boolean available) {
        this.name = name;
        this.id = id;
        this.availableSince = availableSince;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvailableSince() {
        return availableSince;
    }

    public void setAvailableSince(String availableSince) {
        this.availableSince = availableSince;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
