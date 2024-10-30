package ru.hogwarts.school.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Факультет не найден")
public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(long id) {
        super("%s-й факультет не найден".formatted(id));
    }
}
