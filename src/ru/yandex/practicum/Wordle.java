package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.DictionaryLoadException;
import ru.yandex.practicum.exceptions.EmptyDictionaryException;
import ru.yandex.practicum.exceptions.WordAlreadyUsedException;
import ru.yandex.practicum.exceptions.WordNotInDictionaryException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter("game.log"))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);
            WordleDictionary dictionary;

            try {
                dictionary = loader.loadDictionary("words_ru.txt");
            } catch (DictionaryLoadException | EmptyDictionaryException e) {
                System.err.println("Ошибка инициализации игры: " + e.getMessage());
                logWriter.println("Ошибка инициализации игры: " + e.getMessage());
                return;
            }

            WordleGame game = new WordleGame(dictionary, logWriter);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Добро пожаловать в игру Wordle!");
            System.out.println("Введите слово из 5 букв или нажмите Enter для подсказки.");

            while (!game.isGameOver()) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Подсказка: " + game.getHint());
                    continue;
                }

                try {
                    String result = game.makeGuess(input);
                    System.out.println(result);
                } catch (WordNotInDictionaryException | WordAlreadyUsedException e) {
                    System.out.println(e.getMessage());
                    logWriter.println("Ошибка ввода: " + e.getMessage());
                }
            }

            if (game.isPlayerWon()) {
                System.out.println("Поздравляем! Вы угадали слово: " + game.getAnswer());
            } else {
                System.out.println("Игра окончена. Загаданное слово: " + game.getAnswer());
            }

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        }
    }
}