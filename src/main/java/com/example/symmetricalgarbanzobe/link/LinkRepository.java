package com.example.symmetricalgarbanzobe.link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LinkRepository extends JpaRepository<Link, Long> {

    Link findByOriginalPath(String originalPath);

    Link findByShortPath(String shortPath);
}