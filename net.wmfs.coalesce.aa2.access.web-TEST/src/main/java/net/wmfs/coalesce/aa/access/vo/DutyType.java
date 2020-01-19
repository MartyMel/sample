package net.wmfs.coalesce.aa.access.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DutyType {
	
	private String label;
	private long dtId;

	public long getDtId() {
		return dtId;
	}

	public String getLabel() {
		return label;
	}

	public void setDtId(long dtId) {
		this.dtId = dtId;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
