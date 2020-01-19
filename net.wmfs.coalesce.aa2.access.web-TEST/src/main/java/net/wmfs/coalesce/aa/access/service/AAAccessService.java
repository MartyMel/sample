package net.wmfs.coalesce.aa.access.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.wmfs.coalesce.aa.access.dao.MiscDao;
import net.wmfs.coalesce.aa.access.db.vo.AAAccess;
import net.wmfs.coalesce.aa.access.repo.AAAccessRepo;
import net.wmfs.coalesce.aa.access.util.Grizzly;
import net.wmfs.coalesce.aa.access.util.Runner;
import net.wmfs.coalesce.aa.access.vo.AccessVO;

@Service
public class AAAccessService {

	@Autowired
	public AAAccessRepo aaAccessRepo;

	@Autowired
	MiscDao miscDao;

	@Autowired
	public Runner[] runners;
	
	public void printStuff(){
		for(Runner runner : runners) {
			System.out.println(runner);
		}
		
	}

	public List<AccessVO> findByEmpNo(Pageable pageRequest, Long empNo) {

		List<AAAccess> accesses = aaAccessRepo.findByEmpNoOrderByIsApprovedDesc(empNo);

		List<AccessVO> accessList = new ArrayList<>();
		for (AAAccess aaAccess : accesses) {
			String taskGroupName = null;
			if (aaAccess.getTaskGroupId() != null) {
				taskGroupName = miscDao.getGroupName(aaAccess.getTaskGroupId());
			}
			String orgName = null;
			if (aaAccess.getOrgId() != null) {
				orgName = miscDao.getOrgName(aaAccess.getOrgId());
			}
			String dutyTypeName = "No Duty Type";
			if (aaAccess.getDutyTypeId() != null) {
				dutyTypeName = miscDao.getDutyTypeName(aaAccess.getDutyTypeId());
			}

			AccessVO accessVO = new AccessVO(aaAccess.getId(), aaAccess.getEmpNo(), taskGroupName, orgName,
					dutyTypeName, aaAccess.getJustification(), aaAccess.getIsApproved());

			accessList.add(accessVO);
		}

		return accessList;
	}

	public String getJustification(Long empNo, Long taskGroupId, Long orgId, Long dutyTypeId) {
		return aaAccessRepo
				.findJustificationByEmpNoAndTaskGroupIdAndOrgIdAndDutyTypeId(empNo, taskGroupId, orgId, dutyTypeId)
				.getJustification();
	}

	public int updateIsApprove(Long empNo, Long taskGroupId, Long orgId, Long dutyTypeId) {
		if(dutyTypeId == null){
			return aaAccessRepo.updateIsApprovedWithNoDutyTypeId(empNo, taskGroupId, orgId);
		} else {
			return aaAccessRepo.updateIsApproved(empNo, taskGroupId, orgId, dutyTypeId);
		}
	}

	public int updateAAAccess(Long id, Long taskGroupId, Long organisationId, Long dutyTypeId, String justification,
			String isApproved) {
		return aaAccessRepo.updateAAAccess(id, taskGroupId, organisationId, dutyTypeId, justification, isApproved);
	}

	public void deleteById(Long id) {
		aaAccessRepo.deleteById(id);
	}
}
