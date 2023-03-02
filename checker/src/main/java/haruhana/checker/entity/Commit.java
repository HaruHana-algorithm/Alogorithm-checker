package haruhana.checker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Commit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commit_seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private State state;

	private LocalDate localDate;


	public void updateCommitInfo(State state){
		this.state=state;
	}
}
