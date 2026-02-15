package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<String> guesses = new ArrayList<>();
        guesses.add("кошка");
        guesses.add("кость");

        List<String> filtered = dict.filterWords(guesses);

        assertNotNull(filtered);
        assertTrue(filtered.contains("кошка") || filtered.contains("кость") || filtered.contains("ёлка"));
    }
}