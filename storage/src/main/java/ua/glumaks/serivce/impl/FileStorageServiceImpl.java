package ua.glumaks.serivce.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.glumaks.config.StorageConfigurationProperties;
import ua.glumaks.exception.FileStorageException;
import ua.glumaks.exception.StorageServiceException;
import ua.glumaks.serivce.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static ua.glumaks.util.CompressionUtil.compress;
import static ua.glumaks.util.CompressionUtil.decompress;

@Slf4j
@Service

public class FileStorageServiceImpl implements FileStorageService {

    private final Path filePath;


    @Autowired
    public FileStorageServiceImpl(StorageConfigurationProperties properties) {
        filePath = Paths.get(properties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(filePath);
        } catch (IOException e) {
            log.error("Failure to create directories: {}", filePath);
            throw new FileStorageException("Failure to initialize storage");
        }
    }

    @Override
    public String store(byte[] data, String originalFileName) {
        try {
            String fileName = normalizeFileName(originalFileName);
            Path targerPath = filePath.resolve(fileName);
            byte[] compressedBytes = compress(data);

            Files.write(targerPath, compressedBytes);
            return fileName;
        } catch (IOException ex) {
            log.error("Can't upload a file: {}", originalFileName);
            throw new StorageServiceException(ex);
        }
    }

    private String normalizeFileName(String originalName) {
        String name = StringUtils.cleanPath(originalName);
        String extension = getFileExtension(name);

        name = UUID.randomUUID() + "-file";
        if (!extension.equals("")) {
            name += "." + extension;
        }

        if (name.contains("..")) {
            log.warn("Incorrect filename sequence: {}", name);
            throw new StorageServiceException(
                    "Filename contains invalid path sequence: " + name);
        }

        return name;
    }

    private String getFileExtension(String fileName) {
        int ind = fileName.lastIndexOf(".");
        return ind == -1?
                "":
                fileName.substring(ind + 1);
    }

    @Override
    public byte[] load(String fileName) {
        try {
            Path targerPath = filePath.resolve(fileName);
            byte[] compressed = Files.readAllBytes(targerPath);
            return decompress(compressed);
        } catch (IOException ex) {
            log.error("Failure to load a file", ex);
            throw new StorageServiceException(ex);
        }
    }

}
