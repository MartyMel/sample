package net.wmfs.coalesce.aa.access.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.wmfs.coalesce.lov.DynamicLOV;
import net.wmfs.coalesce.ws.WebServiceInvocationException;
import net.wmfs.coalesce.ws.client.WSClient;

@Component
public class WebServicesInvokerImpl implements WebServicesInvoker {
	
	private static final Logger logger = LoggerFactory.getLogger(WebServicesInvokerImpl.class);
	
	private static final String ERROR_MSG_PREFIX = "An error occurred whilst calling the ";
	private static final String ERROR_MSG_POSTFIX = " web service";
	
	@Value("${net.wmfs.coalesce-ws.url}")
	private String webServiceUrl;

	@Value("${aa.todo.groupsServiceName}")
	private String groupsServiceName;

	@Value("${aa.todo.locationServiceName}")
	private String locationServiceName;

	@Value("${aa.todo.dutyTypeServiceName}")
	private String dutyTypeServiceName;
	

	@Override
	public DynamicLOV getDutyTypeList(String employeeNumber, long groupId, long locId, boolean excl)
			throws WebServiceInvocationException {

		WSClient wsClient = new WSClient(webServiceUrl);
		try {
			return (DynamicLOV) wsClient.getResult(dutyTypeServiceName,
					new Object[] { employeeNumber, groupId, locId, true });
		} catch (Exception e) {
			logger.error(ERROR_MSG_PREFIX + dutyTypeServiceName + ERROR_MSG_POSTFIX, e);
			return null;
		}
	}

	
	@Override
	public DynamicLOV getGroups(String employeeNumber) throws WebServiceInvocationException {

		WSClient wsClient = new WSClient(webServiceUrl);
		try {
			DynamicLOV result = (DynamicLOV) wsClient.getResult(groupsServiceName, new Object[] { employeeNumber });
			logger.debug("Groups: {}", result);
			return result;
		} catch (Exception e) {
			logger.error(ERROR_MSG_PREFIX + groupsServiceName + ERROR_MSG_POSTFIX, e);
			return null;
		}
	}

	
	@Override
	public DynamicLOV getOrganisationList(String employeeNumber, long groupId) throws WebServiceInvocationException {

		WSClient wsClient = new WSClient(webServiceUrl);
		try {
			DynamicLOV result = (DynamicLOV) wsClient.getResult(locationServiceName,
					new Object[] { employeeNumber, groupId });
			logger.debug("Organisation Label: {}", result.getNodes().get(0).getLabel());
			return result;
		} catch (Exception e) {
			logger.error(ERROR_MSG_PREFIX + locationServiceName + ERROR_MSG_POSTFIX, e);
			return null;
		}
	}
}
