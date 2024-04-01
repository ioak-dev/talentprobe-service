package com.talentprobe.domain.assessment;

import java.util.List;

public interface AssessmentService {

  List<Assessment> getAllAssessments();

  Assessment createAndUpdate(Assessment assessment);

  Assessment getById(String id);

  void delete(String id);

}
