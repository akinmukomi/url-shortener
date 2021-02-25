package me.akinmukomi.assessment.urlshortener.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenRequest;
import me.akinmukomi.assessment.urlshortener.controllers.dtos.UrlShortenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "NDcxZTQyNDItNDBlZC00OTE4LTk2ZGUtMGM3YjEwZmI1M2Mw", roles = "ADMIN")
class UrlShortenerControllerTest {

    private final String originalUrl = "https://carbon.bamboohr.com/jobs/view.php?id=65&source=getcarbon";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shortenUrlWithInvalidUrl() throws Exception {

        UrlShortenRequest request = UrlShortenRequest.builder().originalUrl("Akinmukomi Oluwaseun").build();

         mockMvc.perform(
                            post("/url-shortener")
                            .content(objectMapper.writeValueAsBytes(request))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                        ).andDo(print())
                        .andExpect(status().isBadRequest());

    }

    @Test
    void shortenUrlWithValidUrl() throws Exception {
        UrlShortenRequest request = UrlShortenRequest.builder().originalUrl(originalUrl).build();

        mockMvc.perform(
                    post("/url-shortener")
                            .content(objectMapper.writeValueAsBytes(request))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl").isNotEmpty());
    }

    @Test
    void retrieveOriginalUrl() throws Exception {
        UrlShortenRequest request = UrlShortenRequest.builder().originalUrl(originalUrl).build();

        MvcResult mvcResult = mockMvc.perform(
                post("/url-shortener")
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl").isNotEmpty())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        UrlShortenResponse urlShortenResponse = objectMapper.readValue(contentAsString, UrlShortenResponse.class);
        String shortUrl = urlShortenResponse.getShortUrl();

        mockMvc.perform(get(shortUrl)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isMovedPermanently())
                .andExpect(redirectedUrl(originalUrl));
    }
}