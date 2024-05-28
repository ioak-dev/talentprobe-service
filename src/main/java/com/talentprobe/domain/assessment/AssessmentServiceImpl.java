package com.talentprobe.domain.assessment;

import com.talentprobe.domain.ai.AIResponse;
import com.talentprobe.domain.ai.AIService;
import com.talentprobe.domain.assessmentquestion.AssessmentQuestionService;
import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssessmentServiceImpl implements AssessmentService {

  @Autowired
  private AssessmentRepository assessmentRepository;

  @Autowired
  AIService aiService;

  @Autowired
  private AssessmentQuestionService assessmentQuestionService;

  @Autowired
  private AssessmentQuestionStageService assessmentQuestionStageService;

  @Override
  public List<Assessment> getAllAssessments() {
    return assessmentRepository.findAll();
  }

  @Override
  public Assessment create(Assessment assessment) {
    assessment.setStatus(Status.Draft);
    //assessment.setSkillSet(updateSkillSet(assessment));
      return assessmentRepository.save(assessment);
    }

  @Override
  public Assessment update(Assessment request, String id) {
    if (id != null) {
      Assessment assessment = assessmentRepository.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Not found"));
      if (request.getJobDescription().equals(assessment.getJobDescription())){
        assessment.setName(request.getName());
        assessment.setJobDescription(request.getJobDescription());
        assessment.setDuration(request.getDuration());
        assessment.setStatus(request.getStatus());
        assessment.setLastRecommendationNumber(request.getLastRecommendationNumber());
        return assessmentRepository.save(assessment);
      }
      else {
        List<AIResponse> aiResponseList = aiService.getAIResponse(request.getJobDescription(),20) ;
        assessmentQuestionStageService.deleteAndUpdateQuestionStage(aiResponseList, id);
        Assessment assessmentTemp = assessmentQuestionService.updateQuestionsFromStage(id);
        assessment.setName(request.getName());
        assessment.setJobDescription(request.getJobDescription());
        assessment.setDuration(request.getDuration());
        assessment.setStatus(request.getStatus());
        assessment.setLastRecommendationNumber(assessmentTemp.getLastRecommendationNumber());
        assessment.setSkillSet(updateSkillSet(request));
        return assessmentRepository.save(assessment);
      }
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

  @Override
  public Assessment updateStatus(Status status, String id) {
    Assessment assessment = new Assessment();
    if (id != null) {
      assessment = assessmentRepository.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment Not found"));
      assessment.setStatus(status);
      assessmentRepository.save(assessment);
    }
    return assessment;
  }

  public List<String> updateSkillSet(Assessment request) {
    return aiService.getAISkillSetResponse(request.getJobDescription(), 4);
  }
}
