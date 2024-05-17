package com.talentprobe.domain.testgenie.testcase;

import com.talentprobe.domain.testgenie.gptResponse.GptResponse;
import com.talentprobe.domain.testgenie.gptResponse.GptService;
import com.talentprobe.domain.testgenie.testcase.TestCase.TestDescriptionResource;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestCaseServiceImpl implements TestCaseService {

  @Autowired
  private TestCaseRepository testCaseRepository;

  @Autowired
  private GptService gptService;


  @Override
  public void constructTestCaseFromGptResponse(String suiteId, String usecaseId,String description) {
    List<GptResponse> gptResponseList = gptService.getGptResponse(description);
    log.info("Successfully generated test cases for use case");
    List<TestCase> testCases = new ArrayList<>();
    if (!gptResponseList.isEmpty()) {
      gptResponseList.forEach(
          gptResponse -> {
            TestDescriptionResource testDescriptionResource=new TestDescriptionResource();
            TestCase testCase = new TestCase();
            testCase.setSuiteId(suiteId);
            testCase.setUseCaseId(usecaseId);
            testDescriptionResource.setOverview(gptResponse.getDescription().getOverview());
            testDescriptionResource.setSteps(gptResponse.getDescription().getSteps());
            testDescriptionResource.setExpectedOutcome(gptResponse.getDescription().getExpectedOutcome());
            testCase.setDescription(testDescriptionResource);
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
  }


  public List<TestCase> getTestCasesForSuiteIdAndUseCaseId(String suiteId, String usecaseId){
    List<TestCase> testCaseList = testCaseRepository.findAllBySuiteIdAndUseCaseId(suiteId, usecaseId);
    return testCaseList;
  }

}
