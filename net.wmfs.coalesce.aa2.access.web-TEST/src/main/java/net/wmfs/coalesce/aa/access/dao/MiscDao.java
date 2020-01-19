package net.wmfs.coalesce.aa.access.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MiscDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getGroupName(Long groupId) {
		
		return jdbcTemplate
				.queryForObject("select gr_group_label from cs_tdl_groups where gr_id = " + groupId, String.class);
	}
	
	public String getOrgName(Long orgId){
		return jdbcTemplate
				.queryForObject("select or_name from cs_tdl_organisations where or_id = " + orgId, String.class);
	}
	
	public String getDutyTypeName(Long dutyTypeId){
		return jdbcTemplate
				.queryForObject("select dt_name from cs_tdl_duty_types where dt_id = " + dutyTypeId, String.class);
	}
}
