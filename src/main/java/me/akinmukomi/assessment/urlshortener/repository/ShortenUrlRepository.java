package me.akinmukomi.assessment.urlshortener.repository;

import me.akinmukomi.assessment.urlshortener.domain.ShortenUrl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShortenUrlRepository extends MongoRepository<ShortenUrl , UUID> {

    @Cacheable(value = "shortUrls", key = "#shortUrl")
    public Optional<ShortenUrl> findByShortUrlEquals(String shortUrl);

}
