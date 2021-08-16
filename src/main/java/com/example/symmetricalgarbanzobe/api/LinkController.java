package com.example.symmetricalgarbanzobe.api;

import com.example.symmetricalgarbanzobe.link.Link;
import com.example.symmetricalgarbanzobe.repository.LinkRepository;
import com.example.symmetricalgarbanzobe.service.LinkService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/link")
public class LinkController {

    private final LinkService linkService;
    private final LinkRepository linkRepository;

    @Autowired
    public LinkController(LinkService linkService, LinkRepository linkRepository) {
        this.linkService = linkService;
        this.linkRepository = linkRepository;
    }

    @GetMapping
    public List<Link> getLinks() {
        return linkService.getLinks();
    }

    @PostMapping(path = "shorten")
    public Link registerNewLink(@RequestBody Link link) {
        String shortPath = RandomStringUtils.randomAlphanumeric(8);

        Link responseLink = linkRepository.findLinkByOriginalPath(link.getOriginalPath());
        if (responseLink != null) {
            return responseLink;
        }
        responseLink = new Link(shortPath, link.getOriginalPath());
        linkService.addNewLink(responseLink);
        return responseLink;
    }

    @DeleteMapping(path = "{linkId}")
    public void deleteLink(@PathVariable("linkId") Long linkId) {
        linkService.deleteLink(linkId);
    }
}
