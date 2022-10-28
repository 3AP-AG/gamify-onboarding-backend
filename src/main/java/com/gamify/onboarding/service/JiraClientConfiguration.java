package com.gamify.onboarding.service;

import static com.gamify.onboarding.constants.AppConstant.BASE_URL;
import static com.gamify.onboarding.constants.AppConstant.PASSWORD;
import static com.gamify.onboarding.constants.AppConstant.USERNAME;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import java.net.URI;
import org.springframework.context.annotation.Configuration;
@Configuration
public class JiraClientConfiguration {

  private JiraRestClient restClient;

  public JiraClientConfiguration() {
    this.restClient = getJiraRestClient();
  }

  public JiraRestClient getJiraRestClient() {
    return new AsynchronousJiraRestClientFactory()
        .createWithBasicHttpAuthentication(
            URI.create(BASE_URL),
            USERNAME,
            PASSWORD);
  }
}
