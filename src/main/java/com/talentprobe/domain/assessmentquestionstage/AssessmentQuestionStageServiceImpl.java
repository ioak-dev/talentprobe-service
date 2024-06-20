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
  private AssessmentQuestionStageRepository assessmentQuestionStageRepository;

  @Override
  public void deleteAndUpdateQuestionStage(AIResponse aiResponseList, String assessmentId) {
    List<AssessmentQuestionStage> assessmentQuestionStageList = assessmentQuestionStageRepository
        .findAllByAssessmentId(assessmentId);
    if (!assessmentQuestionStageList.isEmpty()) {
      deleteQuestionStage(assessmentId);
      updateQuestionStage(aiResponseList, assessmentId);
    }else{
      updateQuestionStage(aiResponseList, assessmentId);
    }
  }

  private void deleteQuestionStage(String assessmentId) {
    assessmentQuestionStageRepository.deleteAllByAssessmentId(assessmentId);
  }

  private void updateQuestionStage(AIResponse aiResponseList, String assessmentId) {
    long recommendationNumber = 1;
    List<AssessmentQuestionStage> assessmentQuestionStageList = new ArrayList<>();
    for (Object aiResponse : aiResponseList.getQuestions()) {
      AssessmentQuestionStage assessmentQuestionStage = new AssessmentQuestionStage();
      assessmentQuestionStage.setAssessmentId(assessmentId);
      assessmentQuestionStage.setData(aiResponse);
      assessmentQuestionStage.setType(Type.MultipleChoice);
      assessmentQuestionStage.setRecommendationNumber(recommendationNumber);
      assessmentQuestionStageList.add(assessmentQuestionStage);
      recommendationNumber++;
    }
    assessmentQuestionStageRepository.saveAll(assessmentQuestionStageList);
  }
}
