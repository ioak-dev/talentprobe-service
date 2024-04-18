package com.talentprobe.domain.testgenie.testcase;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TestCaseServiceImpl implements TestCaseService {

  private static final String ERROR_RESPONSE="Test case does not exists";

  @Autowired
  private TestCaseRepository testCaseRepository;

  @Override
  public TestCase getTestCaseForSuiteAndUseCase(String suiteId, String usecaseId) {
    Optional<TestCase> testCase = testCaseRepository.findBySuiteIdAndUseCaseId(suiteId, usecaseId);
    return testCase.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,ERROR_RESPONSE));
  }

}
