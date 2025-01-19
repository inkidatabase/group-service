package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ComponentScan(basePackages = {
    "inkidatabase.groupservice.repository",
    "inkidatabase.groupservice.mapper"
})
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    private Group btsGroup;
    private Group blackpinkGroup;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();

        btsGroup = new Group("BTS", "HYBE", 2013);
        btsGroup.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        btsGroup.setLabels(Arrays.asList("kpop", "bighit"));
        btsGroup = groupRepository.save(btsGroup);

        blackpinkGroup = new Group("BLACKPINK", "YG", 2016);
        blackpinkGroup.setMembers(Arrays.asList("Jisoo", "Jennie", "Rosé", "Lisa"));
        blackpinkGroup.setLabels(Arrays.asList("kpop", "yg"));
        blackpinkGroup = groupRepository.save(blackpinkGroup);
    }

    @Test
    void findAll_ReturnsAllGroups() {
        List<Group> groups = groupRepository.findAll();
        
        assertThat(groups).hasSize(2);
        assertThat(groups).extracting("groupName").containsExactlyInAnyOrder("BTS", "BLACKPINK");
    }

    @Test
    void findById_WhenExists_ReturnsGroup() {
        Optional<Group> found = groupRepository.findById(btsGroup.getGroupId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getGroupName()).isEqualTo("BTS");
    }

    @Test
    void findByAgency_ReturnsMatchingGroups() {
        List<Group> hybeGroups = groupRepository.findByAgencyIgnoreCase("HYBE");
        
        assertThat(hybeGroups).hasSize(1);
        assertThat(hybeGroups.get(0).getGroupName()).isEqualTo("BTS");
    }

    @Test
    void findByDebutYear_ReturnsMatchingGroups() {
        List<Group> groups2013 = groupRepository.findByDebutYear(2013);
        
        assertThat(groups2013).hasSize(1);
        assertThat(groups2013.get(0).getGroupName()).isEqualTo("BTS");
    }

    @Test
    void findByLabel_ReturnsGroupsWithLabel() {
        List<Group> kpopGroups = groupRepository.findByLabelsContaining("kpop");
        
        assertThat(kpopGroups).hasSize(2);
        assertThat(kpopGroups).extracting("groupName").containsExactlyInAnyOrder("BTS", "BLACKPINK");
    }

    @Test
    void findByMember_ReturnsGroupsWithMember() {
        List<Group> groups = groupRepository.findByMembersContaining("RM");
        
        assertThat(groups).hasSize(1);
        assertThat(groups.get(0).getGroupName()).isEqualTo("BTS");
    }

    @Test
    void findActiveGroups_ReturnsActiveGroups() {
        List<Group> activeGroups = groupRepository.findActiveGroups();
        
        assertThat(activeGroups).hasSize(2);
        assertThat(activeGroups).extracting("groupName").containsExactlyInAnyOrder("BTS", "BLACKPINK");
    }

    @Test
    void findDisbandedGroups_ReturnsDisbandedGroups() {
        // Set BTS as disbanded
        btsGroup.setDisbandYear(2023);
        groupRepository.save(btsGroup);

        List<Group> disbandedGroups = groupRepository.findByDisbandYearGreaterThan(0);
        
        assertThat(disbandedGroups).hasSize(1);
        assertThat(disbandedGroups.get(0).getGroupName()).isEqualTo("BTS");
    }
}
