package com.gamify.onboarding.service;


import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.gamify.onboarding.constants.AppConstant;
import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
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

  private final JiraClientServiceOld jiraClientServiceOld;

  public JiraClientService(JiraClientConfiguration jiraClientConfiguration,
      JiraClientServiceOld jiraClientServiceOld) {
    this.jiraRestClient = jiraClientConfiguration.getJiraRestClient();
    this.jiraClientServiceOld = jiraClientServiceOld;
  }

  public List<Mission> getAllMissions(String username) throws Exception {

    String accountId = jiraClientServiceOld.getAccountIdByUsername(username); //getUser(username).getAccountId();

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
        .map(issue ->
            new Mission(issue.getKey(),
                issue.getSummary(),
                issue.getDescription(),
                MissionStatus.getMissionStatusByJiraLabel(issue.getStatus().getName())
                )
        ).collect(Collectors.toList());
  }

  public void updateIssueStatus(String issueKey, MissionStatus status, String username) {
    IssueRestClient issueClient = jiraRestClient.getIssueClient();
    Issue issue = getIssue(issueKey);
    Iterable<Transition> transitions = issueClient.getTransitions(issue).claim();

    StreamSupport.stream(transitions.spliterator(), false)
        .filter(x-> x.getName().equals(status.getJiraLabelValue()))
        .forEach(
            transition -> {
              TransitionInput input = new TransitionInput(transition.getId());
              issueClient.transition(issue, input).claim();
            }
        );
  }

  public Issue getIssue(String issueKey) {
    IssueRestClient client = jiraRestClient.getIssueClient();
    return client.getIssue(issueKey).claim();
  }

  public User getUser(String username) {

    SearchResult result = jiraRestClient
        .getSearchClient()
        .searchJql("query=nemanja.djokic@3ap.ch")
//            AppConstant.PROPERTY_QUERY
//                + username)
        .claim();


    UserRestClient client = jiraRestClient.getUserClient();
    return client.getUser(username).claim();
  }

}
