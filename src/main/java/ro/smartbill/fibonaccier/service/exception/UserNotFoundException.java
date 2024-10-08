package ro.smartbill.fibonaccier.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends Exception {

    private String user;

}
