package haruhana.checker.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import haruhana.checker.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

	public void getRepoContributors(){
		RestTemplate restTemplate=new RestTemplate();

		String url="https://api.github.com/repos/"+git_owner+"/"+git_repo+"/commits";
		String forObject = restTemplate.getForObject(url, String.class);

		JsonParser parser = new JsonParser();
		JsonArray commits = parser.parse(forObject).getAsJsonArray();

		ZoneId utcZoneId=ZoneId.of("UTC");
		ZoneId kstZoneId=ZoneId.of("Asia/Seoul");
		DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;


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
	}


	private void setInitializeMemberInfo(String username,String email,LocalDate commitTime,String imgUrl){
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setName(username);
		memberDTO.setEmail(email);
		memberDTO.setCommitTime(commitTime);
		memberDTO.setImgUrl(imgUrl);
		memberService.setInitializeMemberInfo(memberDTO);
	}
}
