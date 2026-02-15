package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.DictionaryLoadException;
import ru.yandex.practicum.exceptions.EmptyDictionaryException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private final StringWriter stringWriter = new StringWriter();
    private final PrintWriter logWriter = new PrintWriter(stringWriter);

    @Test
    void testLoadDictionaryFromFile() throws IOException, EmptyDictionaryException, DictionaryLoadException {
        Path tempFile = Files.createTempFile("test_dict_", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("полёт\n");
        }

        WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);
        WordleDictionary dict = loader.loadDictionary(tempFile.toString());

        assertNotNull(dict);
        assertEquals(1, dict.size());
        assertTrue(dict.contains("полет"));
    }

    @Test
    void testLoadNonExistentFile() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);

        assertThrows(DictionaryLoadException.class, () -> loader.loadDictionary("nonexistent_file.txt"));
    }
}