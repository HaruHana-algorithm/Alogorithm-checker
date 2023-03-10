package haruhana.checker.service;

import haruhana.checker.dto.MemberCommitDTO;
import haruhana.checker.dto.MemberDTO;
import haruhana.checker.entity.Member;
import haruhana.checker.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;


	private final MemberCommitService memberCommitService;

	@Transactional
	public void setInitializeMemberInfo(MemberDTO memberDTO){
		memberRedisSave(memberDTO);
		/*memberRepository.findByName(memberDTO.getName())
				.ifPresentOrElse(
						existMember->existMember.updateMemberInfo(memberDTO.getName(),memberDTO.getEmail(),memberDTO.getCommitTime()),
						()->memberRepository.save(memberDTO.toEntity())
				);*/
		Optional<Member> findM = memberRepository.findByName(memberDTO.getName());
		if (findM.isPresent()){
			LocalDate own = findM.get().getCommitTime();
			LocalDate commitTime = memberDTO.getCommitTime();
			LocalDate localInfoCompare = getLocalInfoCompare(own,commitTime);

			findM.get().updateMemberInfo(memberDTO.getName(),memberDTO.getEmail(),localInfoCompare,memberDTO.getImgUrl());
		}else {
			memberRepository.save(memberDTO.toEntity());
		}
	}

	public List<Member> getMemberList(){
		return memberRepository.findAll();
	}


	private LocalDate getLocalInfoCompare(LocalDate own,LocalDate geu){
		return own.isAfter(geu) ? own : geu;
	}

	public Member getMemberInfoByName(String name){
		return memberRepository.findByName(name).get();
	}

	private void memberRedisSave(MemberDTO memberDTO){
		MemberCommitDTO memberCommitDTO=new MemberCommitDTO();
		memberCommitDTO.setId(UUID.randomUUID().toString());
		memberCommitDTO.setName(memberDTO.getName());
		memberCommitDTO.setLocalDate(memberDTO.getCommitTime());
		memberCommitService.saveTempMemberCommitList(memberCommitDTO);
	}
}
