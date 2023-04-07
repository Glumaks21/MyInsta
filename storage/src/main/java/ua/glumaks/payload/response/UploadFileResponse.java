package ua.glumaks.payload.response;

public record UploadFileResponse(
        String name,
        String downloadUri,
        String contentType,
        Long size
) {
}
