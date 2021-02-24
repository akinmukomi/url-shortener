package me.akinmukomi.assessment.urlshortener.services;

import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenRequest;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenResponse;
import org.springframework.stereotype.Service;

@Service
public interface UrlShortenerService {

    UrlShortenResponse shortenUrl(UrlShortenRequest request);

    UrlShortenResponse retrieveUrl(String shortUrl);

}
