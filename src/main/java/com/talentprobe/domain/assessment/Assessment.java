package com.talentprobe.domain.assessment;

import java.time.ZonedDateTime;
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
@Document(collection = "assessment")
public class Assessment {

  @Id
  private String id;
  private String name;
  private String jobDescription;
  private String duration;
  private Status status;
  private long lastRecommendationNumber;
  private String skillSet;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private ZonedDateTime createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private ZonedDateTime lastModifiedDate;

}
