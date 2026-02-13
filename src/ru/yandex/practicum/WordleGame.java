package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    private final WordleDictionary dictionary;
    private final String answer;
    private final List<String> previousGuesses = new ArrayList<>();
    private final PrintWriter logWriter;
    private int steps = 6;

    public WordleGame(WordleDictionary dictionary, PrintWriter logWriter) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.logWriter = logWriter;
    }

    public String makeGuess(String input) throws InvalidWordException {
        String guess = normalize(input);

        if (guess.length() != 5) {
            throw new InvalidWordException("Слово должно содержать ровно 5 букв.");
        }
        if (!dictionary.contains(guess)) {
            throw new InvalidWordException("Слово отсутствует в словаре.");
        }
        if (previousGuesses.contains(guess)) {
            throw new InvalidWordException("Вы уже вводили это слово.");
        }

        previousGuesses.add(guess);
        steps--;

        StringBuilder feedback = new StringBuilder();
        char[] guessChars = guess.toCharArray();
        char[] answerChars = answer.toCharArray();

        boolean[] used = new boolean[answer.length()];

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
        return resultStr;
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