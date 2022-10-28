package com.gamify.onboarding.model;

import java.io.Serializable;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class issueWrapper implements Serializable {
  @JsonProperty("key")
  private String key;
  @JsonProperty("id")
  private String id;
  private TransitionsWrapper transitions;
  private IssueFieldsWrapper fields;


}
