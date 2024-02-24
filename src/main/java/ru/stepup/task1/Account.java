package ru.stepup.task1;

import lombok.Getter;

import java.util.*;

public class Account {
    public void setName(String name) {
        history.push(save());
        this.name = name;
    }

    @Getter
    String name;

    public HashMap<Currency, Integer> getValues() {
        return new HashMap<>(values);
    }

    Map<Currency, Integer> values = new HashMap<>();

    Stack<AccountState> history = new Stack<>();

    public Account(String name){
        if (name == null || name.length() == 0) throw new IllegalArgumentException("Cannot create account: empty name!");
        this.name = name;
    }

    public AccountState save(){
       return new AccountState(this);
    }

    public void setCurrencyValue(Currency cur, Integer value){
        if (cur == null) throw new IllegalArgumentException("Currency must be not null!");
        if (value == null || value < 0) throw new IllegalArgumentException("value must be > 0!");
        history.push(save());
        values.put(cur, value);
    }
    private void setState(AccountState state){
        this.name = state.getName();
        this.values = state.getValues();
    }
    public void restore(AccountState state){
        this.setState(state);
        this.history = new Stack<>();
    }
    public void undo(){
        if (history.isEmpty()) throw new EmptyAccountHistoryException("Cannot undo last change: History is empty!");
        AccountState state = history.pop();
        setState(state);
    }
    public boolean isUndoAvailable(){
        return !history.isEmpty();
    }
    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", values=" + values +
                ", history=" + history +
                '}';
    }
}
