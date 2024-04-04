package com.talentprobe.domain.assessmentquestion;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentQuestionRepository extends MongoRepository<AssessmentQuestion, String> {
  AssessmentQuestion findByAssessmentId(String assessmentId);

  List<AssessmentQuestion> findAllByAssessmentId(String assessmentId);
}
