package com.talentprobe.domain.testgenie.suite;

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

@RestController
@RequestMapping("/api/suite")
public class SuiteController {

  @Autowired
  private SuiteService suiteService;

  @GetMapping
  public ResponseEntity<List<Suite>> getAllSuites(){
    return ResponseEntity.ok(suiteService.getAllSuites());
  }

  @PostMapping
  public ResponseEntity<Suite> createSuite(@RequestBody Suite suite){
   return ResponseEntity.ok(suiteService.createSuite(suite));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Suite> updateSuite(@PathVariable String id,@RequestBody Suite suite){
    return ResponseEntity.ok(suiteService.updateSuite(id,suite));
  }

  @DeleteMapping("/{id}")
  public void deleteSuite(@PathVariable String id){
     suiteService.deleteSuite(id);
  }

}
