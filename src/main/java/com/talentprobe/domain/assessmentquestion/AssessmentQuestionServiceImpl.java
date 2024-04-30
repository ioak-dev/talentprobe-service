package com.talentprobe.domain.assessmentquestion;

import com.talentprobe.domain.assessment.Assessment;
import com.talentprobe.domain.assessment.AssessmentRepository;
import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStage;
import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStageRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentQuestionServiceImpl implements
    AssessmentQuestionService {

  @Autowired
  private AssessmentQuestionRepository assessmentQuestionRepository;

  @Autowired
  private AssessmentQuestionStageRepository assessmentQuestionStageRepository;

  @Autowired
  private AssessmentRepository assessmentRepository;

  @Override
  public List<AssessmentQuestion> upsertAndDelete(List<AssessmentQuestion> request,
      String assessmentId) {
    List<String> newQuestionIds = request.stream().map(AssessmentQuestion::getId).toList();
    List<AssessmentQuestion> oldQuestions = assessmentQuestionRepository.
        findAllByAssessmentId(assessmentId);
    for (AssessmentQuestion oldQuestion : oldQuestions) {
      if (!newQuestionIds.contains(oldQuestion.getId())) {
        assessmentQuestionRepository.delete(oldQuestion);
      }
    }
    return assessmentQuestionRepository.saveAll(request);
  }

  @Override
  public List<AssessmentQuestion> getAllByAssessmentId(String assessmentId) {
    return assessmentQuestionRepository
        .findAllByAssessmentId(assessmentId);
  }

  @Override
  public void updateQuestionsFromStage(String assessmentId) {
    int deletedQuestionsCount = getDeletedQuestionsCount(assessmentId);
    List<AssessmentQuestionStage> assessmentQuestionStageList = assessmentQuestionStageRepository
        .findAllByAssessmentId(assessmentId);
    List<AssessmentQuestionStage> firstXAssessmentQuestionStages;
    if (deletedQuestionsCount == 0) {
      firstXAssessmentQuestionStages = assessmentQuestionStageList
          .stream().limit(10).toList();
    } else {
      firstXAssessmentQuestionStages = assessmentQuestionStageList
          .stream().limit(deletedQuestionsCount).toList();
    }
    List<AssessmentQuestion> assessmentQuestionList = new ArrayList<>();
    firstXAssessmentQuestionStages.forEach(stage -> {
      AssessmentQuestion assessmentQuestion = new AssessmentQuestion();
      assessmentQuestion.setAssessmentId(stage.getAssessmentId());
      assessmentQuestion.setAssessmentStageId(stage.getId());
      assessmentQuestion.setQuestion(stage.getQuestion());
      assessmentQuestion.setAnswer(stage.getAnswer());
      assessmentQuestion.setType(stage.getType());
      assessmentQuestion.setChoices(stage.getChoices());
      assessmentQuestion.setPinned(false);
      assessmentQuestionList.add(assessmentQuestion);
    });
    assessmentQuestionRepository.saveAll(assessmentQuestionList);
  }

  @Override
  public List<AssessmentQuestion> newQuestion(String assessmentId) {
    Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow();
    List<AssessmentQuestionStage> assessmentQuestionStageList = assessmentQuestionStageRepository
        .findAllByAssessmentId(assessmentId);
    AssessmentQuestionStage assessmentQuestionStage = assessmentQuestionStageList.get(
        (int) (assessment.getLastRecommendationId() + 1));
    AssessmentQuestion assessmentQuestion = new AssessmentQuestion();
    assessmentQuestion.setAssessmentId(assessmentQuestionStage.getAssessmentId());
    assessmentQuestion.setAssessmentStageId(assessmentQuestionStage.getId());
    assessmentQuestion.setQuestion(assessmentQuestionStage.getQuestion());
    assessmentQuestion.setAnswer(assessmentQuestionStage.getAnswer());
    assessmentQuestion.setType(assessmentQuestionStage.getType());
    assessmentQuestion.setChoices(assessmentQuestionStage.getChoices());
    assessmentQuestion.setPinned(false);
    assessmentQuestionRepository.save(assessmentQuestion);

    assessment.setLastRecommendationId(assessment.getLastRecommendationId() + 1);
    assessmentRepository.save(assessment);
    return assessmentQuestionRepository.findAllByAssessmentId(assessmentId);
  }

  @Override
  public AssessmentQuestion update(AssessmentQuestion request, String questionId,
      String assessmentId) {
    AssessmentQuestion assessmentQuestion = assessmentQuestionRepository.
        findByAssessmentIdAndId(assessmentId, questionId);
    if (assessmentQuestion == null) {
      assessmentQuestion = new AssessmentQuestion();
    }
    assessmentQuestion.setAssessmentId(request.getAssessmentId());
    assessmentQuestion.setAssessmentStageId(request.getAssessmentStageId());
    assessmentQuestion.setQuestion(request.getQuestion());
    assessmentQuestion.setAnswer(request.getAnswer());
    assessmentQuestion.setType(request.getType());
    assessmentQuestion.setChoices(request.getChoices());
    assessmentQuestion.setPinned(request.getPinned());
    assessmentQuestionRepository.save(assessmentQuestion);
    return assessmentQuestion;
  }

  public int getDeletedQuestionsCount(String assessmentId) {
    int deletedQuestionsCount = 0;
    List<AssessmentQuestion> assessmentQuestions = assessmentQuestionRepository.findAllByAssessmentId(
        assessmentId);
    List<AssessmentQuestion> deletedQuestions = new ArrayList<>();
    for (AssessmentQuestion assessmentQuestion : assessmentQuestions) {
      if (!assessmentQuestion.getPinned()) {
        deletedQuestions.add(assessmentQuestion);
        deletedQuestionsCount++;
      }
    }
    assessmentQuestionRepository.deleteAll(deletedQuestions);
    return deletedQuestionsCount;
  }
}
