package com.talentprobe.domain.testgenie.usecase;

import java.util.List;

public interface UseCaseService {

  List<UseCase> getUseCasesForSuite(String suiteId);

  UseCase createUseCaseForSuite(String suiteId, UseCase useCase);

  UseCase getUseCaseById(String suiteId, String useCaseId);

 UseCase updateUseCase(String suiteId, String useCaseId, UseCase useCaseDetails);

  void deleteUseCase(String suiteId, String useCaseId);
}
