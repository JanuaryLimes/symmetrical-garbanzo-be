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

    LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(path = "shorten")
    List<Link> getAll() {
        return linkService.getAll();
    }

    @GetMapping(path = "shorten/{shortPath}")
    ResponseEntity<?> getOriginalLink(@PathVariable("shortPath") String shortPath) {
        Link responseLink = linkService.getByShortPath(shortPath);
        if (responseLink != null) {
            return new ResponseEntity<>(responseLink, HttpStatus.OK);
        }
        LinkErrorResponse linkErrorResponse = new LinkErrorResponse();
        linkErrorResponse.setMessage("short link not found");
        return new ResponseEntity<>(linkErrorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "shorten")
    Link register(@RequestBody Link link) {
        String shortPath = RandomStringUtils.randomAlphanumeric(8);

        Link responseLink = linkService.getByOriginalPath(link.getOriginalPath());
        if (responseLink != null) {
            return responseLink;
        }
        responseLink = new Link(shortPath, link.getOriginalPath());
        linkService.addNew(responseLink);
        return responseLink;
    }

    @PostMapping(path = "shorten/label")
    ResponseEntity<?> registerCustom(@RequestBody Link link) {
        Link responseLink = linkService.getByShortPath(link.getShortPath());
        if (responseLink != null) {
            LinkErrorResponse linkErrorResponse = new LinkErrorResponse();
            linkErrorResponse.setMessage("given short link already exists");
            return new ResponseEntity<>(linkErrorResponse, HttpStatus.CONFLICT);
        }
        responseLink = new Link(link.getShortPath(), link.getOriginalPath());
        linkService.addNew(responseLink);
        return new ResponseEntity<>(responseLink, HttpStatus.OK);
    }
}
