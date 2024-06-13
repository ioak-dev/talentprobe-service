package com.talentprobe.domain.testgenie.consolidatedtestcase;

import java.util.List;

public interface ApplicationTestCaseGeneratorService {


  List<ApplicationTestCase> generateConsolidatedTestCase(String suiteId);

}
