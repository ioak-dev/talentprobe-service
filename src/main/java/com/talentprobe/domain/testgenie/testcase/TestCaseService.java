package com.talentprobe.domain.testgenie.testcase;

import com.talentprobe.domain.testgenie.usecase.UseCase;
import java.util.List;

public interface TestCaseService {

  TestCase getTestCaseForSuiteAndUseCase(String suiteId, String usecaseId);

}
