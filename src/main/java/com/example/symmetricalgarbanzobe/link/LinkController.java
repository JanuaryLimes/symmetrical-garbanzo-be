package com.example.symmetricalgarbanzobe.link;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        Link responseLink = linkService.getByOriginalPath(link.getOriginalPath());
        if (responseLink != null) {
            return responseLink;
        }
        responseLink = new Link(linkService.generateShortPath(), link.getOriginalPath());
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

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<LinkErrorResponse> handleNullPointerException(NullPointerException e) {
        LinkErrorResponse error = new LinkErrorResponse();
        error.setMessage(e.getMessage());
        log.warn("originalPath is marked non-null but is null");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
