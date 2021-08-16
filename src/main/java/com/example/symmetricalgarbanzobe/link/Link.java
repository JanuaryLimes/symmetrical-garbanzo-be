package com.example.symmetricalgarbanzobe.link;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Link {
    @Id
    private String shortPath;
    private String originalPath;

    public Link() {
    }

    public Link(String shortPath, String originalPath) {
        this.shortPath = shortPath;
        this.originalPath = originalPath;
    }

    public String getShortPath() {
        return shortPath;
    }

    public void setShortPath(String shortPath) {
        this.shortPath = shortPath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    @Override
    public String toString() {
        return "Link{" +
                "shortPath='" + shortPath + '\'' +
                ", originalPath='" + originalPath + '\'' +
                '}';
    }
}
