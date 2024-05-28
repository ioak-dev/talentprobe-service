package com.talentprobe.domain.ai;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AIResumeResponse {

  @JsonProperty("name")
  private String name;

  @JsonProperty("overview")
  private String overview;

  @JsonProperty("technicalSkills")
  private Map<String, Object> technicalSkills;

  @JsonProperty("domainSkills")
  private Map<String, Object> domainSkills;

  @JsonProperty("totalExperience")
  private String totalExperience;

  @JsonProperty("industryNormalizedExperience")
  private String industryNormalizedExperience;

  @JsonProperty("currentDesignation")
  private String currentDesignation;

  @JsonProperty("standardizedDesignation")
  private String standardizedDesignation;

  @JsonProperty("recentExperience")
  private String recentExperience;

  @JsonProperty("longestExperience")
  private String longestExperience;

  @JsonProperty("averageExperiencePerCompany")
  private String avgExperiencePerCompany;

  @JsonProperty("allProjects")
  private List<String> allProjects;

  @JsonProperty("keyProject")
  private String keyProject;

  @JsonProperty("keyQuestionsToBeAsked")
  private Map<String, Object> questionsToBeAsked;

  @JsonProperty("education")
  private String education;

  private Object output;
}
