package me.akinmukomi.assessment.urlshortener.controllers;

import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortenerControllerTest {

//    @Autowired
//    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shortenUrlWithInvalidUrl() {

        UrlShortenRequest request = UrlShortenRequest.builder().originalUrl("Akinmukomi Oluwaseun").build();

//        mockMvc.perform()
    }

    @Test
    void shortenUrlWithValidUrl() {

        UrlShortenRequest request = UrlShortenRequest.builder().originalUrl("Akinmukomi Oluwaseun").build();

//        mockMvc.perform()
    }

    @Test
    void retrieveOriginalUrl() {
    }

    @Test
    void shortenUrl() {
    }

    @Test
    void testRetrieveOriginalUrl() {
    }
}