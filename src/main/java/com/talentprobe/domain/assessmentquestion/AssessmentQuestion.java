package com.talentprobe.domain.assessmentquestion;

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
@Document(collection = "assessmentquestion")
public class AssessmentQuestion {

  @Id
  private String id;
  private String assessmentId;
  private String assessmentStageId;
  private Type type;
  private String question;
  private String answer;
  private List<String> choices;
  private String pinned;


  @CreatedBy
  private String createdBy;

  @CreatedDate
  private ZonedDateTime createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private ZonedDateTime lastModifiedDate;
}
