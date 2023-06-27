package haruhana.checker.repo.projection;

import haruhana.checker.entity.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public interface MemberProjection {
	String getEmail();
	LocalDate getCommitTime();
	String getName();
}
