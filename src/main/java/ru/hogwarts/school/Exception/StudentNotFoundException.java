package ru.hogwarts.school.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Студент не найден")
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(long id) {
        super("%s-й студент не найден".formatted(id));
    }
}
