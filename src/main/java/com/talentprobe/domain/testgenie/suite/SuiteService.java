package com.talentprobe.domain.testgenie.suite;

import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface SuiteService {

  List<Suite> getAllSuites();

  Suite createSuite(Suite suite);

  void deleteSuite(String id);

  Suite updateSuite(String id, Suite suite);

  ResponseEntity<ByteArrayResource> exportSuite(String suiteId);

  Suite getSuiteById(String suiteId);
}
