package com.talentprobe.domain.testgenie.suite;

import java.util.List;

public interface SuiteService {

  List<Suite> getAllSuites();

  Suite createSuite(Suite suite);

  void deleteSuite(String id);

  Suite updateSuite(String id, Suite suite);

  void exportSuite(String suiteId);

  Suite getSuiteById(String suiteId);
}
