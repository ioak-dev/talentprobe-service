package com.talentprobe.domain.assessmentquestion;

import com.talentprobe.domain.assessment.Assessment;
import java.util.List;

public interface AssessmentQuestionService {

  AssessmentQuestion create(AssessmentQuestion request, String assessmentId);

  List<AssessmentQuestion> getAllByAssessmentId(String assessmentId);

  Assessment updateQuestionsFromStage(String assessmentId);

  AssessmentQuestion newQuestion(String assessmentId);

  AssessmentQuestion update(AssessmentQuestion request, String questionId, String assessmentId);

  void delete(String questionId, String assessmentId);
}
