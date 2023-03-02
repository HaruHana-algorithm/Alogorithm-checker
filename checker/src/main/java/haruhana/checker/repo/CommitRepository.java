package haruhana.checker.repo;

import haruhana.checker.entity.Commit;
import haruhana.checker.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CommitRepository extends JpaRepository<Commit,Long> {
	Optional<Commit> findByMemberAndLocalDate(Member m, LocalDate commitDate);
}
