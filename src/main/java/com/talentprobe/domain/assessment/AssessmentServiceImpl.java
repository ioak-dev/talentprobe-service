package com.talentprobe.domain.assessment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssessmentServiceImpl implements AssessmentService {

  @Autowired
  private AssessmentRepository assessmentRepository;

  @Override
  public List<Assessment> getAllAssessments() {
    return assessmentRepository.findAll();
  }

  @Override
  public Assessment create(Assessment assessment) {
    assessment.setStatus(Status.New);
      return assessmentRepository.save(assessment);
    }

  @Override
  public Assessment update(Assessment request, String id) {
    if (request.getId() != null) {
      Assessment assessment = assessmentRepository.findById(request.getId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Not found"));
      assessment.setName(request.getName());
      assessment.setJobDescription(request.getJobDescription());
      assessment.setDuration(request.getDuration());
      assessment.setStatus(request.getStatus());
      assessment.setLastRecommendationId(request.getLastRecommendationId());
      return assessmentRepository.save(assessment);
    }
    throw new IllegalArgumentException("Assessment id cannot be null for update");
  }

  @Override
  public Assessment getById(String id) {
    return assessmentRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Not found"));
  }

  @Override
  public void delete(String id) {
    assessmentRepository.deleteById(id);
  }
}