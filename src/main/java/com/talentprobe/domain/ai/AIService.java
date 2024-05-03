package com.talentprobe.domain.ai;

import com.talentprobe.domain.ai.AIServiceImpl.AiSkillSetResponse;
import java.util.List;

public interface AIService {

  List<AIResponse> getAIResponse(String jobDescription,
      int noOfQues);

  AiSkillSetResponse getAISkillSetResponse(String jobDescription, int numberOfSkills);
}
