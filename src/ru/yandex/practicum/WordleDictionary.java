package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WordleDictionary {
    private final List<String> words = new ArrayList<>();

    public void addWord(String word) {
        String normalized = normalize(word);
        if (!words.contains(normalized)) {
            words.add(normalized);
        }
    }

    public boolean contains(String word) {
        return words.contains(normalize(word));
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    public List<String> filterWords(List<String> guesses) {
        return words.stream()
                .filter(word -> matchesAllFeedbacks(word, guesses))
                .collect(Collectors.toList());
    }

    private boolean matchesAllFeedbacks(String candidate, List<String> guesses) {
        for (String guess : guesses) {
            if (!matchesFeedback(candidate, guess)) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesFeedback(String candidate, String guess) {
        return true;
    }

    public int size() {
        return words.size();
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }
}