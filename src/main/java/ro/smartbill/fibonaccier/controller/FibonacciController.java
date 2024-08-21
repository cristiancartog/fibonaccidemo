package ro.smartbill.fibonaccier.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import ro.smartbill.fibonaccier.controller.model.UserListResult;
import ro.smartbill.fibonaccier.service.FibonacciComputerService;
import ro.smartbill.fibonaccier.service.exception.UserNotFoundException;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/fibo")
@Validated
@AllArgsConstructor
@Log
public class FibonacciController {

    public static final String USER_PARAM = "user";

    private FibonacciComputerService fiboService;

    @PostMapping("/next")
    public int generate(@RequestParam(USER_PARAM) @NotBlank final String user) throws UserNotFoundException {
        return fiboService.next(user);
    }

    @PostMapping("/back")
    public void stepBack(@RequestParam(USER_PARAM) @NotBlank final String user) throws UserNotFoundException {
        fiboService.back(user);
    }

    @GetMapping("/list")
    public UserListResult list(@RequestParam(USER_PARAM) @NotBlank final String user) throws UserNotFoundException {
        return new UserListResult(user, fiboService.values(user));
    }

    //// exception handlers ////

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolations(final ConstraintViolationException exception) {
        log.warning(exception.getMessage());

        var errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s %s", violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(joining(","));

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<Object> handleNullParameterExceptions(final MissingServletRequestParameterException exception) {
        log.warning(exception.getMessage());

        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<Object> handleUserNotFoundExceptions(final UserNotFoundException exception) {
        log.warning(exception.getMessage());

        return new ResponseEntity<>(exception.getMessage(), NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGenericExceptions(final Exception exception) {
        log.warning(exception.getMessage());

        return new ResponseEntity<>(exception.getMessage(), SERVICE_UNAVAILABLE);
    }

}
