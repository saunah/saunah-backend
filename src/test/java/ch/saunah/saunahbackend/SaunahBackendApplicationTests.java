package ch.saunah.saunahbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;

@SpringBootTest(classes = SaunahBackendApplication.class)
class SaunahBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    /**
     * A sample dummy test to CI/CD.
     */
	@Test
	void dummyTest() {
        assertTrue(true);
	}

    /**
     * A sample dummy test to test connection testing datasource
     * realized by a H2 in memory database.
     */
    @Test
    void dummyTestDataSource() {
        Iterable<User> users = userRepository.findAll();
        assertFalse(users.iterator().hasNext());

        User newUser = new User();
        newUser.setEmail("test@mail.com");
        newUser.setPasswordHash("root");
        newUser.setFirstName("Max");
        newUser.setLastName("Müller");
        newUser.setPhoneNumber("079 123 45 67");
        newUser.setZip("8500");
        newUser.setPlace("Winterthur");
        newUser.setStreet("Müllerstrasse 20");
        newUser.setRole(UserRole.USER);
        newUser.setActivationId(UUID.randomUUID().toString());
        userRepository.save(newUser);

        users = userRepository.findAll();
        assertEquals(1, getIterableSize(users));
    }

    // -- Helper methods

    /**
     * Example helper method to get size of an iterable.
     */
    private <T> long getIterableSize(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }
}
