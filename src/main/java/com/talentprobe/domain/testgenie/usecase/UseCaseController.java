package com.talentprobe.domain.testgenie.usecase;

import com.talentprobe.domain.testgenie.testcase.TestCase;
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

@RequestMapping("/api/suite")
@RestController
public class UseCaseController {


  @Autowired
  private UseCaseService useCaseService;

  @GetMapping("/{id}/usecase")
  public ResponseEntity<List<UseCase>> getAllUseCases(@PathVariable String id){
    return ResponseEntity.ok(useCaseService.getUseCasesForSuite(id));
  }

  @PostMapping("/{id}/usecase")
  public ResponseEntity<List<TestCase>> createUseCase(@PathVariable String id,
      @RequestBody UseCase useCase){
    return ResponseEntity.ok(useCaseService.createUseCaseForSuite(id,useCase));
  }

  @PutMapping("/{id}/usecase/{usecaseid}")
  public ResponseEntity<List<TestCase>> updateUseCase(@PathVariable String id,
      @PathVariable String usecaseid,@RequestBody UseCase useCase){
    return ResponseEntity.ok(useCaseService.updateUseCase(id,usecaseid,useCase));
  }

  @DeleteMapping("/{id}/usecase/{usecaseid}")
  public void deleteUseCase(@PathVariable String id,@PathVariable String usecaseid){
    useCaseService.deleteUseCase(id,usecaseid);
  }

  @GetMapping("/{id}/usecase/{usecaseid}")
  public UseCase getUseCaseById(@PathVariable String id,@PathVariable String usecaseid){
   return useCaseService.getUseCaseById(id,usecaseid);
  }


}
