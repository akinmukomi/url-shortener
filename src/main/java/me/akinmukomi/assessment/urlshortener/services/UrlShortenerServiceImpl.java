package me.akinmukomi.assessment.urlshortener.services;

import lombok.extern.slf4j.Slf4j;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenRequest;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenResponse;
import me.akinmukomi.assessment.urlshortener.domain.ShortenUrl;
import me.akinmukomi.assessment.urlshortener.repository.ShortenUrlRepository;
import me.akinmukomi.assessment.urlshortener.utils.UniqueUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    ShortenUrlRepository shortenUrlRepository;

    @Autowired
    UniqueUrlGenerator uniqueUrlGenerator;

    @Override
    public UrlShortenResponse shortenUrl(@Valid UrlShortenRequest request) {

        String shortUniqueUrl = null;
        Optional<ShortenUrl> optionalShortenUrl = null;
        do{
            shortUniqueUrl = uniqueUrlGenerator.generateShortUniqueUrl();
            optionalShortenUrl = shortenUrlRepository.findByShortUrlEquals(shortUniqueUrl);

        }while(optionalShortenUrl.isPresent());

        ShortenUrl shortenUrl = ShortenUrl.builder()
                .id(UUID.randomUUID())
                .createdDate(new Date())
                .originalUrl(request.getOriginalUrl())
                .shortUrl(shortUniqueUrl)
                .build();

        shortenUrlRepository.save(shortenUrl);

        return UrlShortenResponse.builder().shortUrl(shortUniqueUrl).message("Successfully generated a short url for "+request.getOriginalUrl()).build();
    }

    @Override
    public UrlShortenResponse retrieveUrl(@NotEmpty(message = "ShortUrl must be provided") String shortUrlKey) {

        String shortUrl = uniqueUrlGenerator.generateShortUrlFromKey(shortUrlKey);

        Optional<ShortenUrl> optionalShortenUrl = shortenUrlRepository.findByShortUrlEquals(shortUrl);
        if(!optionalShortenUrl.isPresent()){
            return UrlShortenResponse.builder().message("No URL exists with provided shortUrl ").build();
        }
        return UrlShortenResponse.builder().originalUrl(optionalShortenUrl.get().getOriginalUrl()).build();
    }
}
