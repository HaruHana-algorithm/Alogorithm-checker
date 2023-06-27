package haruhana.checker.repo.projection;

import haruhana.checker.dto.projection.MemberResponseDTO;

public interface MemberProjectionRepository {
	MemberResponseDTO getCommitInfoToDTOProjection();
}
