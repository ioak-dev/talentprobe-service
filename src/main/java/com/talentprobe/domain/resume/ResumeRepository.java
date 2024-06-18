package com.talentprobe.domain.resume;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ResumeRepository extends MongoRepository<Resume, String> {


  @Query(value = "{}", fields = "{ 'fileName': 1, 'data': 1}")
  List<Resume> findAllProjected();

  Optional<Resume> findById(String Id);


}
