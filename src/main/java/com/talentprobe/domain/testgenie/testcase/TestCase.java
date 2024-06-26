package com.talentprobe.domain.testgenie.testcase;


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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "testcase")
public class TestCase {

  @Id
  private String id;

  private String suiteId;

  private String useCaseId;

  private TestDescriptionResource description;

  private String serializedDescription;

  private String summary;

  private String priority;

  private String comments;

  private String components;

  private String labels;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private ZonedDateTime createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private ZonedDateTime lastModifiedDate;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class TestDescriptionResource {

    private String overview;

    private List<String> steps;

    private String expectedOutcome;
  }

}
