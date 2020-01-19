package net.wmfs.coalesce.aa.access.db.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cs_tdl_access", schema = "irmis")
public class AAAccess {

	@Id
	@Column(name = "acc_id", nullable = false)
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "CS_TDL_ACCESS_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	private Long id;
	
	@Column(name = "acc_emp_no")
	private Long empNo;
	
	@Column(name = "acc_task_group_id")
	private Long taskGroupId;
	
	@Column(name = "acc_org_id")
	private Long orgId;
	
	@Column(name = "acc_duty_type_id")
	private Long dutyTypeId;
	
	@Column(name = "acc_justification")
	private String justification;
	
	@Column(name = "acc_is_approved")
	private String isApproved;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Long empNo) {
		this.empNo = empNo;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	public Long getTaskGroupId() {
		return taskGroupId;
	}

	public void setTaskGroupId(Long taskGroupId) {
		this.taskGroupId = taskGroupId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDutyTypeId() {
		return dutyTypeId;
	}

	public void setDutyTypeId(Long dutyTypeId) {
		this.dutyTypeId = dutyTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dutyTypeId == null) ? 0 : dutyTypeId.hashCode());
		result = prime * result + ((empNo == null) ? 0 : empNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isApproved == null) ? 0 : isApproved.hashCode());
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((taskGroupId == null) ? 0 : taskGroupId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AAAccess other = (AAAccess) obj;
		if (dutyTypeId == null) {
			if (other.dutyTypeId != null)
				return false;
		} else if (!dutyTypeId.equals(other.dutyTypeId))
			return false;
		if (empNo == null) {
			if (other.empNo != null)
				return false;
		} else if (!empNo.equals(other.empNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isApproved == null) {
			if (other.isApproved != null)
				return false;
		} else if (!isApproved.equals(other.isApproved))
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (taskGroupId == null) {
			if (other.taskGroupId != null)
				return false;
		} else if (!taskGroupId.equals(other.taskGroupId))
			return false;
		return true;
	}
	
}
