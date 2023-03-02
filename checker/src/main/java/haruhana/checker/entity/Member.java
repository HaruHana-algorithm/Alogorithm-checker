package haruhana.checker.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_id;

	private String name;

	private String email;

	private LocalDate commitTime;

	private String imgUrl;

	@OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
	private List<Commit> commit;

	public void updateMemberInfo(String name,String email,LocalDate commitTime,String imgUrl){
		this.name=name;
		this.email=email;
		this.commitTime=commitTime;
		this.imgUrl=imgUrl;
	}
}
