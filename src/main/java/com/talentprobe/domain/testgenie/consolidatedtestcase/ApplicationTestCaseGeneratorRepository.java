package com.talentprobe.domain.testgenie.consolidatedtestcase;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationTestCaseGeneratorRepository extends MongoRepository<ApplicationTestCase,String> {


  boolean existsBySuiteId(String suiteId);

  List<ApplicationTestCase> findAllBySuiteId(String suiteId);

  void deleteAllBySuiteId(String suiteId);
}
