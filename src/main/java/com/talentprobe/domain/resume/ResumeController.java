package com.talentprobe.domain.resume;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

  @Autowired
  private ResumeService resumeService;

  @GetMapping
  public ResponseEntity<List<Resume>> getAll() {
    return ResponseEntity.ok(resumeService.getAllResumes());
  }


  @GetMapping("{id}")
  public ResponseEntity<Resume> getResumeById(@PathVariable String id) {
    return ResponseEntity.ok(resumeService.getResumeById(id));
  }

  @PostMapping
  public ResponseEntity<Resume> scanResume(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(resumeService.scanResume(file));
  }

}
