package haruhana.checker.repo;

import haruhana.checker.entity.Commit;
import haruhana.checker.entity.Member;
import haruhana.checker.entity.State;
import haruhana.checker.repo.projection.CommitProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommitRepository extends JpaRepository<Commit,Long> {
	Optional<Commit> findByMemberAndLocalDate(Member m, LocalDate commitDate);

	List<CommitProjection> findByState(State state);
}
