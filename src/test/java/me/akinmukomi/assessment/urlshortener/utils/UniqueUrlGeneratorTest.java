package me.akinmukomi.assessment.urlshortener.utils;

import me.akinmukomi.assessment.urlshortener.utils.UniqueUrlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UniqueUrlGeneratorTest {

    @Autowired
    UniqueUrlGenerator uniqueUrlGenerator;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateShortUniqueUrlSuccessfully() {

        String uniqueUrl = uniqueUrlGenerator.generateShortUniqueUrl();

        assertNotNull(uniqueUrl);

    }

    @Test
    void generateMultipleShortUniqueUrlSuccessfully() {
        int size = 4;
        Set<String> uniqueKeys = new HashSet<>();

        for(int i = 1; i <= 4 ; i++){
            uniqueKeys.add(uniqueUrlGenerator.generateShortUniqueUrl());
        }
        assertEquals(size , uniqueKeys.size());
    }

    @Test
    void generateShortUrlFromKeySuccessfully() {

        String url = uniqueUrlGenerator.generateShortUrlFromKey(UUID.randomUUID().toString());

        assertNotNull(url);

    }
}