package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordleDictionaryTest {

    @Test
    void testNormalizeWord() {
        WordleDictionary dict = new WordleDictionary();
        assertEquals("привет", dict.normalize("Привет"));
        assertEquals("привет", dict.normalize("привёт"));
    }

    @Test
    void testFilterWords() {
        WordleDictionary dict = new WordleDictionary();
        dict.addWord("кошка");
        dict.addWord("кость");
        dict.addWord("ёлка");

        List<String> guesses = List.of("кошка", "кость");
        List<String> filtered = dict.filterWords(guesses);

        assertNotNull(filtered);
    }
}