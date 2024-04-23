package com.talentprobe.domain.testgenie.gptResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
public class GptServiceImpl implements GptService{

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${gpt.apiKey}")
  private String gptApiKey;

  @Value("${gpt.accessKey}")
  private String gptAccessKey;

  @Value("${gpt.url}")
  private String url;

  @Override
  public List<GptResponse> getGptResponse(String description) {
    List<GptResponse> gptResponseList = new ArrayList<>();
    try {
      HttpEntity<String> entity = createHttpEntity(description);
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);

      gptResponseList = mapToGptResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
    }

    return gptResponseList;
  }

  private HttpEntity<String> createHttpEntity(String description) {

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
        payload = content.replace("{placeholder}", description);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private List<GptResponse> mapToGptResponse(Object body) {

    // To be completed once the prompts are ready and response is received in required format
    return null;
  }
}
