package haruhana.checker.service;

import haruhana.checker.dto.MemberCommitDTO;
import haruhana.checker.entity.redis.MemberCommit;
import haruhana.checker.repo.MemberCommitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

	@Transactional
	public void saveTempMemberCommitList(MemberCommitDTO memberCommitDTO){
		memberCommitRepository.save(memberCommitDTO.toEntity());
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
