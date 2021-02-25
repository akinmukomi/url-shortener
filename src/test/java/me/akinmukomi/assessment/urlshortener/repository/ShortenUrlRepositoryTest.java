package me.akinmukomi.assessment.urlshortener.repository;

import me.akinmukomi.assessment.urlshortener.domain.ShortenUrl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ShortenUrlRepositoryTest {

    @Autowired
    ShortenUrlRepository repository;

    @Autowired
    CacheManager cacheManager;

    private static String shortUrl = "";
    private static String originalUrl =  "";

    @BeforeAll
    static void beforeAll() {
        shortUrl = "http://"+UUID.randomUUID().toString()+".com/a";
        originalUrl  = "https://"+UUID.randomUUID().toString()+".com";
    }

    @Test
    @Order(1)
    public void persistShortenUrl(){
        ShortenUrl shortenUrl = ShortenUrl.builder()
                .id(UUID.randomUUID())
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();

        ShortenUrl url = repository.save(shortenUrl);

        assertNotNull(url);
    }

    @Test
    @Order(2)
    public void retrieveShortUrlFromDb(){
        Optional<ShortenUrl> optionalShortenUrl = repository.findByShortUrlEquals(shortUrl);

        assertTrue(optionalShortenUrl.isPresent());
        assertNotNull(optionalShortenUrl.get());
        assertEquals(optionalShortenUrl.get().getShortUrl(), shortUrl);
        assertEquals(optionalShortenUrl.get().getOriginalUrl(), originalUrl);

    }

    @Test
    @Order(3)
    public void retrieveShortUrlFromCache(){
        Optional<ShortenUrl> optionalShortenUrl = repository.findByShortUrlEquals(shortUrl);

        assertEquals(optionalShortenUrl,getCachedBook(shortUrl) );
    }

    private Optional<ShortenUrl> getCachedBook(String shortUrl) {
        return ofNullable(cacheManager.getCache("shortUrls")).map(c -> c.get(shortUrl, ShortenUrl.class));
    }

}