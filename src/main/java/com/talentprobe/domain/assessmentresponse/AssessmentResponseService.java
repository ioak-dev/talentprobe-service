package com.talentprobe.domain.assessmentresponse;

import java.util.List;

public interface AssessmentResponseService {


  AssessmentResponse createAndUpdate(AssessmentResponse request, String assessmentId);

  List<AssessmentResponse> getAllByAssessmentId(String assessmentId);

  void delete(String id, String assessmentId);

}
