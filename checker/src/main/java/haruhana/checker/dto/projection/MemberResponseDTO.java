package haruhana.checker.dto.projection;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponseDTO {
	private LocalDate lastCommitDate;
	private String name;
}
