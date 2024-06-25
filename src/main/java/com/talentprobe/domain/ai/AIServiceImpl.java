package com.talentprobe.domain.ai;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentprobe.domain.util.GptModelSelector;
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
  private RestTemplate restTemplate;

  @Value("${gpt.accessKey}")
  private String gptAccessKey;

  @Value("${gpt.url}")
  private String url;

  @Value("${mock.enabled}")
  private boolean isMockEnabled;

  @Autowired
  private GptModelSelector modelSelector;

  @Override
  public AIResponse getAIResponse(String jobDescription,
      int noOfQues) {
    AIResponse aiResponseList = new AIResponse();
    try {
      if (isMockEnabled) {
        Resource resource = resourceLoader.getResource(
            "classpath:Gpt_Mock_Response_TalentProbe.json");
        ResponseEntity<Object> responseEntity = ResponseEntity
            .ok()
            .header("header", "value")
            .body(StreamUtils.copyToString(resource.getInputStream(),
                StandardCharsets.UTF_8));
        return mapToAIResponse(responseEntity.getBody());
      }
      HttpEntity<String> entity = createHttpEntity(jobDescription,noOfQues);
      log.info("Making gpt call for questions generation");
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
      aiResponseList = mapToAIResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT,
          exception.getMessage());
    } catch (Exception e){
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }
    return aiResponseList;
  }

  @Override
  public List<String> getAISkillSetResponse(String jobDescription, int numberOfSkills) {
    List<String> aiSkillSetResponse;
    try {
      if (isMockEnabled) {
        Resource resource = resourceLoader.getResource(
            "classpath:Gpt_Mock_Response_SkillSet.json");
        ResponseEntity<Object> responseEntity = ResponseEntity
            .ok()
            .header("header", "value")
            .body(StreamUtils.copyToString(resource.getInputStream(),
                StandardCharsets.UTF_8));
        return mapToSkillSetResponse(responseEntity.getBody());
      }
      HttpEntity<String> entity = createHttpEntityForSkillSet(jobDescription, numberOfSkills);
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
      if (isMockEnabled) {
        Resource resource = resourceLoader.getResource(
            "classpath:Gpt_Mock_Response_resumeScan.json");
        ResponseEntity<Object> responseEntity = ResponseEntity
            .ok()
            .header("header", "value")
            .body(StreamUtils.copyToString(resource.getInputStream(),
                StandardCharsets.UTF_8));
        return mapToResumeScreeningResponse(responseEntity.getBody());
      }
      HttpEntity<String> entity = createHttpEntityForResumeScan(content);
      log.info("Making gpt call for resume key points generation");
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
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
    String modelName=modelSelector.getModelName();
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbeSkillSetTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String job = jobDescription.replaceAll("\n","\\\\n")
            .replaceAll("\r","\\\\r");
        payload = content.replace("${jobDescription}", job)
            .replace("${numberOfSkills}", String.valueOf(numberOfSkills)).replace("${model.name}",modelName);
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
    String modelName=modelSelector.getModelName();
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbeTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String job = jobDescription.replaceAll("\n","\\\\n")
            .replaceAll("\r","\\\\r");
        payload = content.replace("${numberOfQuestions}", String.valueOf(noOfQues))
            .replace("${jobDescription}", job).replace("${model.name}",modelName);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private AIResponse mapToAIResponse(Object body) {
    log.info("mapping AI response to questions");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false);
    List<Object> list = new ArrayList<>();
    AIResponse aiResponse = new AIResponse();
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
                  JsonNode contentNode = objectMapper.readTree(
                      removeInvalidCharacters(contentString));
                  JsonNode questionNode = contentNode.get("questions");
                  if (null == questionNode) {
                    if (contentNode.isArray()) {
                      for (JsonNode node : contentNode) {
                        Object aiResponseTemp = objectMapper.treeToValue(node, Object.class);
                        list.add(aiResponseTemp);
                        aiResponse.setQuestions(list);
                      }
                    }
                  } else if (questionNode.isArray()) {
                    for (JsonNode node : questionNode) {
                      Object aiResponseTemp = objectMapper.treeToValue(node, Object.class);
                      list.add(aiResponseTemp);
                    }
                    aiResponse.setQuestions(list);
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
    return aiResponse;
  }

  public String removeInvalidCharacters(String input) {
    String sanitizedString;
    if (input.startsWith("```json")) {
      sanitizedString = input.replaceAll("^```json", "")
          .replaceAll("^(String)", "").replaceAll("/", "");
      return sanitizedString.replace("`", "");
    }
    return input;
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
                JsonNode contentNode = objectMapper.readTree(
                    removeInvalidCharacters(contentString));
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
    String modelName=modelSelector.getModelName();
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbeResumeScanTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String resumeContent = resumeData.replaceAll("\n", "\\\\n")
            .replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t");
        payload = content.replace("${resume}", resumeContent).replace("${model.name}",modelName);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private AIResumeResponse mapToResumeScreeningResponse(Object body) {
    log.info("mapping AI response to resume key points");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false);
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
                JsonNode contentNode = objectMapper.readTree(removeInvalidCharacters(contentString));
                aiResumeResponse.setData(contentNode.get("keyPoints"));
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
