package ch.saunah.saunahbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckCoverageTest {
    /**
     * A sample dummy test to CI/CD.
     */
	@Test
	void dummyTest() {
        CodeCoverageCheck check = new CodeCoverageCheck();
        assertEquals(1, check.checkCoverageOne() );
        assertEquals(3, check.checkCoverageTwo(3));
	}
}
