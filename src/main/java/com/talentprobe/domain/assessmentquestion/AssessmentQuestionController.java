package com.talentprobe.domain.assessmentquestion;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment/")
public class AssessmentQuestionController {

  @Autowired
  private AssessmentQuestionService assessmentQuestionService;

  @GetMapping("/{assessmentId}/question")
  public ResponseEntity<List<AssessmentQuestion>> getAllByAssessmentId(@PathVariable String assessmentId) {
    return ResponseEntity.ok(assessmentQuestionService.getAllByAssessmentId(assessmentId));
  }

  @GetMapping("/{assessmentId}/new-question")
  public ResponseEntity<AssessmentQuestion> newQuestion(@PathVariable String assessmentId) {
    return ResponseEntity.ok(assessmentQuestionService.newQuestion(assessmentId));
  }

  @PostMapping("/{assessmentId}/question")
  public ResponseEntity<AssessmentQuestion> upsertAndDelete(
      @RequestBody AssessmentQuestion request, @PathVariable String assessmentId) {
    return ResponseEntity.ok(assessmentQuestionService.create(request, assessmentId));
  }

  @PutMapping("/{assessmentId}/question/{questionId}")
  public ResponseEntity<AssessmentQuestion> update(
      @RequestBody AssessmentQuestion request, @PathVariable String questionId,
      @PathVariable String assessmentId) {
    return ResponseEntity.ok(assessmentQuestionService.update(request, questionId, assessmentId));
  }

  @DeleteMapping("/{assessmentId}/question/{questionId}")
  public void delete(@PathVariable String questionId,
      @PathVariable String assessmentId) {
    assessmentQuestionService.delete( questionId, assessmentId);
  }
}