package me.akinmukomi.assessment.urlshortener.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("shorten_url")
public class ShortenUrl implements Serializable {

    @Id
    private UUID id;

    @URL
    private String originalUrl;

    @URL
    @Indexed(unique = true)
    private String shortUrl;

    private Date createdDate;

}
