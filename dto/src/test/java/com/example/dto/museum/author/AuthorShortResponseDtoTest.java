package com.example.dto.museum.author;

import com.example.dto.config.AbstractDtoTest;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorShortResponseDtoTest extends AbstractDtoTest {

    @ParameterizedTest
    @InstancioSource
    void shouldPass(AuthorShortResponse authorShortResponse) {
        var violations = validate(authorShortResponse);

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void invalidTextFields(String value) {
        var fields = Stream.of("username", "userFirstName", "userLastName").toList();
        fields.stream()
                .map(field -> getAuthorShortResponse(field, value))
                .map(this::validate)
                .map(Set::isEmpty)
                .forEach(Assertions::assertFalse);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-100L, 0L})
    void invalidId(Long value) {
        var userShortResponse = getAuthorShortResponse(value);
        var violations = validate(userShortResponse);
        assertFalse(violations.isEmpty());
    }
}