package com.talentprobe.domain.testgenie.gptResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class GptServiceImpl implements GptService {

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
  public List<GptResponse> getGptResponse(String usecase) {

    List<GptResponse> gptResponseList = new ArrayList<>();
    try {
      HttpEntity<String> entity = createHttpEntity(usecase);
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
     /* Resource resource = resourceLoader.getResource("classpath:Gpt_Mock_Response_TestGenie.json");
      ResponseEntity<Object> responseEntity = ResponseEntity
          .ok()
          .header("header", "value")
          .body(StreamUtils.copyToString(resource.getInputStream(),
              StandardCharsets.UTF_8));*/
      gptResponseList = mapToGptResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }

    return gptResponseList;
  }

  private HttpEntity<String> createHttpEntity(String usecase) {

    String envApiKey = System.getenv("CHATGPT_API_KEY");
    String apiKey = null == envApiKey || envApiKey.isBlank() ? gptApiKey : envApiKey;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey + " " + apiKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:testGenieTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        payload = content.replace("${usecase}", String.valueOf(usecase));
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return new HttpEntity<>(payload, headers);
  }

  private List<GptResponse> mapToGptResponse(Object body) {
    ObjectMapper objectMapper = new ObjectMapper();
    List<GptResponse> list = new ArrayList<>();
    try {
      JsonNode rootNode = null;
      if (body instanceof Map) {
        rootNode = objectMapper.valueToTree(body);
      } else if (body instanceof String) {
        rootNode = objectMapper.readTree((String) body);
      }
      if (rootNode != null) {
        JsonNode choicesNode = rootNode.get("choices");
        if (choicesNode != null && choicesNode.isArray()) {
          for (JsonNode choice : choicesNode) {
            JsonNode messageNode = choice.get("message");
            if (messageNode != null) {
              String contentString = messageNode.get("content").asText();
              JsonNode contentNode = objectMapper.readTree(contentString);
              JsonNode testCaseNode = contentNode.get("testCases");

              if (testCaseNode.isArray()) {
                for (JsonNode node : testCaseNode) {
                  GptResponse gptResponse = objectMapper.treeToValue(node, GptResponse.class);
                  list.add(gptResponse);
                }
              }
            }
          }
        } else {
          log.error("'choices' node is missing in the response");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return list;
  }
}
