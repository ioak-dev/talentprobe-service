package com.talentprobe.domain.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStageService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AIServiceImpl implements AIService {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private AssessmentQuestionStageService assessmentQuestionStageService;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${gpt.apiKey}")
  private String gptApiKey;

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
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
      /*Resource resource = resourceLoader.getResource("classpath:Gpt_Mock_Response2.json");
      ResponseEntity<Object> responseEntity = ResponseEntity
          .ok()
          .header("header", "value")
          .body(StreamUtils.copyToString(resource.getInputStream(),
              StandardCharsets.UTF_8));*/
      aiResponseList = mapToAIResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
    }
    return aiResponseList;
  }

  private HttpEntity<String> createHttpEntity(String jobDescription,int noOfQues) {

    String envApiKey = System.getenv("CHATGPT_API_KEY");
    String apiKey = null == envApiKey || envApiKey.isBlank() ? gptApiKey : envApiKey;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey + " " + apiKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:talentProbleTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
       String job = jobDescription.replaceAll("\n","\\\\n").replaceAll("\r","\\\\r");
        payload = content.replace("${numberOfQuestions}", String.valueOf(noOfQues))
            .replace("${jobDescription}", job);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private List<AIResponse> mapToAIResponse(Object body) {
    ObjectMapper objectMapper = new ObjectMapper();
    List<AIResponse> list = new ArrayList<>();
    try {
      JsonNode rootNode = objectMapper.readTree(body.toString());
      JsonNode choicesNode = rootNode.get("choices");
      if (choicesNode != null) {
        if (choicesNode.isArray()) {
          for (JsonNode choice : choicesNode) {
            JsonNode messageNode = choice.get("message");
            if (messageNode != null) {
              String contentString = messageNode.get("content").asText();
              JsonNode contentNode = objectMapper.readTree(contentString);
              JsonNode questionNode = contentNode.get("questions");
              if(null==questionNode){
                if(contentNode.isArray()){
                  for(JsonNode node :contentNode) {
                    AIResponse aiResponse = objectMapper.treeToValue(node, AIResponse.class);
                    list.add(aiResponse);
                  }
                }
              }else if(questionNode.isArray()){
                for(JsonNode node :questionNode) {
                  AIResponse aiResponse = objectMapper.treeToValue(node, AIResponse.class);
                  list.add(aiResponse);
                }
              }
              }
          }
        }
      } else {
        log.error("'choices' node is missing in the response");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }



  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class GptResponseResource {

    private GptResponse gptResponse;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class GptResponse {

    private List<AIResponse> aiResponseList;
  }
}
