package com.example.symmetricalgarbanzobe.service;

import com.example.symmetricalgarbanzobe.link.Link;
import com.example.symmetricalgarbanzobe.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public void addNewLink(Link link) {
        linkRepository.save(link);
    }

    public void deleteLink(Long linkId) {
        boolean exists = linkRepository.existsById(linkId);
        if (!exists) {
            throw new IllegalStateException("link with id" + linkId + " doesnt exists");
        }
        linkRepository.deleteById(linkId);
    }
}
