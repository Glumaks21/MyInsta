package ua.glumaks.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.glumaks.model.UploadFile;
import ua.glumaks.payload.response.UploadFileResponse;
import ua.glumaks.serivce.UploadService;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;


    @GetMapping("/download/{fileName}")
    ResponseEntity<byte[]> download(@PathVariable String fileName) {
        log.info("Download a file: {}", fileName);

        UploadFile file = uploadService.getByName(fileName);
        byte[] data = uploadService.loadData(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getContentType()));
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").
                filename(file.getOriginalName()).
                build();
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UploadFileResponse> upload(@RequestParam("file") MultipartFile file) {

        log.info("Upload a file: {}", file.getOriginalFilename());
        UploadFile uploadFile = uploadService.upload(file);

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/uploads/download/")
                .path(uploadFile.getStorageName())
                .toUriString();

        UploadFileResponse response = new UploadFileResponse(
                uploadFile.getOriginalName(),
                downloadUri,
                uploadFile.getContentType(),
                uploadFile.getSize()
        );

        return ResponseEntity.status(CREATED).body(response);
    }

}
