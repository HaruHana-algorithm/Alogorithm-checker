package haruhana.checker.dto;

import haruhana.checker.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
	private String name;
	private String email;
	private LocalDateTime commitTime;
	private String imgUrl;

	public Member toEntity(){
		return Member.builder()
				.name(name)
				.email(email)
				.commitTime(commitTime)
				.imgUrl(imgUrl)
				.build();
	}
}
