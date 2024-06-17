package com.talentprobe.domain.testgenie.consolidatedtestcase;

import java.util.List;

public interface ApplicationTestCaseGeneratorService {


  void generateConsolidatedTestCase(String suiteId);

  List<ApplicationTestCase> getAllConsolidatedTestCase(String suiteId);

}
