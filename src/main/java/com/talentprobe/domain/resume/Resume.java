package com.talentprobe.domain.resume;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resume")
public class Resume {

  @Id
  private String id;
  private String name;
  private byte[] attachment;
  private String overview;
  private Map<String, Object> technicalSkills;
  private Map<String, Object> domainSkills;
  private String totalExperience;
  private String industryNormalizedExperience;
  private String currentDesignation;
  private String standardizedDesignation;
  private String recentExperience;
  private String longestExperience;
  private String avgExperiencePerCompany;
  private List<String> allProjects;
  private String keyProject;
  private Map<String, Object> questionsToBeAsked;
  private String education;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private ZonedDateTime createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private ZonedDateTime lastModifiedDate;
}
