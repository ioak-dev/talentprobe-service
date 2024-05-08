package com.talentprobe.domain.resume;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ResumeRepository extends MongoRepository<Resume, String> {


  @Query(value = "{}", fields = "{ 'name': 1, 'technicalSkills': 1, 'domainSkills': 1, 'totalExperience': 1}")
  List<Resume> findAllProjected();


}
