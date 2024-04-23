package com.talentprobe.domain.testgenie.testcase;

import com.talentprobe.domain.testgenie.gptResponse.GptResponse;
import com.talentprobe.domain.testgenie.gptResponse.GptService;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired
  private GptService gptService;

  @Override
  public TestCase getTestCaseForSuiteAndUseCase(String suiteId, String usecaseId) {
    List<GptResponse> gptResponseList = gptService.getGptResponse("");
    List<TestCase> testCases = new ArrayList<>();
    gptResponseList.forEach(
        gptResponse->{
          TestCase testCase = new TestCase();
          testCase.setDescription(gptResponse.getDescription());
          testCase.setComponents(gptResponse.getComponents());
          testCase.setSummary(gptResponse.getSummary());
          testCases.add(testCase);
        }
    );
    testCaseRepository.saveAll(testCases);
    Optional<TestCase> testCase = testCaseRepository.findBySuiteIdAndUseCaseId(suiteId, usecaseId);
    return testCase.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,ERROR_RESPONSE));
  }

}
