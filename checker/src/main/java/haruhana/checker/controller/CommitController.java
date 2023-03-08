package haruhana.checker.controller;

import haruhana.checker.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommitController {

	private final CommitService commitService;

	@PostMapping("/api/repo/commitCheck")
	public void getRepoCommitCheck(){
		commitService.getTodayLearnCheck();
		commitService.commitStateUpdate();
	}

}
