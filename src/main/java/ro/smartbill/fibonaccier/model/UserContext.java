package ro.smartbill.fibonaccier.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class UserContext {

    public static final int MIN_INDEX = 0;

    @Getter
    private int index = MIN_INDEX;
    private final List<Integer> values = new ArrayList<>(List.of(1));

    public List<Integer> values() {
        return new ArrayList<>(values);
    }

    public void addValue(int value) {
        values.add(value);
    }

    public void incrementIndex() {
        index++;
    }

    public void decrementIndex() {
        if (index > MIN_INDEX) {
            index--;
        }
    }
}
