package com.talentprobe.domain.ai;

import java.util.List;

public interface AIService {

  AIResponse getAIResponse(String jobDescription,
      int noOfQues);

  List<String> getAISkillSetResponse(String jobDescription, int numberOfSkills);

  AIResumeResponse getAIResumeKeypoints(String content);
}
