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
  public long getLatRecommendationNumber(String assessmentId){
    long recommendationNumber = 0;
    List<AssessmentQuestionStage> assessmentQuestionStageList = assessmentQuestionStageRepository
        .findAllByAssessmentId(assessmentId);
    if (!assessmentQuestionStageList.isEmpty()) {
      recommendationNumber =
          assessmentQuestionStageList.get(0).getRecommendationNumber();
    }
    return recommendationNumber;
  }

  @Override
  public void deleteAndUpdateQuestionStage(List<AIResponse> aiResponseList, String assessmentId) {
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

  private void updateQuestionStage(List<AIResponse> aiResponseList, String assessmentId) {
    long recommendationNumber = 1;
    List<AssessmentQuestionStage> assessmentQuestionStageList = new ArrayList<>();
    for (AIResponse aiResponse : aiResponseList) {
      AssessmentQuestionStage assessmentQuestionStage = new AssessmentQuestionStage();
      assessmentQuestionStage.setAssessmentId(assessmentId);
      assessmentQuestionStage.setQuestion(aiResponse.getQuestion());
      assessmentQuestionStage.setAnswer(aiResponse.getAnswer());
      assessmentQuestionStage.setChoices(aiResponse.getChoices());
      assessmentQuestionStage.setType(Type.MultipleChoice);
      assessmentQuestionStage.setRecommendationNumber(recommendationNumber);
      assessmentQuestionStageList.add(assessmentQuestionStage);
      recommendationNumber++;
    }
    assessmentQuestionStageRepository.saveAll(assessmentQuestionStageList);
  }
}
