package haruhana.checker.service;

import haruhana.checker.dto.MemberCommitDTO;
import haruhana.checker.entity.redis.MemberCommit;
import haruhana.checker.repo.MemberCommitRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional(readOnly = true)
@Service
@Log4j2
@RequiredArgsConstructor
public class MemberCommitService {

	private final MemberCommitRepository memberCommitRepository;

	@PostConstruct
	public void init(){
		System.out.println("redis 초기화");
		memberCommitRepository.deleteAll();
	}

	@Transactional
	public void saveTempMemberCommitList(MemberCommitDTO memberCommitDTO){
		long saveStart = System.currentTimeMillis();
		memberCommitRepository.save(memberCommitDTO.toEntity());
		long saveEnd = System.currentTimeMillis();
		System.out.println("redisSave="+(double)((saveEnd-saveStart)/1000.0)+"seconds");
	}

	public List<MemberCommit> getInfoAllMemberCommit(){
		Iterable<MemberCommit> all = memberCommitRepository.findAll();
		List<MemberCommit> sortedList = StreamSupport.stream(all.spliterator(), true)
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(MemberCommit::getCommitTime))
				.collect(Collectors.toList());
		System.out.println("sortedList="+sortedList);
		return sortedList;
	}
}
