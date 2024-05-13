package com.talentprobe.domain.testgenie.testcase;

import java.util.List;

public interface TestCaseService {

  List<TestCase> getTestCaseForSuiteAndUseCase(String suiteId, String usecaseId,String description);

}
