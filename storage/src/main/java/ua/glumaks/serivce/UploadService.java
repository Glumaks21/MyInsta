package ua.glumaks.serivce;

import org.springframework.web.multipart.MultipartFile;
import ua.glumaks.model.UploadFile;

public interface UploadService {

    UploadFile getByName(String string);
    byte[] loadData(UploadFile file);
    UploadFile upload(MultipartFile file);

}
