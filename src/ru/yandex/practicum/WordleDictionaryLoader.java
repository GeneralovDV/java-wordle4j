package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.DictionaryLoadException;
import ru.yandex.practicum.exceptions.EmptyDictionaryException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordleDictionaryLoader {
    private final PrintWriter logWriter;

    public WordleDictionaryLoader(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public WordleDictionary loadDictionary(String filename) throws DictionaryLoadException, EmptyDictionaryException {
        WordleDictionary dictionary = new WordleDictionary();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 5) {
                    dictionary.addWord(line);
                }
            }

            if (dictionary.isEmpty()) {
                String msg = "Словарь пуст.";
                logWriter.println(msg);
                throw new EmptyDictionaryException(msg);
            }

            logWriter.println("Загружено " + dictionary.size() + " слов из файла: " + filename);
            return dictionary;

        } catch (IOException e) {
            String msg = "Ошибка загрузки словаря: " + e.getMessage();
            logWriter.println(msg);
            throw new DictionaryLoadException(msg);
        }
    }
}