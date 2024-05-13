package com.talentprobe.domain.testgenie.usecase;

import com.talentprobe.domain.testgenie.testcase.TestCase;
import java.util.List;

public interface UseCaseService {

  List<UseCase> getUseCasesForSuite(String suiteId);

  List<TestCase> createUseCaseForSuite(String suiteId, UseCase useCase);

  UseCase getUseCaseById(String suiteId, String useCaseId);

  List<TestCase> updateUseCase(String suiteId, String useCaseId, UseCase useCaseDetails);

  void deleteUseCase(String suiteId, String useCaseId);
}
