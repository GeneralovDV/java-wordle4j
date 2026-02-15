package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.WordAlreadyUsedException;
import ru.yandex.practicum.exceptions.WordNotInDictionaryException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

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
    void testCorrectGuess() throws WordAlreadyUsedException, WordNotInDictionaryException {
        WordleGame game = new WordleGame(dictionary, logWriter);
        game.makeGuess("герой");

        assertTrue(game.isPlayerWon());
    }

    @Test
    void testInvalidWordLength() {
        WordleGame game = new WordleGame(dictionary, logWriter);

        assertThrows(WordNotInDictionaryException.class, () -> game.makeGuess("дом"));
    }

    @Test
    void testInvalidWordNotInDictionary() {
        WordleGame game = new WordleGame(dictionary, logWriter);

        assertThrows(WordNotInDictionaryException.class, () -> game.makeGuess("слово"));
    }

    @Test
    void testWordAlreadyUsed() throws WordNotInDictionaryException, WordAlreadyUsedException {
        WordleGame game = new WordleGame(dictionary, logWriter);

        game.makeGuess("герой");
        assertThrows(WordAlreadyUsedException.class, () -> game.makeGuess("герой"));
    }
}