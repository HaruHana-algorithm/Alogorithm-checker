package haruhana.checker.projection;

import haruhana.checker.repo.MemberRepository;
import haruhana.checker.repo.projection.ProjectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProjectionTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName(value = "Email을 사용하여 단건 조회")
	public void 프로젝션을_활용하여_단건조회(){
		String email="seonghoo1217@naver.com";

		List<ProjectionRepository> findPResult = memberRepository.findProjectionByEmail(email);
		findPResult.forEach(r-> System.out.println(r.getEmail()));
	}
}
