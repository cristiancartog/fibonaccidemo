package ro.smartbill.fibonaccier.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.smartbill.fibonaccier.model.UserContext;
import ro.smartbill.fibonaccier.service.exception.UserNotFoundException;

import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

@Service
@AllArgsConstructor
public class FibonacciComputerService {

    private UserContextRepository userContextRepository;

    public int next(final String user) throws UserNotFoundException {
        if (!hasLength(user)) {
            throw new UserNotFoundException(user);
        }

        UserContext userContext = userContextRepository.getUserContext(user);

        var index = userContext.getIndex();
        List<Integer> values = userContext.values();
        var size = values.size();

        int result;
        if (index < size) {
            result = values.get(index);
        } else {
            var nextFiboValue = nextFibonacciValue(values);
            userContext.addValue(nextFiboValue);
            result = nextFiboValue;
        }

        userContext.incrementIndex();

        return result;
    }

    private int nextFibonacciValue(final List<Integer> values) {
        var size = values.size();
        if (size > 1) {
            return values.get(size - 1) + values.get(size - 2);
        }

        return 1;
    }

    public void back(final String user) throws UserNotFoundException {
        var userContext = userContextRepository.findUserContext(user);

        if (userContext == null) {
            throw new UserNotFoundException(user);
        }

        userContext.decrementIndex();
    }

    public List<Integer> values(final String user) throws UserNotFoundException {
        var userContext = userContextRepository.findUserContext(user);

        if (userContext == null) {
            throw new UserNotFoundException(user);
        }

        return userContext.values();
    }

}
