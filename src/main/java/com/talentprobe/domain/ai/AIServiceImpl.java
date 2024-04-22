package com.talentprobe.domain.ai;

import com.talentprobe.domain.assessmentquestionstage.AssessmentQuestionStageService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

@Service
public class AIServiceImpl implements AIService{

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
  public List<AIResponse> getAIResponse(String jobDescription) {
   /* List<AIResponse> aiResponseList = new ArrayList<>();
    try {
      Resource resource = resourceLoader.getResource("classpath:Chat_Gpt_Response.json");
      BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
      JsonElement jsonElement = JsonParser.parseReader(reader);
      JsonArray jsonArray = jsonElement.getAsJsonArray();

      for (JsonElement element : jsonArray) {
        JsonObject jsonObject = element.getAsJsonObject();
        AIResponse aiResponse = new AIResponse();
        aiResponse.setQuestion(jsonObject.get("question").getAsString());
        List<String> choices = new ArrayList<>();
        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");
        for (JsonElement choiceElement : choicesArray) {
          choices.add(choiceElement.getAsString());
        }
        aiResponse.setChoices(choices);
        aiResponse.setAnswer(jsonObject.get("answer").getAsString());
        aiResponseList.add(aiResponse);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return aiResponseList;*/
    List<AIResponse> aiResponseList = new ArrayList<>();
    try {
      HttpEntity<String> entity = createHttpEntity(jobDescription);
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);

      aiResponseList = mapToAIResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
    }

    return aiResponseList;
  }

  private HttpEntity<String> createHttpEntity(String jobDescription) {

    String envApiKey = System.getenv("CHATGPT_API_KEY");
    String apiKey = null == envApiKey || envApiKey.isBlank() ? gptApiKey : envApiKey;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey + " " + apiKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:template.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        payload = content.replace("{placeholder}", jobDescription);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private List<AIResponse> mapToAIResponse(Object body) {

    // To be completed once the prompts are ready and response is received in required format
    return null;
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
