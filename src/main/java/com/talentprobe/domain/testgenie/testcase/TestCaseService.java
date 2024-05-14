package com.talentprobe.domain.testgenie.testcase;

import java.util.List;

public interface TestCaseService {

  List<TestCase> getTestCasesForSuiteIdAndUseCaseId(String suiteId, String usecaseId);

  void constructTestCaseFromGptResponse(String suiteId, String usecaseId,String description);

}
