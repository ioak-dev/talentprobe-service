package com.talentprobe.domain.testgenie.gptResponse;

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
public class GptResponse {

  @JsonProperty("description")
  private GptDescriptionResponse description;

  @JsonProperty("summary")
  private String summary;

  @JsonProperty("priority")
  private String priority;

  @JsonProperty("comments")
  private String comments;

  @JsonProperty("components")
  private String components;

  @JsonProperty("labels")
  private String labels;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class GptDescriptionResponse {

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("steps")
    private List<String> steps;

    @JsonProperty("expectedOutcome")
    private String expectedOutcome;

  }
}
