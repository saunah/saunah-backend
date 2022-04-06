package ch.saunah.saunahbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRepository;

@SpringBootTest
class SaunahBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    /**
     * A sample dummy test to CI/CD.
     */
	@Test
	void dummyTest() {
        assertEquals(true, true);
	}

    /**
     * A sample dummy test to test connection testing datasource
     * realized by a H2 in memory database.
     */
    @Test
    void dummyTestDataSource() {
        Iterable<User> users = userRepository.findAll();
        assertFalse(users.iterator().hasNext());

        User newUser = new User("John", "john@example.com");
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
