package com.talentprobe.domain.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JwtTokenResource {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class TokenResource {

    private String origin;
    private String token;
    private String client_id;
  }
}
