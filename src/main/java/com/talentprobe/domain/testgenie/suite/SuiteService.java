package com.talentprobe.domain.testgenie.suite;

import com.talentprobe.domain.testgenie.export.ExportMode;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface SuiteService {

  List<Suite> getAllSuites();

  Suite createSuite(Suite suite);

  void deleteSuite(String id);

  Suite updateSuite(String id, Suite suite);

  ResponseEntity<Object> exportSuite(String suiteId, ExportMode type);

  Suite getSuiteById(String suiteId);
}
