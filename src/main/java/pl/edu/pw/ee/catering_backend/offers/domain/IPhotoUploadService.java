package pl.edu.pw.ee.catering_backend.offers.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotoUploadService {
  public List<String> uploadPhotos(List<MultipartFile> photos);
}
