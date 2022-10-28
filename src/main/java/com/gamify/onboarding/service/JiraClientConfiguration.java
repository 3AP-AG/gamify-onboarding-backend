package com.gamify.onboarding.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.gamify.onboarding.constants.AppConstant;
import java.net.URI;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiraClientConfiguration {

  private String username;
  private String password;

  private JiraRestClient restClient;

  public JiraClientConfiguration() {
    this.restClient = getJiraRestClient();
    this.username = "nemanja.djokic@3ap.ch";
    this.password = "n2phJHCicsDesFV0tF1K0216";
  }

  public JiraRestClient getJiraRestClient() {
    return new AsynchronousJiraRestClientFactory()
        .createWithBasicHttpAuthentication(
            URI.create(AppConstant.BASE_URL),
            username,
            password);
  }
}
