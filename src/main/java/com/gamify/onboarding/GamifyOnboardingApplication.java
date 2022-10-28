package com.gamify.onboarding;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class GamifyOnboardingApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GamifyOnboardingApplication.class, args);

		JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
				.createWithBasicHttpAuthentication(URI.create("https://3apjira.atlassian.net"),
						"nemanja.djokic@3ap.ch", "MLL6f9VD3ZNZZlwz8nGxFEB3");

		Issue task = (jiraRestClient.getIssueClient()).getIssue("EOS-2").claim();


		System.out.println(task.getId());
	}

};
