package com.talentprobe.domain.testgenie.usecase;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UseCaseServiceImpl implements UseCaseService{

  private static final String ERROR_RESPONSE="Use case does not exists";

  @Autowired
  private UseCaseRepository useCaseRepository;

  @Override
  public List<UseCase> getUseCasesForSuite(String suiteId) {
    return useCaseRepository.findBySuiteId(suiteId);
  }

  @Override
  public UseCase createUseCaseForSuite(String suiteId, UseCase useCase) {
    UseCase request=new UseCase();
    request.setId(useCase.getId());
    request.setDescription(useCase.getDescription());
    request.setSuiteId(suiteId);
    return useCaseRepository.save(request);
  }

  @Override
  public UseCase getUseCaseById(String suiteId, String useCaseId) {
    Optional<UseCase> useCase = useCaseRepository.findByIdAndSuiteId(useCaseId, suiteId);
    return useCase.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,ERROR_RESPONSE));
  }

  @Override
  public UseCase updateUseCase(String suiteId, String useCaseId, UseCase useCaseDetails) {
    Optional<UseCase> existingUseCase = useCaseRepository.findByIdAndSuiteId(useCaseId, suiteId);
    if (!existingUseCase.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,ERROR_RESPONSE);
    }
    UseCase usecase = existingUseCase.get();
    usecase.setDescription(useCaseDetails.getDescription());
    return useCaseRepository.save(usecase);
  }

  @Override
  public void deleteUseCase(String suiteId, String useCaseId) {
    if (!useCaseRepository.existsByIdAndSuiteId(useCaseId, suiteId)) {
     throw new ResponseStatusException(HttpStatus.NOT_FOUND,ERROR_RESPONSE);
    }
    useCaseRepository.deleteByIdAndSuiteId(useCaseId, suiteId);
  }

}
