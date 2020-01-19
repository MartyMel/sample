package net.wmfs.coalesce.aa.access.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.wmfs.coalesce.aa.access.vo.DutyType;
import net.wmfs.coalesce.aa.access.vo.Group;
import net.wmfs.coalesce.aa.access.vo.Organisation;
import net.wmfs.coalesce.aa.access.ws.WebServicesInvoker;
import net.wmfs.coalesce.lov.DynamicLOV;
import net.wmfs.coalesce.lov.DynamicLOVNode;


@RestController
@Slf4j
public class DataController {
	
	//private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@Autowired
	private WebServicesInvoker wsInvoker;

	@RequestMapping(value = "ajax/getDutyType.json", method = RequestMethod.GET)
	public String getDutyType(HttpSession session, HttpServletResponse response,
			@RequestParam(value = "groupId", required = true) long groupId,
			@RequestParam(value = "orgId", required = true) long orgId,
			@RequestParam(value = "empNo", required = true) String empNo) {
		
		List<DutyType> types = new ArrayList<>();
		try {
			DynamicLOV result = wsInvoker.getDutyTypeList(empNo, groupId, orgId, true);
			for (DynamicLOVNode node : result.getNodes()) {
				DutyType type = new DutyType();
				if (node.getValue() != null) {
					type.setDtId(Long.parseLong(node.getValue()));
				}
				type.setLabel(node.getLabel());
				types.add(type);
			}
		} catch (Exception ex) {
			log.error("An error occurred whilst retrieving the duty type list", ex);
		}
		
		Gson gson = new Gson();
		return gson.toJson(types);
	}

	@RequestMapping(value = "ajax/getGroups.json", method = RequestMethod.GET)
	public String getGroups(HttpSession session, HttpServletResponse response,
			@RequestParam(value = "empNo", required = true) String empNo) throws IOException {
		
		List<Group> groups = new ArrayList<>();
		try {
			DynamicLOV result = wsInvoker.getGroups(empNo);
			for (DynamicLOVNode node : result.getNodes()) {
				Group group = new Group();
				
				group.setGroupId(Long.parseLong(node.getValue()));
				group.setLabel(node.getLabel());
				groups.add(group);
			}
		} catch (Exception ex) {
			log.error("An error ocurred whilst retrieving the groups", ex);
		}
		
		Gson gson = new Gson();
		return gson.toJson(groups);
	}

	@RequestMapping(value = "ajax/getOrganisation.json", method = RequestMethod.GET)
	public String getOrganisations(HttpSession session, HttpServletResponse response,
			@RequestParam(value = "groupId", required = true) long groupId,
			@RequestParam(value = "empNo", required = true) String empNo) throws IOException {
		
		List<Organisation> organisations = new ArrayList<>();
		try {
			DynamicLOV result = wsInvoker.getOrganisationList(empNo, groupId);
			for (DynamicLOVNode node : result.getNodes()) {
				Organisation organisation = new Organisation();
				organisation.setLocId(Long.parseLong(node.getValue()));
				organisation.setLabel(node.getLabel());
				organisations.add(organisation);
			}
		} catch (Exception ex) {
			log.error("An error occurred whilst retrieving the origanisation list", ex);
		}
		
		Gson gson = new Gson();
		return gson.toJson(organisations);
	}
}
