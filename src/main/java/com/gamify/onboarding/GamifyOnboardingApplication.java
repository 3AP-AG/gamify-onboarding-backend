package com.gamify.onboarding;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class GamifyOnboardingApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GamifyOnboardingApplication.class, args);

		JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
				.createWithBasicHttpAuthentication(URI.create("https://3apjira.atlassian.net"),
						"duska.lazic.jakovljevic@3ap.ch", "VtyreaehKOMRkIIzGQxn65CD");

		Issue task = jiraRestClient.getIssueClient().getIssue("EOS-2").claim();

		System.out.println(task.getId());

		SearchResult sr = jiraRestClient.getSearchClient().searchJql("assignee=632da4d561dbef2805be1c56 AND labels=gamify").claim();



		Iterable<Issue> iterable = () -> sr.getIssues().iterator();
		Stream<Issue> targetStream = StreamSupport.stream(iterable.spliterator(), false);

		List<Mission> missions = targetStream
				.map(issue ->
						new Mission(issue.getKey(),
						issue.getSummary(),
						issue.getDescription(),
						MissionStatus.TODO,
						//TODO promeni na status
						1)
				).collect(Collectors.toList());;
		System.out.println(missions.size());
		System.out.println(missions.get(0).getStatus());

	}

};
