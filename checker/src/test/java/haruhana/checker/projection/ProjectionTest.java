package haruhana.checker.projection;

import haruhana.checker.entity.State;
import haruhana.checker.repo.CommitRepository;
import haruhana.checker.repo.MemberRepository;
import haruhana.checker.repo.projection.CommitProjection;
import haruhana.checker.repo.projection.MemberProjection;
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

	@Autowired
	private CommitRepository commitRepository;


	@Test
	@DisplayName(value = "Email을 사용하여 단건 조회")
	public void 프로젝션을_활용하여_단건조회(){
		String email="seonghoo1217@naver.com";

		List<ProjectionRepository> findPResult = memberRepository.findProjectionByEmail(email);
		findPResult.forEach(r-> System.out.println(r.getEmail()));
	}

	@Test
	public void 클라이언트에게_BODY_전달받은_경우(){
		//given
		String email="seonghoo1217@naver.com";

		//when 주어진 LocalDate 기준이상의 값 컬럼 조회
		List<MemberProjection> memberCommitInfo = memberRepository.findByEmail(email);
		memberCommitInfo.forEach(
				r-> {
					System.out.println("Name="+r.getName());
					System.out.println("Last Commit Date="+r.getCommitTime());
				}
		);
	}

	@Test
	public void Enum_클래스_Projection(){
		//given - 커밋 완료한 정보
		State state = State.CHECK;

		//when - 커밋한 사람 정보
		List<CommitProjection> commitInfoOnlyCheck = commitRepository.findByState(state);
		commitInfoOnlyCheck.forEach(
//				fetchResult-> System.out.println("STATE="+fetchResult.getState())
				fetchResult-> {
					System.out.print("Member Name="+fetchResult.getMember().getName()+"Commit Date"+fetchResult.getLocalDate());
					System.out.println();
				}

		);
	}
}
