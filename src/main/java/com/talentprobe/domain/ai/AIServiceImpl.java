package com.talentprobe.domain.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStageService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AIServiceImpl implements AIService {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private AssessmentQuestionStageService assessmentQuestionStageService;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${gpt.accessKey}")
  private String gptAccessKey;

  @Value("${gpt.url}")
  private String url;

  @Override
  public List<AIResponse> getAIResponse(String jobDescription,
      int noOfQues) {

    List<AIResponse> aiResponseList = new ArrayList<>();
    try {
      HttpEntity<String> entity = createHttpEntity(jobDescription,noOfQues);
      log.info("Making gpt call for questions generation");
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
     /* Resource resource = resourceLoader.getResource("classpath:Gpt_Mock_Response.json");
      ResponseEntity<Object> responseEntity = ResponseEntity
          .ok()
          .header("header", "value")
          .body(StreamUtils.copyToString(resource.getInputStream(),
              StandardCharsets.UTF_8));*/
      aiResponseList = mapToAIResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT,
          exception.getMessage());
    }
    return aiResponseList;
  }

  @Override
  public List<String> getAISkillSetResponse(String jobDescription, int numberOfSkills) {
    List<String> aiSkillSetResponse;
    try {
      HttpEntity<String> entity = createHttpEntityForSkillSet(jobDescription, numberOfSkills);
/*      Resource resource = resourceLoader.getResource("classpath:Gpt_Mock_Response_SkillSet.json");
      ResponseEntity<Object> responseEntity = ResponseEntity
          .ok()
          .header("header", "value")
          .body(StreamUtils.copyToString(resource.getInputStream(),
              StandardCharsets.UTF_8));*/
      log.info("Making gpt call for skill set generation");
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
      aiSkillSetResponse = mapToSkillSetResponse(responseEntity.getBody());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return aiSkillSetResponse;
  }

  @Override
  public AIResumeResponse getAIResumeKeypoints(String content) {
    AIResumeResponse resumeSummaryResource;
    try {
      HttpEntity<String> entity = createHttpEntityForResumeScan(content);
      log.info("Making gpt call for resume key points generation");
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
      /*Resource resource = resourceLoader.getResource("classpath:Gpt_Mock_Response_resumeScan.json");
      ResponseEntity<Object> responseEntity = ResponseEntity
          .ok()
          .header("header", "value")
          .body(StreamUtils.copyToString(resource.getInputStream(),
              StandardCharsets.UTF_8));*/
      resumeSummaryResource = mapToResumeScreeningResponse(responseEntity.getBody());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return resumeSummaryResource;
  }


  private HttpEntity<String> createHttpEntityForSkillSet(String jobDescription, int numberOfSkills) {
    log.info("Creating skill set http entity for gpt payload");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbeSkillSetTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String job = jobDescription.replaceAll("\n","\\\\n")
            .replaceAll("\r","\\\\r");
        payload = content.replace("${jobDescription}", job)
            .replace("${numberOfSkills}", String.valueOf(numberOfSkills));
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private HttpEntity<String> createHttpEntity(String jobDescription,int noOfQues) {
    log.info("Creating questions http entity for gpt payload");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbeTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String job = jobDescription.replaceAll("\n","\\\\n")
            .replaceAll("\r","\\\\r");
        payload = content.replace("${numberOfQuestions}", String.valueOf(noOfQues))
            .replace("${jobDescription}", job);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private List<AIResponse> mapToAIResponse(Object body) {
    log.info("mapping AI response to questions");
    ObjectMapper objectMapper = new ObjectMapper();
    List<AIResponse> list = new ArrayList<>();
      try {
        JsonNode rootNode = null;
        if(body instanceof String) {
          rootNode = objectMapper.readTree((String) body);
        }
        else{
          rootNode = objectMapper.valueToTree(body);
        }
        if(null!=rootNode) {
          JsonNode choicesNode = rootNode.get("choices");
          if (choicesNode != null) {
            if (choicesNode.isArray()) {
              for (JsonNode choice : choicesNode) {
                JsonNode messageNode = choice.get("message");
                if (messageNode != null) {
                  String contentString = messageNode.get("content").asText();
                  JsonNode contentNode = objectMapper.readTree(contentString);
                  JsonNode questionNode = contentNode.get("questions");
                  if (null == questionNode) {
                    if (contentNode.isArray()) {
                      for (JsonNode node : contentNode) {
                        AIResponse aiResponse = objectMapper.treeToValue(node, AIResponse.class);
                        list.add(aiResponse);
                      }
                    }
                  } else if (questionNode.isArray()) {
                    for (JsonNode node : questionNode) {
                      AIResponse aiResponse = objectMapper.treeToValue(node, AIResponse.class);
                      list.add(aiResponse);
                    }
                  }
                }
              }
            }
          }
        }else {
          log.error("Root node is missing in the response");
          throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Root node is missing in the response");
        }
      } catch (IOException e) {
        log.error("Exception occurred "+e.getMessage());
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
      }
    return list;
  }

  private List<String> mapToSkillSetResponse(Object body) {
    log.info("mapping AI response to skill sets");
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> aiResponses = new ArrayList<>();
    try {
      JsonNode rootNode = null;
      if(body instanceof String) {
        rootNode = objectMapper.readTree((String) body);
      }
      else{
        rootNode = objectMapper.valueToTree(body);
      }
      if(null!=rootNode) {
        JsonNode choicesNode = rootNode.get("choices");
        if (choicesNode != null) {
          if (choicesNode.isArray()) {
            for (JsonNode choice : choicesNode) {
              JsonNode messageNode = choice.get("message");
              if (messageNode != null) {
                String contentString = messageNode.get("content").asText();
                JsonNode contentNode = objectMapper.readTree(contentString);
                JsonNode questionNode = contentNode.get("skillSet");
                if (null == questionNode) {
                  if (contentNode.isArray()) {
                    for (JsonNode node : contentNode) {
                      String aiResponse = objectMapper.treeToValue(node, String.class);
                      aiResponses.add(aiResponse);
                    }
                  }
                } else if (questionNode.isArray()) {
                  for (JsonNode node : questionNode) {
                    String aiResponse = objectMapper.treeToValue(node, String.class);
                    aiResponses.add(aiResponse);
                  }
                }
              }
            }
          }
        }
      }else {
        log.error("Root node is missing in the response");
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Root node is missing in the response");
      }
    } catch (IOException e) {
      log.error("Exception occurred "+e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }
    return aiResponses;
  }

  private HttpEntity<String> createHttpEntityForResumeScan(String resumeData) {
    log.info("Creating resume scan http entity for gpt payload");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbeResumeScanTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String resumeContent = resumeData.replaceAll("\n", "\\\\n")
            .replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t");
        payload = content.replace("${resume}", resumeContent);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private AIResumeResponse mapToResumeScreeningResponse(Object body) {
    log.info("mapping AI response to resume key points");
    ObjectMapper objectMapper = new ObjectMapper();
    AIResumeResponse aiResumeResponse = new AIResumeResponse();
    try {
      JsonNode rootNode = null;
      if (body instanceof String) {
        rootNode = objectMapper.readTree((String) body);
      } else {
        rootNode = objectMapper.valueToTree(body);
      }
      if (null != rootNode) {
        JsonNode choicesNode = rootNode.get("choices");
        if (choicesNode != null) {
          if (choicesNode.isArray()) {
            for (JsonNode choice : choicesNode) {
              JsonNode messageNode = choice.get("message");
              if (messageNode != null) {
                String contentString = messageNode.get("content").asText();
                JsonNode contentNode = objectMapper.readTree(contentString);
                JsonNode keyPointsNode = contentNode.get("keyPoints");
                if (null == keyPointsNode) {
                  if (contentNode.isArray()) {
                    for (JsonNode node : contentNode) {
                      aiResumeResponse = objectMapper.treeToValue(node, AIResumeResponse.class);
                    }
                  }
                } else if (keyPointsNode.isArray()) {
                  for (JsonNode node : keyPointsNode) {
                    aiResumeResponse = objectMapper.treeToValue(node, AIResumeResponse.class);
                  }
                } else {
                  aiResumeResponse = objectMapper.treeToValue(keyPointsNode,
                      AIResumeResponse.class);
                }
              }
            }
          }
        }
      } else {
        log.error("Root node is missing in the response");
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
            "Root node is missing in the response");
      }
    } catch (IOException e) {
      log.error("Exception occurred " + e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    return aiResumeResponse;
  }


}
