package haruhana.checker.controller;

import haruhana.checker.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final GithubService githubService;

	@GetMapping("/api/repo/contributors")
	public void getRepoContributors(){
		githubService.getRepoContributors();
	}
}
