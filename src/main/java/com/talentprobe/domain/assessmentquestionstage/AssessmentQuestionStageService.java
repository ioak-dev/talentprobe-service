package com.talentprobe.domain.assessmentquestionstage;

import com.talentprobe.domain.ai.AIResponse;

public interface AssessmentQuestionStageService {

  void deleteAndUpdateQuestionStage(AIResponse aiResponseList, String assessmentId);

}
