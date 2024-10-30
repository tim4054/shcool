package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@Tag(name = "Контроллер info")
public class InfoController {
    @Value("${server.port}")
    String serverPort;

    @GetMapping("/port")
    @Operation(summary = "Получение порта")
    public String getPort() {
    return serverPort;
    }
}

