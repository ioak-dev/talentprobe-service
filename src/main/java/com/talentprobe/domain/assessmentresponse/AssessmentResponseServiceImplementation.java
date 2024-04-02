package com.talentprobe.domain.assessmentresponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssessmentResponseServiceImplementation implements
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
  public AssessmentResponse getById(String assessmentId) {
    return assessmentResponseRepository.findById(assessmentId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Response Not found"));
  }

  @Override
  public void delete(String id) {
    assessmentResponseRepository.deleteById(id);
  }

  @Override
  public List<AssessmentResponse> getAllAssessmentResponses() {
    return assessmentResponseRepository.findAll();
  }
}
