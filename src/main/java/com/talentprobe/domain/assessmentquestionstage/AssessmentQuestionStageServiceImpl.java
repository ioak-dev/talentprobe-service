package com.talentprobe.domain.assessmentquestionstage;

import com.talentprobe.domain.ai.AIResponse;
import com.talentprobe.domain.assessmentquestion.Type;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentQuestionStageServiceImpl implements AssessmentQuestionStageService {

  @Autowired
  private AssessmentQuestionStageRepository repository;

  private static final long RECOMMENDATION_NUMBER = 1L;

  @Override
  public void deleteAndUpdateQuestionStage(List<AIResponse> aiResponseList, String assessmentId) {
    List<AssessmentQuestionStage> assessmentQuestionStageList = repository
        .findAllByAssessmentId(assessmentId);
    if (!assessmentQuestionStageList.isEmpty()) {
      long recommendationNumber =
          assessmentQuestionStageList.getFirst().getRecommendationNumber() + 1;
      deleteQuestionStage(assessmentId);
      updateQuestionStage(aiResponseList, assessmentId, recommendationNumber);
    }else{
      deleteQuestionStage(assessmentId);
      updateQuestionStage(aiResponseList, assessmentId, RECOMMENDATION_NUMBER);
    }
  }

  private void deleteQuestionStage(String assessmentId) {
    repository.deleteAllByAssessmentId(assessmentId);
  }

  private void updateQuestionStage(List<AIResponse> aiResponseList, String assessmentId,
      long recommendationNumber) {
    List<AssessmentQuestionStage> assessmentQuestionStageList = new ArrayList<>();
    aiResponseList.forEach(aiResponse -> {
      AssessmentQuestionStage assessmentQuestionStage = new AssessmentQuestionStage();
      assessmentQuestionStage.setAssessmentId(assessmentId);
      assessmentQuestionStage.setQuestion(aiResponse.getQuestion());
      assessmentQuestionStage.setAnswer(aiResponse.getAnswer());
      assessmentQuestionStage.setChoices(aiResponse.getChoices());
      assessmentQuestionStage.setType(Type.MultipleChoice);
      assessmentQuestionStage.setRecommendationNumber(recommendationNumber);
      assessmentQuestionStageList.add(assessmentQuestionStage);
    });
    repository.saveAll(assessmentQuestionStageList);
  }
}
