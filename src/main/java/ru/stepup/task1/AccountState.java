package ru.stepup.task1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountState {
    private final String name;
    private final Map<Currency,Integer> values;

    public AccountState(Account account){
        this.name = account.getName();
        this.values = account.getValues();
    }

    public String getName() {
        return name;
    }

    public Map<Currency, Integer> getValues() {
        return new HashMap<Currency, Integer>(values);
    }

    @Override
    public String toString() {
        return "AccountState{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountState)) return false;
        AccountState that = (AccountState) o;
        return getName().equals(that.getName()) && getValues().equals(that.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValues());
    }
}
