package com.talentprobe.domain.testgenie.testcase;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCaseRepository extends MongoRepository<TestCase,String> {
  List<TestCase> findAllBySuiteIdAndUseCaseId(String suiteId, String usecaseId);

  Optional<TestCase> findBySuiteId(String suiteId);

}
