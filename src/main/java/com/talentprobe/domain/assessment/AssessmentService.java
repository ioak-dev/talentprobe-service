package com.talentprobe.domain.assessment;

import java.util.List;

public interface AssessmentService {

  List<Assessment> getAllAssessments();

  Assessment create(Assessment assessment);

  Assessment update(Assessment request, String id);

  Assessment getById(String id);

  void delete(String id);

}
