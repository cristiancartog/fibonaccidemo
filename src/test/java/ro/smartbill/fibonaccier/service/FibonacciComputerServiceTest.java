package ro.smartbill.fibonaccier.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.smartbill.fibonaccier.service.exception.UserNotFoundException;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FibonacciComputerServiceTest {

    private static final String USER1 = "USER1";
    private static final String USER2 = "USER2";

    private FibonacciComputerService fiboService;

    @BeforeEach
    public void setUp() {
        fiboService = new FibonacciComputerService(new UserContextRepository());
    }

    @Test
    void testFibonacciComputerServiceByRequirementsExample() throws Exception {
        fiboService.next(USER1);
        fiboService.next(USER1);
        fiboService.next(USER1);
        fiboService.next(USER2);
        fiboService.next(USER1);
        fiboService.next(USER2);
        fiboService.back(USER2);
        fiboService.next(USER2);
        fiboService.next(USER2);

        String user1Values = userValuesAsString(USER1);
        assertEquals("1,1,2,3", user1Values);

        String user2Values = userValuesAsString(USER2);
        assertEquals("1,1,2", user2Values);
    }

    @Test
    void testFibonacciComputerServiceForOneUser() throws Exception {
        assertEquals(1, fiboService.next(USER1));
        assertEquals(1, fiboService.next(USER1));
        assertEquals(2, fiboService.next(USER1));
        assertEquals(3, fiboService.next(USER1));
        assertEquals(5, fiboService.next(USER1));
        assertEquals(8, fiboService.next(USER1));
        assertEquals(13, fiboService.next(USER1));
        assertEquals(21, fiboService.next(USER1));
        assertEquals(34, fiboService.next(USER1));
        assertEquals(55, fiboService.next(USER1));

        fiboService.back(USER1);
        fiboService.back(USER1);

        assertEquals(34, fiboService.next(USER1));
        assertEquals(55, fiboService.next(USER1));
        assertEquals(89, fiboService.next(USER1));

        String user1Values = userValuesAsString(USER1);
        assertEquals("1,1,2,3,5,8,13,21,34,55,89", user1Values);
    }

    @Test
    void testFibonacciComputerServiceNextBackForNullOrEmptyUser() {
        assertThrows(UserNotFoundException.class, () -> fiboService.next(null));
        assertThrows(UserNotFoundException.class, () -> fiboService.next(""));
        assertThrows(UserNotFoundException.class, () -> fiboService.back(null));
        assertThrows(UserNotFoundException.class, () -> fiboService.back(""));
    }

    @Test
    void testFibonacciComputerServiceValuesForUnknownUser() {
        assertThrows(UserNotFoundException.class, () -> fiboService.values("UNKNOWN"));
        assertThrows(UserNotFoundException.class, () -> fiboService.values(""));
        assertThrows(UserNotFoundException.class, () -> fiboService.values(null));
    }

    private String userValuesAsString(final String user) throws UserNotFoundException {
        return fiboService.values(user)
                .stream()
                .map(String::valueOf)
                .collect(joining(","));
    }
}
