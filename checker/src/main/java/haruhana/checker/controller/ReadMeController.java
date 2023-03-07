package haruhana.checker.controller;

import haruhana.checker.entity.ReadmeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReadMeController {

	private final ReadmeGenerator readmeGenerator;

	@PostMapping("/api/repo/readme")
	public void getReadMeWrite(){
		readmeGenerator.getCheckSolvedMonthMember();
	}
}
