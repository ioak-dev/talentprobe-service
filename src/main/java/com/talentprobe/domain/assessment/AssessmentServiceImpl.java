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
  public Assessment create(Assessment request) {
    return assessmentRepository.save(request);
  }

  @Override
  public Assessment update(String id, Assessment request){
    if (id != null) {
      Assessment assessment = assessmentRepository.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Not found"));
      assessment.setName(request.getName());
      assessment.setJobDescription(request.getJobDescription());
      assessment.setDuration(request.getDuration());
      return assessmentRepository.save(assessment);
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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
