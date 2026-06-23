package com.anindo.journalapp.service;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of("anindo", "password123"),
                Arguments.of("john_doe", "qwerty456"),
                Arguments.of("testUser", "")
        );
    }

}
