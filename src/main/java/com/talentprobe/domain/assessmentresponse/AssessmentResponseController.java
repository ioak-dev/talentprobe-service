package com.talentprobe.domain.assessmentresponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment/")
public class AssessmentResponseController {

  @Autowired
  private AssessmentResponseService assessmentResponseService;

  @GetMapping("/{assessmentId}/response")
  public ResponseEntity<List<AssessmentResponse>> getAllByAssessmentId(@PathVariable String assessmentId) {
    return ResponseEntity.ok(assessmentResponseService.getAllByAssessmentId(assessmentId));
  }

  @PostMapping("/{assessmentId}/response")
  public ResponseEntity<AssessmentResponse> createAndUpdate(@PathVariable String assessmentId,
      @RequestBody AssessmentResponse request) {
    return ResponseEntity.ok(assessmentResponseService.createAndUpdate(request, assessmentId));
  }

  @DeleteMapping("/{assessmentId}/response/{responseId}")
  public void delete(@PathVariable String assessmentId, @PathVariable String responseId) {
    assessmentResponseService.delete(responseId, assessmentId);
  }
}
