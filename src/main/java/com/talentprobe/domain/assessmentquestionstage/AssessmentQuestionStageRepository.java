package com.talentprobe.domain.assessmentquestionstage;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentQuestionStageRepository extends MongoRepository<AssessmentQuestionStage, String> {
  AssessmentQuestionStage findByAssessmentId(String assessmentId);
}
