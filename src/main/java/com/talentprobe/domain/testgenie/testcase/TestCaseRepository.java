package com.talentprobe.domain.testgenie.testcase;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCaseRepository extends MongoRepository<TestCase,String> {
  List<TestCase> findAllBySuiteIdAndUseCaseId(String suiteId, String usecaseId);

  List<TestCase> findAllBySuiteId(String suiteId);

  void deleteAllBySuiteIdAndUseCaseId(String suiteId,String usecaseId);

}
