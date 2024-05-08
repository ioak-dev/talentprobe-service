package com.talentprobe.domain.resume;

import java.time.ZonedDateTime;
import java.util.List;
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
  private List<String> technicalSkills;
  private List<String> domainSkills;
  private String totalExperience;
  private String industryNormalizedExperience;
  private String currentDesignation;
  private String standardizedDesignation;
  private String recentExperience;
  private String longestExperience;
  private String avgExperiencePerCompany;
  private String keyProjects;
  private List<String> questionsToBeAsked;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private ZonedDateTime createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private ZonedDateTime lastModifiedDate;
}
