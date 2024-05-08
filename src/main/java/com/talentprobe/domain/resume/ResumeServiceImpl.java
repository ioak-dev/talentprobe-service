package com.talentprobe.domain.resume;

import com.talentprobe.domain.ai.AIResumeResponse;
import com.talentprobe.domain.ai.AIService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResumeServiceImpl implements ResumeService {

  private static final String ERROR_RESPONSE = "Resume not found";
  @Autowired
  AIService aiService;
  @Autowired
  private ResumeRepository resumeRepository;

  @Override
  public List<Resume> getAllResumes() {
    return resumeRepository.findAllProjected();
  }

  @Override
  public Resume getResumeById(String id) {
    Optional<Resume> resume = resumeRepository.findById(id);
    return resume.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE));
  }

  @Override
  public Resume scanResume(MultipartFile file) {
    String extractedText = null;
    byte[] fileBytes = null;
    Resume resume = new Resume();
    try (InputStream inputStream = file.getInputStream()) {
      if (file.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "File is empty");
      }
      fileBytes = file.getBytes();

      if (file.getOriginalFilename().endsWith(".pdf")) {
        try (PDDocument document = PDDocument.load(fileBytes)) {
          PDFTextStripper stripper = new PDFTextStripper();
          extractedText = stripper.getText(document);
        }
      } else if (file.getOriginalFilename().endsWith(".docx")) {
        try (InputStream inStream = file.getInputStream()) {
          XWPFDocument document = new XWPFDocument(inStream);
          StringBuilder builder = new StringBuilder();
          document.getParagraphs().forEach(paragraph -> {
            builder.append(paragraph.getText());
            builder.append("\n");
          });
          extractedText = builder.toString();
        }
      } else {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
            "Only .pdf and .docx files are supported");
      }
      AIResumeResponse aiResumeResponse = aiService.getAIResumeKeypoints(extractedText);
      // Create the Database entity and save in DB
      mapDatabaseEntityFromAIResponse(aiResumeResponse, resume);
      resume.setAttachment(fileBytes);
      resumeRepository.save(resume);
    } catch (IOException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          exception.getMessage());
    }
    return resume;
  }

  private void mapDatabaseEntityFromAIResponse(AIResumeResponse aiResumeResponse, Resume resume) {
    resume.setName(aiResumeResponse.getName());
    resume.setOverview(aiResumeResponse.getOverview());
    resume.setTechnicalSkills(aiResumeResponse.getTechnicalSkills());
    resume.setDomainSkills(aiResumeResponse.getDomainSkills());
    resume.setTotalExperience(aiResumeResponse.getTotalExperience());
    resume.setIndustryNormalizedExperience(aiResumeResponse.getIndustryNormalizedExperience());
    resume.setCurrentDesignation(aiResumeResponse.getCurrentDesignation());
    resume.setStandardizedDesignation(aiResumeResponse.getStandardizedDesignation());
    resume.setRecentExperience(aiResumeResponse.getRecentExperience());
    resume.setLongestExperience(aiResumeResponse.getLongestExperience());
    resume.setAvgExperiencePerCompany(aiResumeResponse.getAvgExperiencePerCompany());
    resume.setKeyProjects(aiResumeResponse.getKeyProjects());
    resume.setQuestionsToBeAsked(aiResumeResponse.getQuestionsToBeAsked());

  }

  @Override
  public ResponseEntity<Object> downloadResume(String id) {
    Optional<Resume> resumeData = resumeRepository.findById(id);
    if (!resumeData.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RESPONSE);
    }
    Resume resume = resumeData.get();
    byte[] attachment = resume.getAttachment();
    String extension = detectFileExtension(attachment);
    String fileName = resume.getName() + extension;
    MediaType mediaType = "pdf".equals(extension) ? MediaType.APPLICATION_PDF :
        MediaType.APPLICATION_OCTET_STREAM;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentDispositionFormData("attachment", fileName);
    headers.setContentType(mediaType);

    return ResponseEntity.ok()
        .headers(headers)
        .contentLength(attachment.length)
        .body(new ByteArrayResource(attachment));
  }


  private String detectFileExtension(byte[] data) {
    String extension = null;
    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
      new XWPFDocument(byteArrayInputStream);
      extension = ".docx";
    } catch (Exception e) {
     /* try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
          PDDocument document = PDDocument.load(bis)) {*/
      extension = ".pdf"; // Successfully loaded, it's a PDF
    }
    return extension;
  }

}
