package com.example.symmetricalgarbanzobe.link;

import javax.persistence.*;

@Entity
@Table
public class Link {
    @Id
    @SequenceGenerator(
            name = "link_sequence",
            sequenceName = "link_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "link_sequence"
    )
    private Long id;
    private String originalPath;
    private String shortPath;

    public Link() {
    }

    public Link(String originalPath, String shortPath) {
        this.originalPath = originalPath;
        this.shortPath = shortPath;
    }

    public Link(Long id, String originalPath, String shortPath) {
        this.id = id;
        this.originalPath = originalPath;
        this.shortPath = shortPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getShortPath() {
        return shortPath;
    }

    public void setShortPath(String shortPath) {
        this.shortPath = shortPath;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", originalPath='" + originalPath + '\'' +
                ", shortPath='" + shortPath + '\'' +
                '}';
    }
}
