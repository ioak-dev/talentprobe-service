package com.talentprobe.domain.assessmentresponse;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentResponseRepository extends MongoRepository<AssessmentResponse, String> {

  AssessmentResponse findByAssessmentId(String assessmentId);

}
