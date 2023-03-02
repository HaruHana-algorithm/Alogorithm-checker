package haruhana.checker.service;

import haruhana.checker.dto.CommitDTO;
import haruhana.checker.entity.Commit;
import haruhana.checker.entity.Member;
import haruhana.checker.entity.State;
import haruhana.checker.repo.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommitService {

	private final MemberService memberService;

	private final CommitRepository commitRepository;


	public void getTodayLearnCheck(){
		List<Member> memberList = memberService.getMemberList();
		for (Member m:memberList){
			CommitDTO commitDTO=new CommitDTO();
			commitDTO.setMember(m);
			commitDTO.setLocalDate(LocalDate.now());
			commitDTO.setState(compareLocalDateToday(m.getCommitTime()));
			saveOrUpdate(commitDTO);
		}
	}

	private State compareLocalDateToday(LocalDate member_localDate){
		if (member_localDate.equals(LocalDate.now())) return State.CHECK;
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
}
