package com.gamify.onboarding.model;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import java.net.URI;

public class AsynchronousMyJiraRestClientFactory extends AsynchronousJiraRestClientFactory
{

  @Override
  public JiraRestClient create(URI serverUri, AuthenticationHandler authenticationHandler) {
    final DisposableHttpClient httpClient = new AsynchronousHttpClientFactory()
        .createClient(serverUri, authenticationHandler);
    return new AsynchronousMyJiraRestClient(serverUri, httpClient);
  }


  @Override
  public JiraRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username, final String password) {
    return create(serverUri, new BasicHttpAuthenticationHandler(username, password));
  }
}
