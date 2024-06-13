package com.talentprobe.domain.testgenie.consolidatedtestcase;

import com.talentprobe.domain.testgenie.testcase.TestCase.TestDescriptionResource;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
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
@Document(collection = "application.testcase")
public class ApplicationTestCase {

  @Id
  private String id;

  private String suiteId;

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


}
