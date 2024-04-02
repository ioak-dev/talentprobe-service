package com.talentprobe.domain.assessmentquestion;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssessmentQuestionServiceImplementation implements
    AssessmentQuestionService {

  @Autowired
  private AssessmentQuestionRepository assessmentQuestionRepository;

  @Override
  public List<AssessmentQuestion> upsertAndDelete(List<AssessmentQuestion> request) {
    List<String> newQuestionIds = request.stream().map(AssessmentQuestion::getId).toList();
    List<AssessmentQuestion> oldQuestions = assessmentQuestionRepository.findAll();
    for (AssessmentQuestion oldQuestion : oldQuestions) {
      if (!newQuestionIds.contains(oldQuestion.getId())) {
        assessmentQuestionRepository.delete(oldQuestion);
      }
    }
    return assessmentQuestionRepository.saveAll(request);
  }

  @Override
  public AssessmentQuestion getById(String assessmentId) {
    AssessmentQuestion assessmentQuestion = assessmentQuestionRepository
        .findByAssessmentId(assessmentId);
    if (assessmentQuestion!= null)
    return assessmentQuestion;
    else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Question Not found");
    }

  }

}
