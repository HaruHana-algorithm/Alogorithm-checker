package haruhana.checker.controller;

import haruhana.checker.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("//api/repo")
public class UserController {

	private final GithubService githubService;

	@GetMapping("/contributors")
	public void getRepoContributors(){
		githubService.getRepoContributors();
	}

	@GetMapping("/memberList")

}
