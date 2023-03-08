package haruhana.checker.repo;

import haruhana.checker.entity.redis.MemberCommit;
import org.springframework.data.repository.CrudRepository;


public interface MemberCommitRepository extends CrudRepository<MemberCommit,String> {
}
