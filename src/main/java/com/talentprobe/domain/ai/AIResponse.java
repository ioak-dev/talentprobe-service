package com.talentprobe.domain.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {

  @JsonProperty("question")
  private String question;
  @JsonProperty("choices")
  private List<String> choices;
  @JsonProperty("answer")
  private String answer;
}
