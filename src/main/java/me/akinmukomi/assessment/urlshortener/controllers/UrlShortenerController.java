package me.akinmukomi.assessment.urlshortener.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenRequest;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenResponse;
import me.akinmukomi.assessment.urlshortener.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/url-shortener")
@Api(value = "URL Shortener APIs", tags =  "URL Shortener APIs")
public class UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @ApiOperation(value = "Shorten Url" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully generated a short url for {Original URL}", response = UrlShortenResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = UrlShortenResponse.class)
    })
    @PostMapping(value = "" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity shortenUrl(@RequestBody @Valid UrlShortenRequest request){

        UrlShortenResponse response = urlShortenerService.shortenUrl(request);

        return new ResponseEntity(response, OK);
    }

    @ApiOperation(value = "Retrieve Original URL" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 301, message = "{User is redirected to Original URL}"),
            @ApiResponse(code = 400, message = "Bad request", response = UrlShortenResponse.class),
            @ApiResponse(code = 404, message = "Short URL provided is not valid", response = UrlShortenResponse.class)
    })
    @GetMapping(value = "/{shortUrlKey}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity retrieveOriginalUrl(@PathVariable(value = "shortUrlKey") String shortUrlKey){

        UrlShortenResponse response = urlShortenerService.retrieveUrl(shortUrlKey);

        return ResponseEntity.status((response.getOriginalUrl() != null) ? MOVED_PERMANENTLY : NOT_FOUND).header(HttpHeaders.LOCATION, response.getOriginalUrl()).build();
    }

}
