package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class WordleDictionaryLoader {
    private final PrintWriter logWriter;

    public WordleDictionaryLoader(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public WordleDictionary loadDictionary(String filename) {
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
            logWriter.println("Загружено " + dictionary.size() + " слов из файла: " + filename);
        } catch (IOException e) {
            logWriter.println("Ошибка загрузки словаря: " + e.getMessage());
        }
        return dictionary;
    }
}