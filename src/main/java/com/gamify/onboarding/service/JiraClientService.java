package com.gamify.onboarding.service;

import static com.gamify.onboarding.constants.AppConstant.PASSWORD;
import static com.gamify.onboarding.constants.AppConstant.USERNAME;
import static com.gamify.onboarding.constants.AppConstant.USER_URL;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.gamify.onboarding.constants.AppConstant;
import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JiraClientService {

  @Value("${jira.username}")
  private static String username;

  @Value("${jira.token")
  private static String password;

  private final JiraRestClient jiraRestClient;


  public JiraClientService(JiraClientConfiguration jiraClientConfiguration) {
    this.jiraRestClient = jiraClientConfiguration.getJiraRestClient();
  }

  public List<Mission> getAllMissions(String username) throws Exception {

    String accountId = getAccountId(username);

    SearchResult result = jiraRestClient
        .getSearchClient()
        .searchJql(
            AppConstant.PROPERTY_ASSIGNEE
                + accountId
                + AppConstant.OPERATOR_AND
                + AppConstant.PROPERTY_LABELS
                + AppConstant.LABEL_GAMIFY)
        .claim();

    Iterable<Issue> issueIterable = () -> result.getIssues().iterator();

    return StreamSupport.stream(issueIterable.spliterator(), false)
        .map(issue -> new Mission(issue.getKey(),
            issue.getSummary(),
            issue.getDescription(),
            MissionStatus.getMissionStatusByJiraLabel(issue.getStatus().getName())))
        .collect(Collectors.toList());
  }

  public void updateIssueStatus(String issueKey, MissionStatus status, String username) {
    IssueRestClient issueClient = jiraRestClient.getIssueClient();
    Issue issue = getIssue(issueKey);
    Iterable<Transition> transitions = issueClient.getTransitions(issue).claim();

    StreamSupport.stream(transitions.spliterator(), false)
        .filter(x -> x.getName().equals(status.getJiraLabelValue()))
        .forEach(
            transition -> {
              TransitionInput input = new TransitionInput(transition.getId());
              issueClient.transition(issue, input).claim();
            });
  }

  public Issue getIssue(String issueKey) {
    IssueRestClient client = jiraRestClient.getIssueClient();
    return client.getIssue(issueKey).claim();
  }

  public String getAccountId(String username) throws UnirestException {

    return Unirest.get(USER_URL + username)
        .basicAuth(USERNAME, PASSWORD)
        .header("Accept", "application/json")
        .asJson().getBody().getArray().getJSONObject(0).getString("accountId");
  }

}
