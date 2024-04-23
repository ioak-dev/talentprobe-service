package com.talentprobe.domain.testgenie.testcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/suite")
@RestController
public class TestCaseController {

  @Autowired
  private TestCaseService testCaseService;

  @GetMapping("/{suiteId}/usecase/{usecaseId}/testcase")
  public TestCase getTestCase(@PathVariable String suiteId,@PathVariable String usecaseId){
    return testCaseService.getTestCaseForSuiteAndUseCase(suiteId,usecaseId);
  }
}
