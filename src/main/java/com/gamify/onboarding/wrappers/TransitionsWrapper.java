package com.gamify.onboarding.wrappers;

import java.io.Serializable;
import java.util.LinkedHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransitionsWrapper implements Serializable {

  private String id;
  private String name;
  private LinkedHashMap to;

}
