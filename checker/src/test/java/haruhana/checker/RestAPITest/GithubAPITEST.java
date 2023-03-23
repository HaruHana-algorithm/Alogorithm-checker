package haruhana.checker.RestAPITest;

import haruhana.checker.service.GithubService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GithubAPITEST {

	@Autowired
	private GithubService githubService;

	@Test
	@DisplayName("RestTemplate 1회 Response 속도")
	public void usedToRestTemplateConnectGit(){
		long restStart = System.currentTimeMillis();
		githubService.useToRestTemplate();
		long restEnd = System.currentTimeMillis();
		System.out.println("rest 1회 요청 평균 응답속도="+(double) ((restEnd-restStart)/1000.0));
	}

	@Test
	@DisplayName("HttpURLConnection 1회 Response 속도")
	public void usedToHttpUrlConnectionGit(){
		long hucStart = System.currentTimeMillis();
		githubService.useToHttpURLConnection();
		long hucEnd = System.currentTimeMillis();
		System.out.println("HttpURLConnection 1회 요청 평균 응답속도="+(double) ((hucEnd-hucStart)/1000.0));
	}


	@Test
	@DisplayName("HttpURLConnection 1회 Response 속도")
	public void usedWebClientConnectionGit(){
		long wcStart = System.currentTimeMillis();
		githubService.useToWebClient();
		long wcEnd = System.currentTimeMillis();
		System.out.println("HttpURLConnection 1회 요청 평균 응답속도="+(double) ((wcEnd-wcStart)/1000.0));
	}


	@Test
	@DisplayName("RestTemplate 평균 100회 Response 속도")
	public void usedToRestTemplateConnectGit100(){
		double sum=0;
		for (int i=0;i<100;i++){
			long restStart = System.currentTimeMillis();
			githubService.useToRestTemplate();
			long restEnd = System.currentTimeMillis();
			sum+=(double) ((restEnd-restStart)/1000.0);
		}
		System.out.println("rest 100회 요청 평균 응답속도="+sum/100);
	}

	@Test
	@DisplayName("HttpURLConnection 평균 100회 Response 속도")
	public void usedToHttpUrlConnectionGit100(){
		double sum=0;
		for (int i=0;i<100;i++){
			long hucStart = System.currentTimeMillis();
			githubService.useToHttpURLConnection();
			long hucEnd = System.currentTimeMillis();
			sum+=(double) ((hucEnd-hucStart)/1000.0);
		}
		System.out.println("HttpURLConnection 100회 요청 평균 응답속도="+sum/100);
	}

	@Test
	@DisplayName("WebClient 평균 100회 Response 속도")
	public void usedToWebClientConnectionGit100(){
		double sum=0;
		for (int i=0;i<100;i++){
			long wcStart = System.currentTimeMillis();
			githubService.useToWebClient();
			long wcEnd = System.currentTimeMillis();
			sum+=(double) ((wcEnd-wcStart)/1000.0);
		}
		System.out.println("WebClient 100회 요청 평균 응답속도="+sum/100);
	}
}
