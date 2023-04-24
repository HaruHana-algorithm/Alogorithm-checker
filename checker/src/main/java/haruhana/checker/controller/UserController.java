package haruhana.checker.controller;

import haruhana.checker.entity.Member;
import haruhana.checker.service.GithubService;
import haruhana.checker.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("//api/repo")
public class UserController {

	private final GithubService githubService;

	private final MemberService memberService;

	@GetMapping("/contributors")
	public void getRepoContributors(){
		githubService.getRepoContributors();
	}

	@GetMapping("/memberList")
	public List<Member> getInfoMemberList(){
		return memberService.getInfoMemberList();
	}
}
