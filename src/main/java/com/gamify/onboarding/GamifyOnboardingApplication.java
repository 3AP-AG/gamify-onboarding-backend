package com.gamify.onboarding;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.gamify.onboarding.model.MyJiraClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GamifyOnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamifyOnboardingApplication.class, args);
//
//		MyJiraClient myJiraClient = new MyJiraClient(
//				"nemanja.djokic@3ap.ch",
//				"QstGmxWfftSKPERHC5gMAF2C",
//				"https://3apjira.atlassian.net"
//		);


//		Issue issue = myJiraClient.getIssue("EOS-2");
//		System.out.println(issue.getDescription());

//		Issue issue = myJiraClient.getIssue("EOS-02");
//		System.out.println(issue.getDescription());

}
};
