package pl.edu.pw.ee.catering_backend.offers.domain;

import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoUploadService implements IPhotoUploadService {
  private final RestTemplate restTemplate;
  private final String pythonServiceUrl = "http://localhost:5000/upload";

  public PhotoUploadService() {
    this.restTemplate = new RestTemplate();
  }

  public List<String> uploadPhotos(List<MultipartFile> photos) {
    return photos.stream()
            .map(this::uploadSinglePhoto)
            .collect(Collectors.toList());
  }

  private String uploadSinglePhoto(MultipartFile photo) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      ByteArrayResource photoResource = new ByteArrayResource(photo.getBytes()) {
        @Override
        public String getFilename() {
          return photo.getOriginalFilename();
        }
      };

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("file", photoResource);

      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

      ResponseEntity<PhotoUploadResponse> response = restTemplate.exchange(
              pythonServiceUrl,
              HttpMethod.POST,
              requestEntity,
              PhotoUploadResponse.class
      );

      if (response.getStatusCode() == HttpStatus.CREATED) {
        return response.getBody().getUrl();
      } else {
        throw new InvalidMealDataException("Invalid photo format: " + photo.getOriginalFilename(), new ArrayList<>());
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while uploading photo", e);
    }
  }

  @Data
  private static class PhotoUploadResponse {
    private String url;
  }
}
