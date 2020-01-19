package net.wmfs.coalesce.aa.access.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.wmfs.coalesce.aa.access.db.vo.AAAccess;

@Repository
public interface AAAccessRepo extends CrudRepository<AAAccess, Long> {

	List<AAAccess> findByEmpNoOrderByIsApprovedDesc(Long empNo);

	AAAccess findJustificationByEmpNoAndTaskGroupIdAndOrgIdAndDutyTypeId(Long empNo, Long taskGroupId, Long orgId,
			Long dutyTypeId);

	@Modifying
	@Transactional
	@Query("update AAAccess a set isApproved = 'Y' where a.empNo = ?1 and a.taskGroupId = ?2 and a.orgId = ?3 and a.dutyTypeId = ?4")
	int updateIsApproved(Long empNo, Long taskGroupId, Long orgId, Long dutyTypeId);
	
	@Modifying
	@Transactional
	@Query("update AAAccess a set isApproved = 'Y' where a.empNo = ?1 and a.taskGroupId = ?2 and a.orgId = ?3 and a.dutyTypeId is null")
	int updateIsApprovedWithNoDutyTypeId(Long empNo, Long taskGroupId, Long orgId);

	@Modifying
	@Transactional
	@Query("update AAAccess a set a.taskGroupId = ?2, a.orgId = ?3, a.dutyTypeId = ?4, a.justification = ?5, a.isApproved = ?6 where a.id = ?1")
	int updateAAAccess(Long id, Long taskGroupId, Long organisationId, Long dutyTypeId, String justification,
			String isApproved);

	@Transactional
	void deleteById(Long id);
}
