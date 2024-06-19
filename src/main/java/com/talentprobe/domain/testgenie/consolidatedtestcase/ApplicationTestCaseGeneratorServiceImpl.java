package com.talentprobe.domain.testgenie.consolidatedtestcase;

import com.talentprobe.domain.testgenie.gptResponse.GptResponse;
import com.talentprobe.domain.testgenie.gptResponse.GptService;
import com.talentprobe.domain.testgenie.suite.SuiteService;
import com.talentprobe.domain.testgenie.testcase.TestCase.TestDescriptionResource;
import com.talentprobe.domain.testgenie.usecase.UseCase;
import com.talentprobe.domain.testgenie.usecase.UseCaseRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class ApplicationTestCaseGeneratorServiceImpl implements
    ApplicationTestCaseGeneratorService {

  @Autowired
  private UseCaseRepository useCaseRepository;

  @Autowired
  private GptService gptService;

  @Autowired
  private SuiteService suiteService;

  @Autowired
  private ApplicationTestCaseGeneratorRepository applicationTestCaseGeneratorRepository;

  @Override
  public void generateConsolidatedTestCase(String suiteId) {
    List<UseCase> useCaseList = useCaseRepository.findBySuiteId(suiteId);
    StringBuilder constructUseCaseDescription = new StringBuilder();
    if (useCaseList == null || useCaseList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No use case found for the suite id");
    }
    useCaseList.forEach(
        useCase -> constructUseCaseDescription.append(useCase.getDescription()).append("\n"));
    List<GptResponse> gptResponseList = gptService.getGptResponseForConsolidatedUseCase(
        constructUseCaseDescription.toString().trim());
    log.info("Successfully generated test cases for application use case");
    applicationTestCaseGeneratorRepository.deleteAllBySuiteId(suiteId);
    log.info("Flushed all the old data and saving new one");
    List<ApplicationTestCase> applicationTestCaseList = new ArrayList<>();
    if (!gptResponseList.isEmpty()) {
      gptResponseList.forEach(
          gptResponse -> {
            TestDescriptionResource testDescriptionResource = new TestDescriptionResource();
            ApplicationTestCase testCase = new ApplicationTestCase();
            testCase.setSuiteId(suiteId);
            testDescriptionResource.setOverview(gptResponse.getDescription().getOverview());
            testDescriptionResource.setSteps(gptResponse.getDescription().getSteps());
            testDescriptionResource.setExpectedOutcome(
                gptResponse.getDescription().getExpectedOutcome());
            testCase.setSerializedDescription(
                suiteService.buildDescriptionFromGptResponse(testDescriptionResource));
            testCase.setDescription(testDescriptionResource);
            testCase.setSummary(gptResponse.getSummary());
            testCase.setComments(gptResponse.getComments());
            testCase.setPriority(gptResponse.getPriority());
            testCase.setComponents(gptResponse.getComponents());
            testCase.setLabels(gptResponse.getLabels());
            applicationTestCaseList.add(testCase);
          }
      );
      applicationTestCaseGeneratorRepository.saveAll(applicationTestCaseList);
    }
  }

  @Override
  public List<ApplicationTestCase> getAllConsolidatedTestCase(String suiteId) {
    log.info("Retrieve all the testcase from database");
    if (!applicationTestCaseGeneratorRepository.existsBySuiteId(suiteId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Test cases not found for the suite: " + suiteId);
    }
    return applicationTestCaseGeneratorRepository.findAllBySuiteId(suiteId);
  }
}
