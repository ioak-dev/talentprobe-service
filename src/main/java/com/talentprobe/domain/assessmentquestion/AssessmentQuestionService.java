package com.talentprobe.domain.assessmentquestion;

import java.util.List;

public interface AssessmentQuestionService {

  List<AssessmentQuestion> upsertAndDelete(List<AssessmentQuestion> request);

  List<AssessmentQuestion> getById(String assessmentId);

  void updateQuestions(String assessmentId);
}
