package me.akinmukomi.assessment.urlshortener.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UniqueUrlGenerator {

    @Value("${keyLength:7}")
    private int keyLength;

    @Value("${baseUrl:http://localhost:8080/url-shortener}")
    private String baseUrl;

    public String generateShortUniqueUrl(){
        UniqueKeyGenerator uniqueKeyGenerator = new UniqueKeyGenerator(keyLength);
        String shortUrlKey = uniqueKeyGenerator.generateUniqueKey();

        return new StringBuilder().append(baseUrl).append("/").append(shortUrlKey).toString();
    }

    public String generateShortUrlFromKey(String shortUrlKey){
        return new StringBuilder().append(baseUrl).append("/").append(shortUrlKey).toString();
    }
}
