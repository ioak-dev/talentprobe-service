package com.talentprobe.domain.testgenie.testcase;

import com.talentprobe.domain.testgenie.usecase.UseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCaseRepository extends MongoRepository<TestCase,String> {
  Optional<TestCase> findBySuiteIdAndUseCaseId(String suiteId, String usecaseId);

}
