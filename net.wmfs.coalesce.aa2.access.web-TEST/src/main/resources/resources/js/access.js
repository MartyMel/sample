var GET_GROUPS_URL = "/aa-test/ajax/getGroups.json";
var GET_ORGANISATION_URL = "/aa-test/ajax/getOrganisation.json";
var GET_DUTYTYPE_URL = "/aa-test/ajax/getDutyType.json";

var POST_ACCESS_URL = "/aa-test/updateAccess.html";
var DELETE_ACCESS_URL = "/aa-test/deleteAccess.html";

var groupId;
var orgId;
var dutyTypeId;

var hasError;

$(document).ready(function(){
	$("#myInput").enterKey(function () {
		if($(this).val()){
			var url = window.location.href;
			var path = window.location.pathname;
		  
			url = url.replace(path, "/aa-test/" + $(this).val() + "/access.html")
			window.location = url;
		}
	});
	
	$("#myInput").ForceNumericOnly();
	
	$("#accessTable").find('tr').each(function(){
		if($(this).find('td:last input').val() == 'Y'){
			$(this).addClass('success');
		} 
		if($(this).find('td:last input').val() == 'N' || $(this).find('td:last input').val() == ''){
			$(this).addClass('danger');
		}
	});
  
	$("#accessTable .glyphicon-edit").click(function(){
		var id = $(this).parent().find('input[class=accessId]').val();
		
		$("#editTable #idEdit").val(id);
		
		$("#editTable #taskGroupBtn").text($("#accessTaskGroup" + id).text());
		loadTaskGroup($("#empNo").val());
		
		$("#editTable #orgBtn").text($("#accessOrg" + id).text());
		
		$("#editTable #dutyTypeBtn").text($("#accessDutyType" + id).text());
		
		$("#editTable #justificationTA").val($("#accessJustification" + id).text());
		
		if($("#accessIsApproved" + id).val() == 'Y'){
			$("#editTable #isApprovedCB").attr("checked", "checked");
		} else {
			$("#editTable #isApprovedCB").removeAttr("checked");
		}
		
		$("#editAccessModal").modal("show");
	});
  
	$("#accessTable .glyphicon-trash").click(function(){

		var id = $(this).parent().find('input[class=accessId]').val();
	  
		$("#deleteModalTable #taskGroupBtn").text($("#accessTaskGroup" + id).text());
		$("#deleteModalTable #taskGroupBtn").val($("#accessTaskGroup" + id).text());
		
		$("#deleteModalTable #orgBtn").text($("#accessOrg" + id).text());
		$("#deleteModalTable #orgBtn").text($("#accessOrg" + id).text());
		
		$("#deleteModalTable #dutyTypeBtn").text($("#accessDutyType" + id).text());
		$("#deleteModalTable #dutyTypeBtn").text($("#accessDutyType" + id).text());
		
		$("#deleteModalTable #justificationTA").val($("#accessJustification" + id).text());
		
		$("#deleteModal").modal("show");
		
		$("#deleteModalTable #deleteButton").click(function(){
			$.ajax({
				"dataType" : "long",
				"type" : "POST",
				"url" : DELETE_ACCESS_URL,
				"data" : {
					"id" : id,
					"_csrf" : $("#csrfToken").val()
				},
				statusCode: {
					200 : function(){
						$("#deleteModal").modal("hide");
						
						$("#accessTable").find('tr').each(function(){
							var id = $(this).find('input[class=accessId]').val();
				  			
				  			if($("#accessTaskGroup" + id).text() == $("#deleteModalTable #taskGroupBtn").text() && $("#accessOrg" + id).text() == $("#deleteModalTable #orgBtn").text() && $("#accessDutyType" + id).text() == $("#deleteModalTable #dutyTypeBtn").text()){
				  				$(this).hide();	
				  			}
						})
					}
				}
			});
		});
		
		$("#deleteModalTable #cancelButton").click(function(){
			$("#deleteModal").modal("hide");
		})
	}); 
	
	$("#saveButton").click(function(){
		if(! validate()){
		
			var accessObj = {};
		  	var id = $("#editTable #idEdit").val();
		  	
		  	accessObj.id = id
		  	
		  	accessObj.taskGroupId = $("#editAccessModal #editTable #taskGroupBtn").val();
		  	$("#accessTaskGroup" + id).text($("#editAccessModal #editTable #taskGroupBtn").text());
		  	
		  	accessObj.orgId = $("#editAccessModal #editTable #orgBtn").val();
		  	$("#accessOrg" + id).text($("#editAccessModal #editTable #orgBtn").text());
		  	
		  	accessObj.dutyTypeId = $("#editAccessModal #editTable #dutyTypeBtn").val();
		  	$("#accessDutyType" + id).text($("#editAccessModal #editTable #dutyTypeBtn").text());
		  	
		  	accessObj.justification = $("#editAccessModal #editTable #justificationTA").val();
		  	
		  	$("#accessJustification" + id).text($("#editAccessModal #editTable #justificationTA").val());
		  	
		  	if($("#editAccessModal #editTable #isApprovedCB").is(":checked")){
		  		accessObj.isApproved = 'Y';
		  		$("#accessIsApproved" + id).val("Y");
		  		$("#accessTaskGroup" + id).parent().removeClass('danger');
		  		$("#accessTaskGroup" + id).parent().addClass('success');
		  	} else {
		  		accessObj.isApproved = 'N';
		  		$("#accessIsApproved" + id).val("N");
		  		$("#accessTaskGroup" + id).parent().addClass('danger');
		  		$("#accessTaskGroup" + id).parent().removeClass('success');
		  	}
		  	
		  	accessObj[$("#csrfParameterName").val()] = $("#csrfToken").val();
		  	$.ajax({
		  		"type" : "POST",
		  		"url" : POST_ACCESS_URL,
		  		"data": accessObj,
		  		statusCode: {
		  			200 : function(){
		  				$("#editAccessModal").modal("hide");
		  			}
		  		}
		  	});
		}
	});
	
	if($("#taskGroupBtn").text() != 'Select Task Group'){
		$("#saveButton").removeAttr('disabled');  
	}
	
	if($("#editAccessModal #cancelButton").click(function(){
		$("#editAccessModal").modal("hide");
	}));
	
	if($("#rejectButton").click(function(){
		$("#approvalTable").hide();
	}));
	
	$("#editAccessModal #justificationTA").on('keypress', function(){
		$("#editAccessModal #justificationTA").removeClass('btn-error');
	});
});

