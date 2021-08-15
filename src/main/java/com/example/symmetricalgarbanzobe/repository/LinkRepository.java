package com.example.symmetricalgarbanzobe.repository;

import com.example.symmetricalgarbanzobe.link.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    /*@Query("select l from Link l where l.originalPath = ?1")
    Optional<Link> findLinksByOriginalPath(String originalPath);*/

}