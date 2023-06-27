package haruhana.checker.repo.projection;

import haruhana.checker.entity.Member;
import haruhana.checker.entity.State;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface CommitProjection {
	State getState();
	LocalDate getLocalDate();
	Member getMember();
}
