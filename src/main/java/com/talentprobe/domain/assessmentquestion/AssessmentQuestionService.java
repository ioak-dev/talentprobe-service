package com.talentprobe.domain.assessmentquestion;

import java.util.List;

public interface AssessmentQuestionService {

  List<AssessmentQuestion> upsertAndDelete(List<AssessmentQuestion> request, String assessmentId);

  List<AssessmentQuestion> getAllByAssessmentId(String assessmentId);

  void updateQuestionsFromStage(String assessmentId);

  AssessmentQuestion newQuestion(String assessmentId);

  AssessmentQuestion update(AssessmentQuestion request, String questionId, String assessmentId);

  void delete(String questionId, String assessmentId);
}
