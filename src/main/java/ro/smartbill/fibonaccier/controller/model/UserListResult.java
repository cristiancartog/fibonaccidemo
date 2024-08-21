package ro.smartbill.fibonaccier.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserListResult {

    private String user;
    private List<Integer> values;

}
