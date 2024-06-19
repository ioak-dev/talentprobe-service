package com.talentprobe.domain.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GptModelSelector {

  @Value("${enable.gpt4.model}")
  private boolean enableGpt4Model;

  private static final String GPT4="gpt-4";

  private static final String GPT3="gpt-3.5-turbo-0125";

  public String getModelName() {
    return enableGpt4Model ? GPT4 : GPT3;
  }
}
