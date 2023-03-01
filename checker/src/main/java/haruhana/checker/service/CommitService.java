package haruhana.checker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommitService {

	private final MemberService memberService;

	public void getTodayLearnCheck(){
		memberService.getMemberList();
	}
}
