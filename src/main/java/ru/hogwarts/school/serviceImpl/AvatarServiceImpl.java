package ru.hogwarts.school.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Exception.AvatarNotFoundException;
import ru.hogwarts.school.Exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final StudentRepository studentRepository;
    public final AvatarRepository avatarRepository;
    @Value("${my.dir}")
    private String pathDir;

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadImage(long studentId, MultipartFile multipartFile) throws IOException {

        createDirectory();

        Path filePath;
        if ((multipartFile.getOriginalFilename() != null)) {
            filePath = Path.of(pathDir, String.format("student(%s)", studentId) + "." +
                    getExtension(multipartFile.getOriginalFilename()));

            createAvatar(studentId, filePath.toString(), multipartFile);

            multipartFile.transferTo(filePath);
        }
    }

    private void createAvatar(long studentId, String filePath, MultipartFile multipartFile) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        avatarRepository.save(new Avatar(
                filePath,
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                student
        ));
    }

    private String getExtension(String originalPath) {
        return originalPath.substring(originalPath.lastIndexOf(".") + 1);
    }

    void createDirectory() throws IOException {
        Path path = Path.of(pathDir);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    @Override
    public Avatar getAvatarFromDB(long studentId) {
        studentExist(studentId);

        return avatarRepository.getByStudentId(studentId)
                .orElseThrow(() -> new AvatarNotFoundException(studentId));
    }


    @Override
    public byte[] getAvatarFromLocal(long studentId) throws IOException {
        studentExist(studentId);

        Avatar avatar = avatarRepository.getByStudentId(studentId)
                .orElseThrow(() -> new AvatarNotFoundException(studentId));

        String filePath = avatar.getFilePath();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            return bufferedInputStream.readAllBytes();
        }
    }

    void studentExist(long studentId) {
        boolean studentExist = studentRepository.existsById(studentId);
        if (!studentExist) {
            throw new StudentNotFoundException(studentId);
        }
    }
}