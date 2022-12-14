package com.gamify.onboarding.service;

import static com.gamify.onboarding.constants.AppConstant.PASSWORD;
import static com.gamify.onboarding.constants.AppConstant.USERNAME;
import static com.gamify.onboarding.constants.AppConstant.USER_URL;

import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
import com.gamify.onboarding.wrappers.Issue;
import com.gamify.onboarding.wrappers.issueWrapper;
import com.mashape.unirest.http.Unirest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Another approach, of using atlassian api, documentation can be found here
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/intro/#about
 * but we decide to use library from atlassian
 */
@Deprecated
@Service
public class JiraClientServiceOld {

  private final RestTemplate restTemplate;

  public JiraClientServiceOld(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public String getAccountIdByUsername(String username) throws Exception{
    return Unirest.get(USER_URL + username)
        .basicAuth(USERNAME, PASSWORD)
        .header("Accept", "application/json")
        .asJson().getBody().getArray().getJSONObject(0).getString("accountId");
  };

  public List<Mission> getIssuesByUsername(String username) throws Exception{
    String userId = getAccountIdByUsername(username);
    String url = "https://3apjira.atlassian.net/rest/api/2/search?jql=assignee=632da4d561dbef2805be1c56 AND labels=gamify";

    // create headers
    HttpHeaders headers = new HttpHeaders();
    // set `accept` header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBasicAuth(USERNAME, PASSWORD);

    // build the request
    HttpEntity request = new HttpEntity(headers);

    // use `exchange` method for HTTP call
    ResponseEntity<Issue> response = this.restTemplate.exchange(url, HttpMethod.GET, request, Issue.class, 1);

    if(response.getStatusCode() == HttpStatus.OK) {
      final issueWrapper[] issues = response.getBody().getIssues();
      return Arrays.stream(issues)
          .map(issue -> new Mission(issue.getKey(),
              issue.getFields().getSummary(),
              issue.getFields().getDescription(),
              MissionStatus.TODO))
          .collect(Collectors.toList());


    } else {
      return null;
    }
  };




}
