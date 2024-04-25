package com.talentprobe.domain.testgenie.export;

import com.talentprobe.domain.testgenie.suite.SuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/export-suite/")
@RestController
public class ExportSuiteController {

  @Autowired
  private SuiteService suiteService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> exportSuite(@PathVariable String id, @RequestParam(value = "type",defaultValue = "CSV") ExportMode type){
   return suiteService.exportSuite(id,type);
  }

}
