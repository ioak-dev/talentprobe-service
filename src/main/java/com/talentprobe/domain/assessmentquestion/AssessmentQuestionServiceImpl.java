package com.talentprobe.domain.assessmentquestion;

import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStage;
import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStageRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssessmentQuestionServiceImpl implements
    AssessmentQuestionService {

  @Autowired
  private AssessmentQuestionRepository assessmentQuestionRepository;

  @Autowired
  private AssessmentQuestionStageRepository assessmentQuestionStageRepository;

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

  @Override
  public void updateQuestions(String assessmentId) {
    int deletedQuestionsCount = getDeletedQuestionsCount(assessmentId);
    List<AssessmentQuestionStage> assessmentQuestionStageList = assessmentQuestionStageRepository
        .findAllByAssessmentId(assessmentId);
    List<AssessmentQuestionStage> firstXAssessmentQuestionStages;
    if (deletedQuestionsCount == 0) {
      firstXAssessmentQuestionStages = assessmentQuestionStageList
          .stream().limit(10).toList();
    }
    else {
      firstXAssessmentQuestionStages = assessmentQuestionStageList
          .stream().limit(deletedQuestionsCount).toList();
    }
    List<AssessmentQuestion> assessmentQuestionList = new ArrayList<>();
    firstXAssessmentQuestionStages.forEach(stage -> {
      AssessmentQuestion assessmentQuestion = new AssessmentQuestion();
      assessmentQuestion.setAssessmentId(stage.getAssessmentId());
      assessmentQuestion.setAssessmentStageId(stage.getId());
      assessmentQuestion.setQuestion(stage.getQuestion());
      assessmentQuestion.setAnswer(stage.getQuestion());
      assessmentQuestion.setType(stage.getType());
      assessmentQuestion.setChoices(stage.getChoices());
      assessmentQuestion.setPinned(false);
      assessmentQuestionList.add(assessmentQuestion);
    });
    assessmentQuestionRepository.saveAll(assessmentQuestionList);
  }

  public int getDeletedQuestionsCount(String assessmentId){
    int deletedQuestionsCount = 0;
    List<AssessmentQuestion> assessmentQuestions = assessmentQuestionRepository.findAllByAssessmentId(
        assessmentId);
    for (AssessmentQuestion assessmentQuestion : assessmentQuestions
    ) {
      if (!assessmentQuestion.getPinned()) {
        assessmentQuestionRepository.delete(assessmentQuestion);
        deletedQuestionsCount++;
      }
    }
    return deletedQuestionsCount;
  }
}
