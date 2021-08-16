package com.example.symmetricalgarbanzobe.repository;

import com.example.symmetricalgarbanzobe.link.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("select link from Link link where link.originalPath = ?1")
    Link findLinkByOriginalPath(String originalPath);

    @Query("select link from Link link where link.shortPath = ?1")
    Link findLinkByShortPath(String shortPath);
}