package haruhana.checker.entity;


import haruhana.checker.service.CommitService;
import haruhana.checker.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
@Log4j2
@Service
@RequiredArgsConstructor
public class ReadmeGenerator {

	private final MemberService memberService;

	private final CommitService commitService;


	/*
	| 참여자 | 횟수 | 1일 | 2일 | 3일 | 4일 | 5일 | 6일 | 7일 | 8일 | 9일 | 10일 | 11일 | 12일 | 13일 | 14일 | 15일 | 16일 | 17일 | 18일 | 19일 | 20일 | 21일 | 22일 | 23일 | 24일 | 25일 | 26일 | 27일 | 28일 | 29일 | 30일 | 31일 |
			| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
			|Yujin(78896558+Yujinmon@users.noreply.github.com)|14||||:white_check_mark:|:white_check_mark:||||:white_check_mark:||||:white_check_mark:|:white_check_mark:||:white_check_mark:|:white_check_mark:|||:white_check_mark:|||:white_check_mark:|||||||||
			|bong-u(bongudev@gmail.com)|24||:white_check_mark:||:white_check_mark:||:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|||:white_check_mark:|:white_check_mark:|:white_check_mark:|||:white_check_mark:||:white_check_mark:|:white_check_mark:|||||:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|
			|0-tae(100774819+0-tae@users.noreply.github.com)|18||:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:|:white_check_mark:||:white_check_mark:|||:white_check_mark:||||:white_check_mark:|||||||||||||||
*/
	public void getCheckSolvedMonthMember(){
		StringBuilder sb=new StringBuilder();
		YearMonth yearMonth = YearMonth.now();
		LocalDate start = yearMonth.atDay(1);
		LocalDate end = yearMonth.atEndOfMonth();
		sb.append("| 참여자 | ");
		int monthOfStart = start.getDayOfMonth();
		int monthOfEnd = end.getDayOfMonth();
		for (int i=monthOfStart;i<=monthOfEnd;i++){
			sb.append(i).append("일 | ");
		}
		sb.append("\n");
		sb.append("| --- | ");
		for (int i = monthOfStart; i <=monthOfEnd; i++) {
			sb.append("--- | ");
		}
		sb.append("\n");
		// 참여자 정보 작성
		for (Member member :memberService.getMemberList()) {
			sb.append("|").append(member.getName()).append("|");
			for (int i = monthOfStart; i <monthOfEnd; i++) {
				if (commitService.dayCheckForReadme(start,member)) {
					sb.append(":white_check_mark:");
				}else {
					if (isNowAfter(start)){
						sb.append(" ");
					}else {
						sb.append(":x:");
					}
				}
				start=start.plusDays(1);
				sb.append("|");
			}
			start=yearMonth.atDay(1);
			sb.append("\n");
		}
		/*sb.append("\n");
		sb.append("\n");
		sb.append("| 참여자 | 횟수 | ");
		for (Member member:memberService.getMemberList()){
			sb.append("|").append(member.getName()).append("|");
			int count = 0;
			start=yearMonth.atDay(1);
			for (int i=monthOfStart;i<=monthOfEnd;i++){
				if (commitService.dayCheckForReadme(start,member)) {
					count++;
				}
				start=start.plusDays(1);
			}
			sb.append(count+"|");
			sb.append("\n");
		}*/

		System.out.println(sb);
		writeReadMeFile(sb.toString());
	}

	public void writeReadMeFile(String contents){
		try {
			YearMonth yearMonth = YearMonth.now();
			int year = yearMonth.atDay(1).getYear();
			int monthValue = yearMonth.atDay(1).getMonthValue();
			File file = new File(year+"_"+monthValue+".md");
			FileWriter writer = new FileWriter(file);
			writer.write(contents);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isNowAfter(LocalDate checkDate){
		return checkDate.isAfter(LocalDate.now());
	}
}