jQuery.fn.ForceNumericOnly =
	function()
	{
	    return this.each(function()
	    {
	        $(this).keydown(function(e)
	        {
	            var key = e.charCode || e.keyCode || 0;
	            // allow backspace, enter, delete, arrows, numbers and keypad numbers ONLY
	            // home and end
	            return (
	                key == 8 || 
	                key == 13 ||
	                key == 46 ||
	                (key >= 35 && key <= 40) ||
	                (key >= 48 && key <= 57) ||
	                (key >= 96 && key <= 105));
	        });
	    });
	};

$.fn.enterKey = function (fnc) {
	return this.each(function () {
        $(this).keypress(function (ev) {
            var keycode = (ev.keyCode ? ev.keyCode : ev.which);
            if (keycode == '13') {
                fnc.call(this, ev);
            }
        })
    })
}

function approveAccess(){	 
	$.ajax({
		"type" : "GET",
		"url" : "/aa-test/" + $("#empNo").val() + "/" + $("#taskGroup").val() + "/" + $("#organisation").val() + "/" + $("#dutyType").val() + "/addAccess.html",
	  	"data": {
	  		"isApproved" : "Y"
	  	},
	  	"success": function(data){
	  		$("#accessTable").find('tr').each(function(){
	  			var id = $(this).find('input[class=accessId]').val();
	  			
	  			if($("#accessTaskGroup" + id).text() == $("#taskGroup").text() && $("#accessOrg" + id).text() == $("#organisation").text() && $("#accessDutyType" + id).text() == $("#dutyType").text()){
	  				$("#accessIsApproved" + id).val("Y");
	  				
	  				$(this).removeClass('danger');
	  				$(this).addClass('success');	
	  			}
	  		})
	  			 
	  		$("#approvalLabel").show();
	  		$("#approvalLabel, #approvalTable").delay(1000).fadeOut("slow");
	  	}
	});
}

function validate(){
	hasError = false;
	if($("#editAccessModal #taskGroupBtn").text() == "Select Task Group"){
		$("#editAccessModal #taskGroupBtn").addClass('btn-error');
		hasError = true;
	}
	if($("#editAccessModal #orgBtn").text() == "Select Organisation"){
		$("#editAccessModal #orgBtn").addClass('btn-error');
		hasError = true;
	}
	if($("#editAccessModal #dutyTypeBtn").text() == "Select Duty Type"){
		$("#editAccessModal #dutyTypeBtn").addClass('btn-error');
		hasError = true;
	}
	if($("#editAccessModal #justificationTA").val() == null || $("#editAccessModal #justificationTA").val() == ''){
		$("#editAccessModal #justificationTA").addClass('btn-error');
		hasError = true;
	}
	return hasError;
}

