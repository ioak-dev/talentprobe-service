package com.talentprobe.domain.testgenie.export;

import com.talentprobe.domain.testgenie.suite.Suite;
import com.talentprobe.domain.testgenie.suite.SuiteService;
import com.talentprobe.domain.testgenie.usecase.UseCase;
import com.talentprobe.domain.testgenie.usecase.UseCaseService;
import java.util.List;
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

@RequestMapping("/api/export-suite/")
@RestController
public class ExportSuiteController {

  @Autowired
  private SuiteService suiteService;

  @GetMapping("/{id}")
  public void exportSuite(@PathVariable String suiteId){
    suiteService.exportSuite(suiteId);
  }

}
