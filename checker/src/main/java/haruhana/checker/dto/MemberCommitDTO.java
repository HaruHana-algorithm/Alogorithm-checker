package haruhana.checker.dto;

import haruhana.checker.entity.redis.MemberCommit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberCommitDTO {
	private String id;
	private String name;
	private LocalDate localDate;

	public MemberCommit toEntity(){
		return MemberCommit.builder()
				.id(id)
				.name(name)
				.commitTime(localDate)
				.build();
	}

}