function loadTaskGroup(empNo){
	var taskGroupUl = $("#taskGroupUl");
	taskGroupUl.html("");
	
	$.ajax({
		"dataType" : "json",
		"type" : "GET",
		"url" : GET_GROUPS_URL + "?empNo=" + empNo,
		"success" : function(groups) {
			for (var grId = 0; grId < groups.length; grId++) {
				var li = $("<li>").attr("role", "presentation").appendTo(taskGroupUl);
				
				var groupLabel = groups[grId].label;
				groupId = groups[grId].groupId;
				
				$("<a>").attr("role", "menuitem").val(groupId).html(groupLabel).appendTo(li);
				
				if(groupLabel == $("#editAccessModal #editTable #taskGroupBtn").text()){
					$("#editAccessModal #editTable #taskGroupBtn").val(groupId);
					
					loadOrganisations(empNo, groupId);
				}
			}
			
			taskGroupUl.find('a').click(function(){
				$("#editAccessModal #taskGroupBtn").text($(this).text());
				$("#editAccessModal #taskGroupBtn").val($(this).val());
				$("#editAccessModal #taskGroupBtn").removeClass('btn-error');
				
				$("#editAccessModal #orgBtn").text("Select Organisation");
				$("#editAccessModal #orgBtn").val("Select Organisation");
				
				$("#editAccessModal #dutyTypeBtn").text("Select Duty Type");
				$("#editAccessModal #dutyTypeBtn").val("Select Duty Type");
				
				groupId = $(this).val();
				loadOrganisations(empNo, $(this).val());
			});
		}
	});
}

function loadOrganisations(empNo, groupId){
	var orgUl = $("#orgUl");
	orgUl.html("");
	
	$.ajax({
		"dataType" : "json",
		"type" : "GET",
		"url" : GET_ORGANISATION_URL + "?empNo=" + empNo + "&groupId=" + groupId,
		"success" : function(organisations) {
			for (var grId = 0; grId < organisations.length; grId++) {
				var li = $("<li>").attr("role", "presentation").appendTo(orgUl);
				
				var orgLabel = organisations[grId].label;
				orgId = organisations[grId].orgId;
				
				$("<a>").attr("role", "menuitem").val(orgId).html(orgLabel).appendTo(li);
				
				if(orgLabel == $("#editAccessModal #editTable #orgBtn").text()){
			 		$("#editAccessModal #editTable #orgBtn").val(orgId);
			 		
			 		loadDutyType(empNo, groupId, orgId);
			 	}
			}
			
			orgUl.find('a').click(function(){
				$("#editAccessModal #orgBtn").text($(this).text());
				$("#editAccessModal #orgBtn").val($(this).val());
				$("#editAccessModal #orgBtn").removeClass('btn-error');
				
				$("#editAccessModal #dutyTypeBtn").text("Select Duty Type");
				$("#editAccessModal #dutyTypeBtn").val("Select Duty Type");
				
				orgId = $(this).val();
				loadDutyType(empNo, groupId, $(this).val());
		     });
		}
  });			 
}

function loadDutyType(empNo, groupId, orgId){
	var dutyTypeUl = $("#dutyTypeUl");
	dutyTypeUl.html("");
	
	$.ajax({
		"dataType" : "json",
		"type" : "GET",
		"url" : GET_DUTYTYPE_URL + "?empNo=" + $("#empNo").val() + "&groupId=" + groupId + "&orgId=" + orgId,
		"success" : function(dutyTypes) {
		for (var grId = 0; grId < dutyTypes.length; grId++) {
			var li = $("<li>").attr("role", "presentation").appendTo(dutyTypeUl);
			
			var dutyTypeLabel = dutyTypes[grId].label;
			dutyTypeId = dutyTypes[grId].dtId;
			
			$("<a>").attr("role", "menuitem").val(dutyTypeId).html(dutyTypeLabel).appendTo(li);
			
			if(dutyTypeLabel == $("#editAccessModal #editTable #dutyTypeBtn").text()){
				$("#editAccessModal #editTable #dutyTypeBtn").val(dutyTypeId);
			}
		}
		
		if(dutyTypes.length == 0){
			$("#editAccessModal #dutyTypeBtn").text("No Duty Type");
			$("#editAccessModal #dutyTypeBtn").val(null);
			$("#editAccessModal #dutyTypeBtn").removeClass('btn-error');
		}
		
		dutyTypeUl.find('a').click(function(){
			$("#editAccessModal #dutyTypeBtn").text($(this).text());
			$("#editAccessModal #dutyTypeBtn").val($(this).val());
			$("#editAccessModal #dutyTypeBtn").removeClass('btn-error');
			
			dutyTypeId = $(this).val();
		});
	}
 });
}