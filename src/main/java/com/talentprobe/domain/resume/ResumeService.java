package com.talentprobe.domain.resume;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {

  List<Resume> getAllResumes();

  Resume getResumeById(String id);

  Resume scanResume(MultipartFile file);

  ResponseEntity<Object> downloadResume(String id);
}
