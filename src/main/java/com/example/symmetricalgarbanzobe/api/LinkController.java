package com.example.symmetricalgarbanzobe.api;

import com.example.symmetricalgarbanzobe.link.Link;
import com.example.symmetricalgarbanzobe.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/link")
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public List<Link> getLinks() {
        return linkService.getLinks();
    }

    @PostMapping
    public void registerNewLink(@RequestBody Link link) {
        linkService.addNewLink(link);
    }

    @DeleteMapping(path = "{linkId}")
    public void deleteLink(@PathVariable("linkId") Long linkId) {
        linkService.deleteLink(linkId);
    }
}
