package com.talentprobe.domain.testgenie.gptResponse;

import java.util.List;

public interface GptService {

  List<GptResponse> getGptResponse(String description);
}
