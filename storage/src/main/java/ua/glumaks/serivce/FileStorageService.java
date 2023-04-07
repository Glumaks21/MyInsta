package ua.glumaks.serivce;

public interface FileStorageService {

    String store(byte[] data, String originalFileName);

    byte[] load(String storageFileName);

}
