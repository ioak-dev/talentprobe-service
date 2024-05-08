package com.talentprobe.domain.resume;

import com.talentprobe.domain.ai.AIService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResumeServiceImpl implements ResumeService {

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
    return resumeRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume Not found"));

  }

  @Override
  public Resume scanResume(MultipartFile file) {
    String extractedText = null;
    byte[] fileBytes = null;
    Resume resume;
    try (InputStream inputStream = file.getInputStream()) {
      if (file.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "File is empty");
      }
      fileBytes = file.getBytes();

     /* ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        bos.write(buffer, 0, bytesRead);
      }
      fileBytes = bos.toByteArray();*/

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
      //   AIResumeResponse aiResumeResponse = aiService.getAIResumeKeypoints(extractedText);
      // fetch the needed details and save in DB
      resume = new Resume();
      resume.setName(file.getOriginalFilename());
      resume.setAttachment(fileBytes);
      resumeRepository.save(resume);
    } catch (IOException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          exception.getMessage());
    }
    return null;
  }
}
