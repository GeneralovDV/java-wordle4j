package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {
    private final List<String> words = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public void addWord(String word) {
        String normalized = normalize(word);
        if (normalized.length() != 5) {
            return; // игнорируем слова не из 5 букв
        }
        if (!words.contains(normalized)) {
            words.add(normalized);
        }
    }

    public boolean contains(String word) {
        return words.contains(normalize(word));
    }

    public String getRandomWord() {
        return words.get(RANDOM.nextInt(words.size()));
    }

    public List<String> filterWords(List<String> guesses) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            boolean matches = true;
            for (String guess : guesses) {
                if (!matchesFeedback(word, guess)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                result.add(word);
            }
        }
        return result;
    }

    private boolean matchesFeedback(String candidate, String guess) {
        char[] candidateChars = candidate.toCharArray();
        char[] guessChars = guess.toCharArray();
        char[] feedback = new char[guessChars.length];
        boolean[] used = new boolean[candidateChars.length];

        for (int i = 0; i < candidateChars.length; i++) {
            if (guessChars[i] == candidateChars[i]) {
                feedback[i] = '+';
                used[i] = true;
            } else {
                feedback[i] = ' ';
            }
        }

        for (int i = 0; i < guessChars.length; i++) {
            if (feedback[i] == ' ') {
                for (int j = 0; j < candidateChars.length; j++) {
                    if (!used[j] && guessChars[i] == candidateChars[j]) {
                        feedback[i] = '^';
                        used[j] = true;
                        break;
                    }
                }
            }
            if (feedback[i] == ' ') {
                feedback[i] = '-';
            }
        }

        String expectedFeedback = new String(feedback);
        String actualFeedback = getFeedbackForGuess(guess, candidate);
        return expectedFeedback.equals(actualFeedback);
    }

    private String getFeedbackForGuess(String guess, String candidate) {
        char[] guessChars = guess.toCharArray();
        char[] answerChars = candidate.toCharArray();
        char[] feedback = new char[5];
        boolean[] used = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == answerChars[i]) {
                feedback[i] = '+';
                used[i] = true;
            } else {
                feedback[i] = ' ';
            }
        }

        for (int i = 0; i < 5; i++) {
            if (feedback[i] == ' ') {
                for (int j = 0; j < 5; j++) {
                    if (!used[j] && guessChars[i] == answerChars[j]) {
                        feedback[i] = '^';
                        used[j] = true;
                        break;
                    }
                }
            }
            if (feedback[i] == ' ') {
                feedback[i] = '-';
            }
        }

        return new String(feedback);
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