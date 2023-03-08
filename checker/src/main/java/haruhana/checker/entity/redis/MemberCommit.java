package haruhana.checker.entity.redis;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;

@Getter
@RedisHash(value = "memberCommit",timeToLive = 300)
@Builder
public class MemberCommit {
	@Id
	private String id;
	private String name;
	private LocalDate commitTime;
}
