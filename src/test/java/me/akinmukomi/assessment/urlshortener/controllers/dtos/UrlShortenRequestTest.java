package me.akinmukomi.assessment.urlshortener.controllers.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class UrlShortenRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkUrlValidationSuccessfulInRequest(){

        UrlShortenRequest urlShortenRequest = UrlShortenRequest.builder().originalUrl("https:github.com").build();

        Set<ConstraintViolation<UrlShortenRequest>> constraintViolations = validator.validate(urlShortenRequest);

        assertEquals(0 , constraintViolations.size());

    }

    @Test
    public void checkUrlValidationFailedInRequest(){

        UrlShortenRequest urlShortenRequest = UrlShortenRequest.builder().originalUrl("Akinmukomi Oluwaseun").build();

        Set<ConstraintViolation<UrlShortenRequest>> constraintViolations = validator.validate(urlShortenRequest);

        assertEquals(1 , constraintViolations.size());

    }
}