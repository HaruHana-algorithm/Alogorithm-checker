package haruhana.checker.repo;

import haruhana.checker.dto.projection.MemberResponseDTO;
import haruhana.checker.entity.Member;
import haruhana.checker.repo.projection.MemberProjectionRepository;
import haruhana.checker.repo.projection.ProjectionRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

	Optional<Member> findByName(String name);

	List<ProjectionRepository> findProjectionByEmail(@Param("email")String email);

	List<MemberProjectionRepository> findMemberCommitInfo(MemberResponseDTO memberResponseDTO);
}
