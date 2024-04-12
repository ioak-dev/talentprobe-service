package com.talentprobe.domain.testgenie.usecase;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UseCaseRepository extends MongoRepository<UseCase,String> {

  List<UseCase> findBySuiteId(String suiteId);

  void deleteByIdAndSuiteId(String useCaseId, String suiteId);

  Optional<UseCase> findByIdAndSuiteId(String useCaseId,String suiteId);

  boolean existsByIdAndSuiteId(String useCaseId, String suiteId);

}
