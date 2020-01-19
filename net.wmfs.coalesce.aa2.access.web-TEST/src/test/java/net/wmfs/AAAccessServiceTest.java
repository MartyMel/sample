package net.wmfs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.wmfs.coalesce.aa.access.service.AAAccessService;
import net.wmfs.coalesce.aa.access.vo.AccessVO;
import net.wmfs.coalesce.aa.access.vo.Client;
import nl.jqno.equalsverifier.EqualsVerifier;

@RunWith(MockitoJUnitRunner.class)
public class AAAccessServiceTest {

	@Mock
	private AAAccessService aaAccessService;

	private AccessVO accessVo;
	private List<AccessVO> accessVOList;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		accessVOList = new ArrayList<AccessVO>();

		accessVo = new AccessVO(1L, 1111L, "TG", "Org", "DT", "Justification Test", null);
		System.out.println(accessVo.empNo());
		System.out.println(accessVo.toString());
		System.out.println(accessVo.hashCode());

		// cant set when attribute is final
		// accessVo.isApproved("true");
		// System.out.println(accessVo.isApproved());
		accessVOList.add(accessVo);

		Client config = Client.builder().host("host").port(123).useHttps(true).build();
	}

	@Test
	public void equalsHashCodeContracts() {
		EqualsVerifier.forClass(AccessVO.class).verify();
	}

	@Test
	public void testMockCreation() {
		assertNotNull(aaAccessService);
		assertNotNull(accessVo);
	}

	@Test
	public void testFindByEmpNo() {
		when(aaAccessService.findByEmpNo(null, 1111L)).thenReturn(accessVOList);
		assertEquals("TG", aaAccessService.findByEmpNo(null, 1111L).get(0).taskGroup());
	}

	@Test
	public void testGetJustification() {
		when(aaAccessService.getJustification(1111L, 2006L, 493L, 23L)).thenReturn("Justification Test");
		assertEquals("Justification Test", aaAccessService.getJustification(1111L, 2006L, 493L, 23L));
	}

	@Test
	public void testUpdateIsApprove() {
		when(aaAccessService.updateIsApprove(1111L, 2006L, 493L, 23L)).thenReturn(1);
		assertEquals(1, aaAccessService.updateIsApprove(1111L, 2006L, 493L, 23L));
	}

	@Test
	public void testUpdateAAAccess() {
		when(aaAccessService.updateAAAccess(1L, 2006L, 493L, 23L, "Justification Test", "Y")).thenReturn(1);
		assertEquals(1, aaAccessService.updateAAAccess(1L, 2006L, 493L, 23L, "Justification Test", "Y"));
	}

	@Test
	public void testDeleteById() {
		doNothing().when(aaAccessService).deleteById(1L);
		aaAccessService.deleteById(1L);
		verify(aaAccessService, times(1)).deleteById(1L);
	}
}