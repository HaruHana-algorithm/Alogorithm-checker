package haruhana.checker.service;

import haruhana.checker.dto.MemberDTO;
import haruhana.checker.entity.Member;
import haruhana.checker.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public void setInitializeMemberInfo(MemberDTO memberDTO){
		/*memberRepository.findByName(memberDTO.getName())
				.ifPresentOrElse(
						existMember->existMember.updateMemberInfo(memberDTO.getName(),memberDTO.getEmail(),memberDTO.getCommitTime()),
						()->memberRepository.save(memberDTO.toEntity())
				);*/
		Optional<Member> findM = memberRepository.findByName(memberDTO.getName());
		if (findM.isPresent()){
			System.out.println("findM="+findM.get().getCommitTime());
			findM.get().updateMemberInfo(memberDTO.getName(),memberDTO.getEmail(),memberDTO.getCommitTime(),memberDTO.getImgUrl());
		}else {
			memberRepository.save(memberDTO.toEntity());
		}
	}

	public List<Member> getMemberList(){
		return memberRepository.findAll();
	}
}
