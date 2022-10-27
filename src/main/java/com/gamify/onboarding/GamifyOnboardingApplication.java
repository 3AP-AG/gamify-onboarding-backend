package com.gamify.onboarding;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.gamify.onboarding.model.AsynchronousMyJiraRestClient;
import com.gamify.onboarding.model.AsynchronousMyJiraRestClientFactory;
import com.gamify.onboarding.model.MyJiraClient;
import java.net.URI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GamifyOnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamifyOnboardingApplication.class, args);

		URI uri = URI.create("https://3apjira.atlassian.net");

		JiraRestClient jiraRestClient = new AsynchronousMyJiraRestClientFactory()
				.createWithBasicHttpAuthentication(URI.create("https://3apjira.atlassian.net"),
						"nemanja.djokic@3ap.ch", "1CUjZLCbbsWc8xKXwx3b2467");
		Issue task = jiraRestClient.getIssueClient().getIssue("EOS-02").claim();
		System.out.println(task.getId());
}
};
