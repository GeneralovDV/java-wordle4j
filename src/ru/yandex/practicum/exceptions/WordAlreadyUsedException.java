package ru.yandex.practicum.exceptions;

public class WordAlreadyUsedException extends Exception {
    public WordAlreadyUsedException(String message) {
        super(message);
    }
}
