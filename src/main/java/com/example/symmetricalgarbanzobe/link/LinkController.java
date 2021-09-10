package com.example.symmetricalgarbanzobe.link;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/link")
class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(path = "shorten")
    public List<Link> getLinks() {
        return linkService.getLinks();
    }

    @GetMapping(path = "shorten/{shortPath}")
    public ResponseEntity<?> getOriginalLink(@PathVariable("shortPath") String shortPath) {
        Link responseLink = linkService.getLinkByShortPath(shortPath);
        if (responseLink != null) {
            return new ResponseEntity<>(responseLink, HttpStatus.OK);
        }
        LinkErrorResponse linkErrorResponse = new LinkErrorResponse();
        linkErrorResponse.setMessage("short link not found");
        return new ResponseEntity<>(linkErrorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "shorten")
    public Link registerNewLink(@RequestBody Link link) {
        String shortPath = RandomStringUtils.randomAlphanumeric(8);

        Link responseLink = linkService.getLinkByOriginalPath(link.getOriginalPath());
        if (responseLink != null) {
            return responseLink;
        }
        responseLink = new Link(shortPath, link.getOriginalPath());
        linkService.addNewLink(responseLink);
        return responseLink;
    }

    @PostMapping(path = "shorten/label")
    public ResponseEntity<?> registerCustomLink(@RequestBody Link link) {
        Link responseLink = linkService.getLinkByShortPath(link.getShortPath());
        if (responseLink != null) {
            LinkErrorResponse linkErrorResponse = new LinkErrorResponse();
            linkErrorResponse.setMessage("given short link already exists");
            return new ResponseEntity<>(linkErrorResponse, HttpStatus.CONFLICT);
        }
        responseLink = new Link(link.getShortPath(), link.getOriginalPath());
        linkService.addNewLink(responseLink);
        return new ResponseEntity<>(responseLink, HttpStatus.OK);
    }
}
