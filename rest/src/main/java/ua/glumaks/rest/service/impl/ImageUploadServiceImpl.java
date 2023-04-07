package ua.glumaks.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.glumaks.rest.model.Image;
import ua.glumaks.rest.repository.ImageRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl {

    private final ImageRepository imageRepo;


    public void upload(MultipartFile file) {
    }

    public Image load() {
        return null;
    }

}
