package net.wmfs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import net.wmfs.coalesce.aa.access.db.vo.AAAccess;
import net.wmfs.coalesce.aa.access.repo.AAAccessRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AAAccessRepositoryTest.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableAutoConfiguration
public class AAAccessRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AAAccessRepo aaAccessRepo;

	private AAAccess aaAccess1;
	private AAAccess aaAccess2;
	private AAAccess aaAccess3;

	@Before
	public void before() {
		aaAccess1 = new AAAccess();
		aaAccess1.setEmpNo(1111L);
		aaAccess1.setTaskGroupId(1989L);
		aaAccess1.setOrgId(10001L);
		aaAccess1.setDutyTypeId(null);
		aaAccess1.setJustification("Justification Tested");
		aaAccess1.setIsApproved(null);

		aaAccess2 = new AAAccess();
		aaAccess2.setEmpNo(2222L);
		aaAccess2.setTaskGroupId(3002L);
		aaAccess2.setOrgId(554L);
		aaAccess2.setDutyTypeId(0L);
		aaAccess2.setJustification("Justification Accepted");
		aaAccess2.setIsApproved(null);

		aaAccess3 = new AAAccess();
		aaAccess3.setEmpNo(3333L);
		aaAccess3.setTaskGroupId(2006L);
		aaAccess3.setOrgId(493L);
		aaAccess3.setDutyTypeId(23L);
		aaAccess3.setJustification("Justification Accepted - updated");
		aaAccess3.setIsApproved("Y");
	}

	@After
	public void after() {
		entityManager.flush();
	}

	@Test
	public void contextLoads() throws Exception {

		assertThat(aaAccessRepo).isNotNull();
	}

	@Test
	public void whenfindByEmpNoOrderByIsApprovedDesc_thenReturnAccesslist() {
		entityManager.persist(aaAccess1);
		List<AAAccess> foundAccesses = aaAccessRepo.findByEmpNoOrderByIsApprovedDesc(aaAccess1.getEmpNo());

		assertThat(foundAccesses.equals(aaAccess1));
	}

	@Test
	public void whenFindJustificationByEmpNoAndTaskGroupIdAndOrgIdAndDutyTypeId_thenReturnJustification() {
		entityManager.persist(aaAccess1);
		AAAccess found = aaAccessRepo.findJustificationByEmpNoAndTaskGroupIdAndOrgIdAndDutyTypeId(aaAccess1.getEmpNo(),
				aaAccess1.getTaskGroupId(), aaAccess1.getOrgId(), aaAccess1.getDutyTypeId());

		assertThat(found.getJustification()).isEqualTo(aaAccess1.getJustification());
	}

	@Test
	public void whenUpdateIsApproved_thenReturnRowsAffected() {
		entityManager.persist(aaAccess2);
		int rowsupdated = aaAccessRepo.updateIsApproved(aaAccess2.getEmpNo(), aaAccess2.getTaskGroupId(),
				aaAccess2.getOrgId(), aaAccess2.getDutyTypeId());

		assertThat(rowsupdated).isEqualTo(1);
	}

	@Test
	public void whenUpdateIsApprovedWithNoDutyTypeId_thenReturnRowsAffected() {
		entityManager.persist(aaAccess1);
		int rowsupdated = aaAccessRepo.updateIsApprovedWithNoDutyTypeId(aaAccess1.getEmpNo(),
				aaAccess1.getTaskGroupId(), aaAccess1.getOrgId());

		assertThat(rowsupdated).isEqualTo(1);
	}

	@Test
	public void whenUpdateAAAccess_thenReturnRowsAffected() {
		entityManager.persist(aaAccess2);
		AAAccess foundAccess = aaAccessRepo.findByEmpNoOrderByIsApprovedDesc(aaAccess2.getEmpNo()).get(0);

		int rowsUpdated = aaAccessRepo.updateAAAccess(foundAccess.getId(), aaAccess3.getTaskGroupId(),
				aaAccess3.getOrgId(), aaAccess3.getDutyTypeId(), aaAccess3.getJustification(),
				aaAccess3.getIsApproved());

		assertThat(rowsUpdated).isEqualTo(1);
	}

	@Test
	@Commit
	public void whenDeleteById_thenCheckAccessDoesNotExist() {
		entityManager.persist(aaAccess3);
		AAAccess foundAccess = aaAccessRepo.findByEmpNoOrderByIsApprovedDesc(aaAccess3.getEmpNo()).get(0);

		aaAccessRepo.deleteById(foundAccess.getId());

		List<AAAccess> shouldBeDeletedAccesses = aaAccessRepo.findByEmpNoOrderByIsApprovedDesc(foundAccess.getEmpNo());

		assertThat(shouldBeDeletedAccesses.size()).isEqualTo(0);
	}
}
