package com.talentprobe.domain.testgenie.gptResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GptResponse {

  private String description;

  private String summary;

  private String comments;

  private String components;
}
