package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter("game.log"))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logWriter);
            WordleDictionary dictionary = loader.loadDictionary("words_ru.txt");

            if (dictionary.isEmpty()) {
                logWriter.println("Словарь пуст.");
                System.err.println("Ошибка: словарь пуст.");
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
                } catch (InvalidWordException e) {
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