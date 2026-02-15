package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.WordAlreadyUsedException;
import ru.yandex.practicum.exceptions.WordNotInDictionaryException;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    private static final int MAX_STEPS = 6;
    private final WordleDictionary dictionary;
    private final String answer;
    private final List<String> previousGuesses = new ArrayList<>();
    private final PrintWriter logWriter;
    private int steps = MAX_STEPS;

    private final Set<Character> correctLetters = new HashSet<>();
    private final Map<Integer, Character> correctPositions = new HashMap<>();
    private final Set<Character> excludedLetters = new HashSet<>();
    private final Map<Character, Integer> letterMinCount = new HashMap<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter logWriter) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.logWriter = logWriter;
    }

    public String makeGuess(String input) throws WordNotInDictionaryException, WordAlreadyUsedException {
        String guess = normalize(input);

        if (guess.length() != 5) {
            throw new WordNotInDictionaryException("Слово должно содержать ровно 5 букв.");
        }
        if (!dictionary.contains(guess)) {
            throw new WordNotInDictionaryException("Слово отсутствует в словаре.");
        }
        if (previousGuesses.contains(guess)) {
            throw new WordAlreadyUsedException("Вы уже вводили это слово.");
        }

        previousGuesses.add(guess);
        steps--;

        StringBuilder feedback = new StringBuilder();
        char[] guessChars = guess.toCharArray();
        char[] answerChars = answer.toCharArray();
        boolean[] used = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == answerChars[i]) {
                feedback.append('+');
                used[i] = true;
            } else {
                feedback.append(' ');
            }
        }

        char[] result = feedback.toString().toCharArray();

        for (int i = 0; i < 5; i++) {
            if (result[i] == ' ') {
                for (int j = 0; j < 5; j++) {
                    if (!used[j] && guessChars[i] == answerChars[j]) {
                        result[i] = '^';
                        used[j] = true;
                        break;
                    }
                }
            }
            if (result[i] == ' ') {
                result[i] = '-';
            }
        }

        String resultStr = new String(result);
        logWriter.println("Попытка: " + guess + " → " + resultStr);
        updateGameState(guess, resultStr);
        return resultStr;
    }

    private void updateGameState(String guess, String feedbackStr) {
        char[] guessChars = guess.toCharArray();
        char[] feedbackChars = feedbackStr.toCharArray();

        Map<Character, Integer> letterCount = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            if (feedbackChars[i] == '+') {
                correctPositions.put(i, guessChars[i]);
                correctLetters.add(guessChars[i]);
                letterCount.put(guessChars[i], letterCount.getOrDefault(guessChars[i], 0) + 1);
            } else if (feedbackChars[i] == '^') {
                correctLetters.add(guessChars[i]);
                letterCount.put(guessChars[i], letterCount.getOrDefault(guessChars[i], 0) + 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : letterCount.entrySet()) {
            letterMinCount.put(entry.getKey(), Math.max(letterMinCount.getOrDefault(entry.getKey(), 0), entry.getValue()));
        }

        for (int i = 0; i < 5; i++) {
            if (feedbackChars[i] == '-') {
                excludedLetters.add(guessChars[i]);
            }
        }
    }

    public boolean isGameOver() {
        return steps == 0 || isPlayerWon();
    }

    public boolean isPlayerWon() {
        return !previousGuesses.isEmpty() && previousGuesses.get(previousGuesses.size() - 1).equals(answer);
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public String getHint() {
        List<String> possibleWords = dictionary.filterWords(previousGuesses);
        if (possibleWords.isEmpty()) {
            return "Подходящих слов не найдено.";
        }
        return possibleWords.get(new Random().nextInt(possibleWords.size()));
    }

    private String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }
}