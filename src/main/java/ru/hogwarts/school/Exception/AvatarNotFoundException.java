package ru.hogwarts.school.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Аватар не найден")
public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException(long id) {
        super("Аватар %s-го студента не найден".formatted(id));
    }
}
