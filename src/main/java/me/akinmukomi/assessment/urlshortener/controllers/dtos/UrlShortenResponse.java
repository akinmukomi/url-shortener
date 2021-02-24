package me.akinmukomi.assessment.urlshortener.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlShortenResponse {

    private String shortUrl;

    private String originalUrl;

    private String message;

    private List<String> errors;

}
