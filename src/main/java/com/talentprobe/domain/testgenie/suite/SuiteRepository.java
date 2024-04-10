package com.talentprobe.domain.testgenie.suite;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuiteRepository extends MongoRepository<Suite,String> {

}
