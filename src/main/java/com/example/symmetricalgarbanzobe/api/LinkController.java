package com.example.symmetricalgarbanzobe.api;

import com.example.symmetricalgarbanzobe.exception.ErrorResponse;
import com.example.symmetricalgarbanzobe.link.Link;
import com.example.symmetricalgarbanzobe.repository.LinkRepository;
import com.example.symmetricalgarbanzobe.service.LinkService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(path = "shorten/label")
    public ResponseEntity<?> registerCustomLink(@RequestBody Link link) {
        Link responseLink = linkRepository.findLinkByShortPath(link.getShortPath());
        if (responseLink != null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("given short link already exists");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
        responseLink = new Link(link.getShortPath(), link.getOriginalPath());
        linkService.addNewLink(responseLink);
        return new ResponseEntity<>(responseLink, HttpStatus.OK);
    }
}
