package com.talentprobe.domain.assessmentquestion;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentQuestionRepository extends MongoRepository<AssessmentQuestion, String> {
  AssessmentQuestion findByAssessmentId(String assessmentId);
}
