package com.talentprobe.domain.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GptModelSelector {

  @Value("${enable.gpt4.model}")
  private boolean enableGpt4Model;

  private static final String GPT4="gpt-4o";

  private static final String GPT3="gpt-3.5-turbo";

  public String getModelName() {
    String modelSelected= enableGpt4Model ? GPT4 : GPT3;
    log.info("Model selected : "+modelSelected);
    return modelSelected;
  }
}
