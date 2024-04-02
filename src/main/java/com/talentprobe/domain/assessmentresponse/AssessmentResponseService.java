package com.talentprobe.domain.assessmentresponse;

import java.util.List;

public interface AssessmentResponseService {


  AssessmentResponse createAndUpdate(AssessmentResponse request, String assessmentId);

  AssessmentResponse getById(String assessmentId);

  void delete(String id);

  List<AssessmentResponse> getAllAssessmentResponses();
}
