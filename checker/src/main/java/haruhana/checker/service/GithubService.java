package haruhana.checker.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import haruhana.checker.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Transactional
@Service
@RequiredArgsConstructor
public class GithubService {

	private final MemberService memberService;

	public static final String git_owner="HaruHana-algorithm";

	public static final String git_repo="HaruHana-algorithm";
	public static long allLog;

	private static String url = "https://api.github.com/repos/" + git_owner + "/" + git_repo + "/commits";

	public void getRepoContributors(){
		allLog=System.currentTimeMillis();
		long start1 = System.currentTimeMillis();
		HttpURLConnection conn = null;
		String forObject="";
		try {
			URL url = new URL("https://api.github.com/repos/" + git_owner + "/" + git_repo + "/commits");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Connection", "Keep-Alive");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())),8192);

			StringBuilder response = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				response.append(output);
			}

			forObject = response.toString();

		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		long end1 = System.currentTimeMillis();
		System.out.println("logging1="+((end1-start1)/1000.0)+"seconds");
		JsonParser parser = new JsonParser();
		JsonArray commits = parser.parse(forObject).getAsJsonArray();

		ZoneId utcZoneId=ZoneId.of("UTC");
		ZoneId kstZoneId=ZoneId.of("Asia/Seoul");
		DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;

		long start2 = System.currentTimeMillis();
		for (JsonElement commit : commits) {
			JsonObject commitObj = commit.getAsJsonObject();
			JsonObject commitData = commitObj.getAsJsonObject("commit");
			JsonObject author = commitObj.getAsJsonObject("author");
			String commitDateStr = commitData.getAsJsonObject("author").get("date").getAsString();
			String username = commitData.getAsJsonObject("author").get("name").getAsString();
			String user_email = commitData.getAsJsonObject("author").get("email").getAsString();
			String img_url = author.get("avatar_url").getAsString();

			ZonedDateTime utcDateTime = ZonedDateTime.parse(commitDateStr, isoFormatter.withZone(utcZoneId));
			// convert UTC time to KST time
			ZonedDateTime kstDateTime = utcDateTime.withZoneSameInstant(kstZoneId);
			// format date as string in "yyyy-MM-dd HH:mm:ss" format
			String kstDateTimeStr = kstDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			LocalDate commitDate = LocalDate.parse(kstDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			setInitializeMemberInfo(username,user_email,commitDate,img_url);
		}
		long end2 = System.currentTimeMillis();
		System.out.println("logging2="+(double)((end2-start2)/1000.0)+"seconds");
	}


	private void setInitializeMemberInfo(String username,String email,LocalDate commitTime,String imgUrl){
		long start3 = System.currentTimeMillis();
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setName(username);
		memberDTO.setEmail(email);
		memberDTO.setCommitTime(commitTime);
		memberDTO.setImgUrl(imgUrl);
		memberService.setInitializeMemberInfo(memberDTO);
		long end3 = System.currentTimeMillis();
	}

	public void useToRestTemplate(){
		RestTemplate restTemplate=new RestTemplate();
		String forObject = restTemplate.getForObject(url, String.class);
	}

	public void useToHttpURLConnection(){
		HttpURLConnection conn = null;
		String forObject="";
		try {
			URL url = new URL("https://api.github.com/repos/" + git_owner + "/" + git_repo + "/commits");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.setReadTimeout(5000); // 읽기 타임아웃 5초
			conn.setConnectTimeout(5000); // 연결 타임아웃 5초
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())),8192);

			StringBuilder response = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				response.append(output);
			}

			forObject = response.toString();

		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void useToWebClient(){
		WebClient webClient = WebClient.create();

		String forObject = webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	public void useToHttpComponents() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet("https://api.github.com/repos/" + git_owner + "/" + git_repo + "/commits");
		request.setHeader("Accept-Encoding", "gzip, deflate");
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		EntityUtils.toString(entity);
		httpClient.close();
	}

	public void useToOkHttp() throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://api.github.com/repos/" + git_owner + "/" + git_repo + "/commits")
				.header("Accept-Encoding", "gzip, deflate")
				.build();
		Response response = client.newCall(request).execute();
		String forObject = response.body().string();
	}
}
