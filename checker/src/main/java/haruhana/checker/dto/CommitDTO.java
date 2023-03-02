package haruhana.checker.dto;

import haruhana.checker.entity.Commit;
import haruhana.checker.entity.Member;
import haruhana.checker.entity.State;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommitDTO {

	private Member member;

	private State state;

	private LocalDate localDate;

	public Commit toEntity(){
		return Commit.builder()
				.member(member)
				.state(state)
				.localDate(localDate)
				.build();
	}
}
