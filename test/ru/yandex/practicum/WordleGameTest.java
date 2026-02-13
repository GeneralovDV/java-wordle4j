package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WordleGameTest {

    private WordleDictionary dictionary;
    private StringWriter stringWriter;
    private PrintWriter logWriter;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary();
        dictionary.addWord("герой");

        stringWriter = new StringWriter();
        logWriter = new PrintWriter(stringWriter);
    }

    @Test
    void testCorrectGuess() throws InvalidWordException {
        WordleGame game = new WordleGame(dictionary, logWriter);
        game.makeGuess("герой");

        assertTrue(game.isPlayerWon());
    }

    @Test
    void testInvalidWordLength() {
        WordleGame game = new WordleGame(dictionary, logWriter);

        assertThrows(InvalidWordException.class, () -> game.makeGuess("дом"));
    }

    @Test
    void testInvalidWordNotInDictionary() {
        WordleGame game = new WordleGame(dictionary, logWriter);

        assertThrows(InvalidWordException.class, () -> game.makeGuess("слово"));
    }
}