package com.talentprobe.domain.assessmentresponse;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentResponseRepository extends MongoRepository<AssessmentResponse, String> {

  List<AssessmentResponse> findAllByAssessmentId(String assessmentId);

  void deleteByAssessmentIdAndId(String assessmentId, String id);

  AssessmentResponse findByAssessmentId(String assessmentId);
}
