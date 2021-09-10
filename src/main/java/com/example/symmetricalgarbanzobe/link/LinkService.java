package com.example.symmetricalgarbanzobe.link;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public Link getLinkByShortPath(String shortPath) {
        return linkRepository.findLinkByShortPath(shortPath);
    }

    public Link getLinkByOriginalPath(String original) {
        return linkRepository.findLinkByOriginalPath(original);
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
