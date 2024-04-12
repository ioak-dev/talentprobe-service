package com.talentprobe.domain.testgenie.suite;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SuiteServiceImpl implements SuiteService{

  @Autowired
  private SuiteRepository suiteRepository;

  @Override
  public List<Suite> getAllSuites() {
    return suiteRepository.findAll();
  }

  @Override
  public Suite createSuite(Suite request) {
      return suiteRepository.save(request);
  }

  @Override
  public void deleteSuite(String id) {
    if(id != null) {
      suiteRepository.deleteById(id);
    }
  }

  @Override
  public Suite updateSuite(String id, Suite requestSuite) {
    if(id !=null){
      Suite existingSuite= suiteRepository.findById(id).orElseThrow(()->
          new ResponseStatusException(HttpStatus.NOT_FOUND,"Suite Not Found"));
      existingSuite.setName(requestSuite.getName());
     return suiteRepository.save(existingSuite);
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id can not be null");
  }
}
