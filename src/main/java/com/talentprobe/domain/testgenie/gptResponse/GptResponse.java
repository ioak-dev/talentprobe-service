package com.talentprobe.domain.testgenie.gptResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  private String description;

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
}
