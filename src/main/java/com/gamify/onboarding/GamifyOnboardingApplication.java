package com.gamify.onboarding;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GamifyOnboardingApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GamifyOnboardingApplication.class, args);

		JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
				.createWithBasicHttpAuthentication(URI.create("https://3apjira.atlassian.net"),
						"nemanja.djokic@3ap.ch", "kup0UYWfORLpdTlrxgkg0395");





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



		Issue task = jiraRestClient.getIssueClient().getIssue("EOS-16").claim();

		IssueRestClient issueClient = jiraRestClient.getIssueClient();

		Iterable<Transition> transitions = issueClient.getTransitions(task).claim();

		Stream<Transition> transitionStream = StreamSupport.stream(transitions.spliterator(), false);

		transitionStream
				.filter(x-> x.getName().equals(MissionStatus.TODO.getJiraLabelValue()))
				.forEach(
						transition -> {
							TransitionInput input = new TransitionInput(transition.getId());
							issueClient.transition(task, input).claim();
						}
				);


	}

};
