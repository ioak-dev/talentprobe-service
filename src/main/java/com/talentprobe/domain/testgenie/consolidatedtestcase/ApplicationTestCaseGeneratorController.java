package com.talentprobe.domain.testgenie.consolidatedtestcase;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suite")
public class ApplicationTestCaseGeneratorController {

  @Autowired
  private ApplicationTestCaseGeneratorService applicationTestCaseGeneratorService;


  @PostMapping("/{suiteId}/usecase/testcase")
  public void generateConsolidatedTestCases(@PathVariable String suiteId){
    applicationTestCaseGeneratorService.generateConsolidatedTestCase(suiteId);
  }

  @GetMapping("/{suiteId}/usecase/testcase")
  public ResponseEntity<List<ApplicationTestCase>> getAllConsolidatedUseCase(@PathVariable String suiteId){
    return ResponseEntity.ok(applicationTestCaseGeneratorService.getAllConsolidatedTestCase(suiteId));
  }

}
