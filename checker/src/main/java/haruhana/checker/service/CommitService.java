package haruhana.checker.service;

import haruhana.checker.dto.CommitDTO;
import haruhana.checker.entity.Commit;
import haruhana.checker.entity.Member;
import haruhana.checker.entity.State;
import haruhana.checker.entity.redis.MemberCommit;
import haruhana.checker.repo.CommitRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CommitService {

	private final MemberService memberService;

	private final CommitRepository commitRepository;

	private final MemberCommitService memberCommitService;

	public static YearMonth yearMonth;
	public static LocalDate start;
	public static LocalDate end;

	@PostConstruct
	public void dateInitialize(){
		yearMonth=YearMonth.now();
		start=yearMonth.atDay(1);
		end=yearMonth.atEndOfMonth();
	}

	public void getTodayLearnCheck(){
		List<Member> memberList = memberService.getMemberList();
		YearMonth yearMonth = YearMonth.now();
		LocalDate start = yearMonth.atDay(1);
		LocalDate end = yearMonth.atEndOfMonth();
		int monthOfStart = start.getDayOfMonth();
		int monthOfEnd = end.getDayOfMonth();
		for (int i=monthOfStart;i<monthOfEnd;i++){
			for (Member m:memberList){
				CommitDTO commitDTO=new CommitDTO();
				commitDTO.setMember(m);
				commitDTO.setState(State.UNCHECK);
				commitDTO.setLocalDate(start);
				saveOrUpdate(commitDTO);
			}
			start=start.plusDays(1);
		}
	}

	private State compareLocalDateToday(LocalDate member_localDate,LocalDate compare_date){
		if (member_localDate.equals(compare_date)) return State.CHECK;
		else return State.UNCHECK;
	}

	private void saveOrUpdate(CommitDTO commitDTO){
		Optional<Commit> findC = commitRepository.findByMemberAndLocalDate(commitDTO.getMember(), commitDTO.getLocalDate());
		if (findC.isPresent()){
			findC.get().updateCommitInfo(commitDTO.getState());
		}else{
			commitRepository.save(commitDTO.toEntity());
		}
	}

	public boolean dayCheckForReadme(LocalDate check_day,Member member){

		Optional<Commit> findC = commitRepository.findByMemberAndLocalDate(member, check_day);
		System.out.println("state="+findC.get().getState());
		return findC.get().getState().toString().equals("CHECK");
	}

	public void commitStateUpdate(){
		List<MemberCommit> infoAllMemberCommit = memberCommitService.getInfoAllMemberCommit();
		for (MemberCommit memberCommit:infoAllMemberCommit){

			System.out.println("memberCommit="+memberCommit.getName());
			System.out.println("memberCommitTime="+memberCommit.getCommitTime());
			if (memberCommit.getName().equals("Lee.t.c")){
				continue;
			}

			if (memberCommit.getCommitTime().isAfter(start.minusDays(1))&&memberCommit.getCommitTime().isBefore(end.plusDays(1))){
				Member findM = memberService.getMemberInfoByName(memberCommit.getName());
				System.out.println("커밋한 멤버="+findM.getName());
				commitRepository.findByMemberAndLocalDate(findM,memberCommit.getCommitTime()).get().updateCommitInfo(State.CHECK);
			}
		}


	}
}
