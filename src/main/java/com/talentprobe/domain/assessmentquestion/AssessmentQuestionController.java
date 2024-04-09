package com.talentprobe.domain.assessmentquestion;

import com.talentprobe.domain.assessment.Assessment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment/")
public class AssessmentQuestionController {

  @Autowired
  private AssessmentQuestionService assessmentQuestionService;

  @GetMapping("/{assessmentId}/question")
  public ResponseEntity<List<AssessmentQuestion>> getById(@PathVariable String assessmentId) {
    return ResponseEntity.ok(assessmentQuestionService.getById(assessmentId));
  }

  @PostMapping("/{assessmentId}/question")
  public ResponseEntity<List<AssessmentQuestion>> upsertAndDelete(
      @RequestBody List<AssessmentQuestion> request) {
    return ResponseEntity.ok(assessmentQuestionService.upsertAndDelete(request));
  }
}