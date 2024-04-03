package com.talentprobe.domain.ai;

import java.util.List;

public interface AIService {

  List<AIResponse> getAIResponse(String jobDescription);

}
