package com.talentprobe.domain.assessment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

  @Autowired
  private AssessmentService assessmentService;

  @GetMapping
  public ResponseEntity<List<Assessment>> getAll() {
    return ResponseEntity.ok(assessmentService.getAllAssessments());
  }

  @PutMapping
  public ResponseEntity<Assessment> createAndUpdate(@RequestBody Assessment request) {
    return ResponseEntity.ok(assessmentService.createAndUpdate(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Assessment> getById(@PathVariable String id) {
    return ResponseEntity.ok(assessmentService.getById(id));
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable String id) {
    assessmentService.delete(id);
  }
}
