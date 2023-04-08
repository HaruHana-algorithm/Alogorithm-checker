package haruhana.checker;

import haruhana.checker.repo.MemberCommitRepository;
import haruhana.checker.service.MemberCommitService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CheckerApplication {

	private final MemberCommitRepository memberCommitRepository;


	public static void main(String[] args) {
		SpringApplication.run(CheckerApplication.class, args);
	}

}
