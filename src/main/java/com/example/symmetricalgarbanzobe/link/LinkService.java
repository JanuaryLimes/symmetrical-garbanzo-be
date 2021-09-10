package com.example.symmetricalgarbanzobe.link;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getAll() {
        return linkRepository.findAll();
    }

    public Link getByShortPath(String shortPath) {
        return linkRepository.findByShortPath(shortPath);
    }

    public Link getByOriginalPath(String original) {
        return linkRepository.findByOriginalPath(original);
    }

    public void addNew(Link link) {
        linkRepository.save(link);
    }
}
