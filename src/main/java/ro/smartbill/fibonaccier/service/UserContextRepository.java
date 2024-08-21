package ro.smartbill.fibonaccier.service;

import org.springframework.stereotype.Service;
import ro.smartbill.fibonaccier.model.UserContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Can be a real repository fetching data from a DB, an internal or external cache or some client
 * to an external service for managing user data. Currently, it's a simple HashMap to simulate an in-memory cache.
 */
@Service
public class UserContextRepository {

    private final Map<String, UserContext> cache = new HashMap<>();

    public synchronized UserContext getUserContext(final String user) {
        return cache.computeIfAbsent(user, k -> new UserContext());
    }

    public synchronized UserContext findUserContext(final String user) {
        return cache.get(user);
    }

}
