package com.talentprobe.domain.testgenie.gptResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class GptServiceImpl implements GptService {

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

  @Override
  public List<GptResponse> getGptResponse(String usecase) {
    List<GptResponse> gptResponseList = new ArrayList<>();
    try {
      if (isMockEnabled) {
        Resource resource = resourceLoader.getResource(
            "classpath:Gpt_Mock_Response_TestGenie.json");
        ResponseEntity<Object> responseEntity = ResponseEntity
            .ok()
            .header("header", "value")
            .body(StreamUtils.copyToString(resource.getInputStream(),
                StandardCharsets.UTF_8));
        return mapToGptResponse(responseEntity.getBody());
      }
      HttpEntity<String> entity = createHttpEntity(usecase);
      log.info("Making gpt call for the use case");
      ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, entity, Object.class);
      gptResponseList = mapToGptResponse(responseEntity.getBody());
    } catch (RestClientException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, exception.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    return gptResponseList;
  }

  private HttpEntity<String> createHttpEntity(String usecase) {
    log.info("Creating http entity for gpt payload");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", gptAccessKey);
    String payload = null;
    try {
      Resource resource = resourceLoader.getResource("classpath:testGenieTemplate.txt");
      String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
      if (!content.isBlank()) {
        String sanitizedUseCase = usecase.replace("\\", "\\\\").replace("\n", "\\\\n")
            .replace("\r", "\\\\r").replace("\"", "\\\"");
        payload = content.replace("${usecase}", sanitizedUseCase.trim());
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    return new HttpEntity<>(payload, headers);
  }

  private List<GptResponse> mapToGptResponse(Object body) {
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    List<GptResponse> gptResponseList = new ArrayList<>();
    try {
      log.info("Extracting the gpt response and mapping to domain");
      JsonNode rootNode = null;
      if (body instanceof String) {
        rootNode = objectMapper.readTree((String) body);
      } else {
        rootNode = objectMapper.valueToTree(body);
      }
      if (rootNode != null) {
        JsonNode choicesNode = rootNode.get("choices");
        if (choicesNode != null && choicesNode.isArray()) {
          for (JsonNode choice : choicesNode) {
            JsonNode messageNode = choice.get("message");
            if (messageNode != null) {
              String contentString = messageNode.get("content").asText();
              JsonNode contentNode = objectMapper.readTree(
                  removeInvalidCharacters(contentString));
              JsonNode testCaseNode = contentNode.get("testCases");
              log.info("Extracted testCases array from response");
              if (testCaseNode != null && testCaseNode.isArray()) {
                for (JsonNode node : testCaseNode) {
                  GptResponse gptResponse = objectMapper.treeToValue(node, GptResponse.class);
                  gptResponseList.add(gptResponse);
                }
              }
            }
          }
        } else {
          log.error("'choices' node is missing in the response");
          throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
              "Choices node is missing in the response");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Root node is missing in the response");
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error occurred while processing");
    }
    return gptResponseList;
  }

  public String removeInvalidCharacters(String input) {
    String sanitizedString;
    if (input.startsWith("```json")) {
      sanitizedString = input.replaceAll("^```json", "");
      return sanitizedString.replace("`", "");
    }
    return input;
  }

}
