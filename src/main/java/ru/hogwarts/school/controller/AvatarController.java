package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/avatar")
@Tag(name = "Контроллер аватарок")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление авы",
            description = "Добавляется к ID студента")
    public void uploadImage(@RequestParam("studentId") long studentId,
                            @RequestBody MultipartFile multipartFile) throws IOException {
        avatarService.uploadImage(studentId, multipartFile);
    }

    @GetMapping(path = "/get/from-db")
    @Operation(summary = "Получение авы",
            description = "Из БД")
    public ResponseEntity<byte[]> getAvatarFromDB(@RequestParam("studentId") long studentId) {
        Avatar avatar = avatarService.getAvatarFromDB(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping(path = "/get/from-local", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Получение авы",
            description = "Из локального хранилища")
    public byte[] getAvatarFromLocal(@RequestParam("studentId") long studentId) throws IOException {
        return avatarService.getAvatarFromLocal(studentId);
    }

    @GetMapping("/with-pagination")
    @Operation(summary = "Получение аватарок with-pagination",
            description = "Постранично")
    public ResponseEntity<?> getAvatarsFromDBWithPagination(@RequestParam(defaultValue = "0") Integer numberOfPage, @RequestParam(defaultValue = "1") Integer sizeOfPage, HttpServletResponse response) throws IOException {
        List<Avatar> avatars = avatarService.getAvatarsFromDBWithPagination(numberOfPage, sizeOfPage);

        if (avatars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ResponseEntity<byte[]>> responses = new ArrayList<>();

        for (Avatar avatar : avatars) {
            Path path = Path.of(avatar.getFilePath());
            byte[] content = Files.readAllBytes(path);

            responses.add(ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                    .contentLength(avatar.getFileSize())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName().toString() + "\"")
                    .body(content));
        }
        return ResponseEntity.ok(responses);
    }
}
