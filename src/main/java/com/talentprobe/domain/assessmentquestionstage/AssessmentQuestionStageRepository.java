package com.talentprobe.domain.assessmentquestionstage;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentQuestionStageRepository extends MongoRepository<AssessmentQuestionStage, String> {
  List<AssessmentQuestionStage> findAllByAssessmentId(String assessmentId);

  void deleteAllByAssessmentId(String assessmentId);

  AssessmentQuestionStage findByAssessmentIdAndRecommendationNumber(String assessmentId,
      long recommendationNumber);
}
