package com.example.dto.users;

import com.example.dto.config.AbstractDtoTest;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserShortResponseDtoTest extends AbstractDtoTest<UserShortResponse> {

    @AfterEach
    void tearDown() {
        clearFields();
    }

    @Test
    void shouldPass() {
        UserShortResponse userResponse = getModel();
        var violations = validate(userResponse);

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void invalidTextFields(String value) {
        var fields = Stream.of("firstName", "lastName", "email").toList();
        fields.stream()
                .peek(field -> setField(field, value))
                .map(field -> getModel())
                .map(this::validate)
                .map(Set::isEmpty)
                .forEach(Assertions::assertFalse);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-100L, 0L})
    void invalidId(Long value) {
        setField("id", value);
        var userShortResponse = getModel();
        var violations = validate(userShortResponse);
        assertFalse(violations.isEmpty());
    }
}