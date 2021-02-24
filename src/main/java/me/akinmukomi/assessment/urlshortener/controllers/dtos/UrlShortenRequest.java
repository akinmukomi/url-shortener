package me.akinmukomi.assessment.urlshortener.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlShortenRequest {

    @NotEmpty(message = "Original URL must be provided")
    @URL(message = "Valid URL must be provided")
    private String originalUrl;

}
