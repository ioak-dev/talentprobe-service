package com.talentprobe.domain.testgenie.suite;

import static com.talentprobe.domain.testgenie.testcase.CsvHeaderNames.comments;
import static com.talentprobe.domain.testgenie.testcase.CsvHeaderNames.components;
import static com.talentprobe.domain.testgenie.testcase.CsvHeaderNames.description;
import static com.talentprobe.domain.testgenie.testcase.CsvHeaderNames.labels;
import static com.talentprobe.domain.testgenie.testcase.CsvHeaderNames.priority;
import static com.talentprobe.domain.testgenie.testcase.CsvHeaderNames.summary;

import com.talentprobe.domain.testgenie.testcase.TestCase;
import com.talentprobe.domain.testgenie.testcase.TestCaseRepository;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SuiteServiceImpl implements SuiteService {

  private static final String ERROR_RESPONSE = "Suite does not exists";

  private static final String HEADER_NAME = "Content-Disposition";
  private static final String HEADER_VALUES = "attachment; filename=";

  private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";


  @Autowired
  private SuiteRepository suiteRepository;

  @Autowired
  private TestCaseRepository testCaseRepository;

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
    if (id != null) {
      suiteRepository.deleteById(id);
    }
  }

  @Override
  public Suite updateSuite(String id, Suite requestSuite) {
    if (id != null) {
      Suite existingSuite = suiteRepository.findById(id).orElseThrow(() ->
          new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE));
      existingSuite.setName(requestSuite.getName());
      return suiteRepository.save(existingSuite);
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id can not be null");
  }

  @Override
  public ResponseEntity<ByteArrayResource> exportSuite(String suiteId) {
    try {
      Path tempDirPath = Files.createTempDirectory("TestcaseData");
      String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
      String fileName = "testcase_" + timestamp + ".csv";
      Path filePath = tempDirPath.resolve(fileName);
      try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8);
          CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
        TestCase testCase = testCaseRepository.findBySuiteId(suiteId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE));
        csvPrinter.printRecord(computeStaticHeaders());
        csvPrinter.printRecord(computeCsvRecords(testCase));
        csvPrinter.flush();
      }
      byte[] fileContent = Files.readAllBytes(filePath);
      ByteArrayResource resource = new ByteArrayResource(fileContent);
      return ResponseEntity.ok()
          .header(HEADER_NAME, HEADER_VALUES + fileName).contentLength(fileContent.length)
          .contentType(MediaType.parseMediaType("text/csv")).body(resource);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  private List<String> computeStaticHeaders() {
    return List.of(description.toString(),
        summary.toString(),
        priority.toString(),
        comments.toString(),
        components.toString(),
        labels.toString());
  }

  private List<String> computeCsvRecords(TestCase testCase) {
    List<String> records = new ArrayList<>();
    records.add(testCase.getDescription());
    records.add(testCase.getSummary());
    records.add(testCase.getPriority());
    records.add(testCase.getComments());
    records.add(testCase.getComponents());
    records.add(testCase.getLabels());
    return records;
  }

  @Override
  public Suite getSuiteById(String suiteId) {
    Optional<Suite> suite = suiteRepository.findById(suiteId);
    return suite.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE));
  }
}
