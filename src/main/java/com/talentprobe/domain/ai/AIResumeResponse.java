package com.talentprobe.domain.ai;

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
public class AIResumeResponse {

  @JsonProperty("Name")
  private String name;

  @JsonProperty("Overview")
  private String overview;

  @JsonProperty("Technical Skills")
  private Map<String, Object> technicalSkills;

  @JsonProperty("Domain Skills")
  private Map<String, Object> domainSkills;

  @JsonProperty("Total Experience")
  private String totalExperience;

  @JsonProperty("Industry Normalized Experience")
  private String industryNormalizedExperience;

  @JsonProperty("Current Designation")
  private String currentDesignation;

  @JsonProperty("Standardized Designation")
  private String standardizedDesignation;

  @JsonProperty("Recent Experience")
  private String recentExperience;

  @JsonProperty("Longest Experience")
  private String longestExperience;

  @JsonProperty("Average Experience per Company")
  private String avgExperiencePerCompany;

  @JsonProperty("All Projects")
  private List<String> allProjects;

  @JsonProperty("Key Project")
  private String keyProject;

  @JsonProperty("Key Questions to be asked")
  private Map<String, Object> questionsToBeAsked;

  @JsonProperty("Education")
  private String education;
}
