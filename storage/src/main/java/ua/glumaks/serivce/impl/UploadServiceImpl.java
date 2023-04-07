package ua.glumaks.serivce.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.glumaks.exception.ResourceNotFoundException;
import ua.glumaks.model.UploadFile;
import ua.glumaks.repository.UploadFileRepository;
import ua.glumaks.serivce.FileStorageService;
import ua.glumaks.serivce.UploadService;

import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final FileStorageService storageService;
    private final UploadFileRepository fileRepo;


    @Override
    public UploadFile getByName(String name) {
        return fileRepo.findByStorageName(name)
                .orElseThrow(() -> new ResourceNotFoundException("File " + name + " wasn't found"));
    }

    @Override
    public byte[] loadData(UploadFile file) {
        return storageService.load(file.getStorageName());
    }

    @Override
    public UploadFile upload(MultipartFile file) {
        String storageName;
        try {
            storageName = storageService.store(
                    file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            throw new IllegalArgumentException("file: " + file); //TODO!!!!!
        }

        UploadFile uploadFile = UploadFile.builder()
                .originalName(file.getOriginalFilename())
                .storageName(storageName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();

        log.info("Saving upload file to db: {}", uploadFile);
        return fileRepo.save(uploadFile);
    }

}
