package com.talentprobe.domain.assessmentresponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentResponseServiceImpl implements
    AssessmentResponseService {

  @Autowired
  private AssessmentResponseRepository assessmentResponseRepository;

  @Override
  public AssessmentResponse createAndUpdate(AssessmentResponse request, String assessmentId) {
    AssessmentResponse assessmentResponse = assessmentResponseRepository
        .findByAssessmentId(request.getAssessmentId());
    if (assessmentResponse != null) {
      assessmentResponse.setEmail(request.getEmail());
      assessmentResponse.setScore(request.getScore());
      assessmentResponse.setStatus(request.getStatus());
      return assessmentResponseRepository.save(assessmentResponse);
    }
    return assessmentResponseRepository.save(request);
  }

  @Override
  public List<AssessmentResponse> getAllByAssessmentId(String assessmentId) {
    return assessmentResponseRepository.findAllByAssessmentId(assessmentId);
  }

  @Override
  public void delete(String id, String assessmentId) {
    assessmentResponseRepository.deleteByAssessmentIdAndId(assessmentId, id);
  }
}
