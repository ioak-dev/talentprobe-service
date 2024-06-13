package com.talentprobe.domain.testgenie.consolidatedtestcase;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationTestCaseGeneratorRepository extends MongoRepository<ApplicationTestCase,String> {

}
