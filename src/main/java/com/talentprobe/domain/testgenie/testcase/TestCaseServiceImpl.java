package com.talentprobe.domain.testgenie.testcase;

import com.talentprobe.domain.testgenie.gptResponse.GptResponse;
import com.talentprobe.domain.testgenie.gptResponse.GptService;
import com.talentprobe.domain.testgenie.usecase.UseCase;
import com.talentprobe.domain.testgenie.usecase.UseCaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TestCaseServiceImpl implements TestCaseService {

  @Autowired
  private TestCaseRepository testCaseRepository;

  @Autowired
  private GptService gptService;

  @Autowired
  private UseCaseService useCaseService;

  @Override
  public List<TestCase> getTestCaseForSuiteAndUseCase(String suiteId, String usecaseId) {
    UseCase useCase = useCaseService.getUseCaseById(suiteId, usecaseId);
    List<GptResponse> gptResponseList = gptService.getGptResponse(useCase.getDescription());
    List<TestCase> testCases = new ArrayList<>();
    if (!gptResponseList.isEmpty()) {
      gptResponseList.forEach(
          gptResponse -> {
            TestCase testCase = new TestCase();
            testCase.setSuiteId(suiteId);
            testCase.setUseCaseId(usecaseId);
            testCase.setDescription(gptResponse.getDescription());
            testCase.setSummary(gptResponse.getSummary());
            testCase.setComments(gptResponse.getComments());
            testCase.setPriority(gptResponse.getPriority());
            testCase.setComponents(gptResponse.getComponents());
            testCase.setLabels(gptResponse.getLabels());
            testCases.add(testCase);
          }
      );
      testCaseRepository.saveAll(testCases);
    }
    List<TestCase> testCaseList = testCaseRepository.findAllBySuiteIdAndUseCaseId(suiteId, usecaseId);
    return testCaseList;
  }

}
