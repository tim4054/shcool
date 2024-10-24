package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadImage(long studentId, MultipartFile multipartFile) throws IOException;

    Avatar getAvatarFromDB(long studentId);

    byte[] getAvatarFromLocal(long studentId) throws IOException;

    List<byte[]> getAvatars(int pageNumber, int pageSize);
}
