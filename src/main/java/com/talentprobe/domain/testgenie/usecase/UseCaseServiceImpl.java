package com.talentprobe.domain.testgenie.usecase;

import com.talentprobe.domain.testgenie.testcase.TestCase;
import com.talentprobe.domain.testgenie.testcase.TestCaseRepository;
import com.talentprobe.domain.testgenie.testcase.TestCaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class UseCaseServiceImpl implements UseCaseService {

  private static final String ERROR_RESPONSE = "Use case does not exists";

  @Autowired
  private UseCaseRepository useCaseRepository;

  @Autowired
  private TestCaseService testCaseService;

  @Autowired
  private TestCaseRepository testCaseRepository;

  @Override
  public List<UseCase> getUseCasesForSuite(String suiteId) {
    return useCaseRepository.findBySuiteId(suiteId);
  }

  @Override
  public List<TestCase> createUseCaseForSuite(String suiteId, UseCase useCase) {
    log.info("Creating Use case for suiteId "+suiteId);
    if (useCase.getDescription() != null && !useCase.getDescription().isEmpty()) {
      UseCase request = new UseCase();
      request.setId(useCase.getId());
      request.setUseCaseName(useCase.getUseCaseName());
      request.setDescription(useCase.getDescription());
      request.setSuiteId(suiteId);
      UseCase responseUsecase = useCaseRepository.save(request);
      log.info("Generating test cases for use case");
      return testCaseService.getTestCaseForSuiteAndUseCase(suiteId, responseUsecase.getId(),
          responseUsecase.getDescription());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Use case description can not null");
    }
  }

  @Override
  public UseCase getUseCaseById(String suiteId, String useCaseId) {
    Optional<UseCase> useCase = useCaseRepository.findByIdAndSuiteId(useCaseId, suiteId);
    return useCase.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE));
  }

  @Override
  public List<TestCase> updateUseCase(String suiteId, String useCaseId, UseCase useCaseDetails) {
    log.info("Updating use case for id "+ useCaseId);
    Optional<UseCase> existingUseCase = useCaseRepository.findByIdAndSuiteId(useCaseId, suiteId);
    if (!existingUseCase.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE);
    }
    UseCase usecase = existingUseCase.get();
    List<TestCase> responseTestCasesList = new ArrayList<>();
    usecase.setUseCaseName(useCaseDetails.getUseCaseName());
    if (useCaseDetails.getDescription() != null &&
        !usecase.getDescription().equals(useCaseDetails.getDescription())) {
      usecase.setDescription(useCaseDetails.getDescription());
      testCaseRepository.deleteAllBySuiteIdAndUseCaseId(suiteId,useCaseId);
      log.info("Generating test cases for use case");
      responseTestCasesList = testCaseService.getTestCaseForSuiteAndUseCase(suiteId, useCaseId,
          useCaseDetails.getDescription());
    } else {
      responseTestCasesList = testCaseRepository.findAllBySuiteIdAndUseCaseId(suiteId, useCaseId);
    }
    useCaseRepository.save(usecase);
    return responseTestCasesList;
  }

  @Override
  public void deleteUseCase(String suiteId, String useCaseId) {
    if (!useCaseRepository.existsByIdAndSuiteId(useCaseId, suiteId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE);
    }
    log.info("Deleting the use case for id "+useCaseId);
    useCaseRepository.deleteByIdAndSuiteId(useCaseId, suiteId);
  }

}
