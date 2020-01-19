package net.wmfs.coalesce.aa.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.wmfs.coalesce.aa.access.dao.MiscDao;

@Service
public class MiscService {

	@Autowired
	private MiscDao miscDao;

	public String getGroupName(Long groupId) {
		return miscDao.getGroupName(groupId);
	}

	public String getOrgName(Long orgId) {
		return miscDao.getOrgName(orgId);
	}

	public String getDutyTypeName(Long dutyTypeId) {
		return miscDao.getDutyTypeName(dutyTypeId);
	}
}
