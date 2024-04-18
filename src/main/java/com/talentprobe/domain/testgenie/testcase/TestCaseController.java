package com.talentprobe.domain.testgenie.testcase;

import com.talentprobe.domain.assessmentresponse.AssessmentResponse;
import com.talentprobe.domain.testgenie.usecase.UseCase;
import com.talentprobe.domain.testgenie.usecase.UseCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
