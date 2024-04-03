package com.talentprobe.domain.assessmentquestionstage;

import com.talentprobe.domain.ai.AIResponse;
import java.util.List;

public interface AssessmentQuestionStageService {

  void deleteAndUpdateQuestionStage(List<AIResponse> aiResponseList, String assessmentId);

}
