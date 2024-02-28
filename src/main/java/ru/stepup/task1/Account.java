package ru.stepup.task1;

import lombok.Getter;

import java.util.*;




public class Account {

    @Getter
    private String name;
    private Map<Currency, Integer> values = new HashMap<>();
    private Deque<Executable> history = new ArrayDeque<>();
    private AccountState state;

    public Account(String name){
        if (name == null || name.length() == 0) throw new IllegalArgumentException("Cannot create account: empty name!");
        this.name = name;
    }

    public void setName(String name) {
        String tmp = this.name;
        history.push(()-> Account.this.name = tmp);
        this.name = name;
    }

    public HashMap<Currency, Integer> getValues() {
        return new HashMap<>(values);
    }

    public void save(){
       this.state = new AccountState(this);
    }

    public void putCurrencyValue(Currency cur, Integer value){
        if (cur == null) throw new IllegalArgumentException("Currency must be not null!");
        if (value == null || value < 0) throw new IllegalArgumentException("value must be > 0!");

        Executable action;

        if (this.values.containsKey(cur))
            action = ()->Account.this.values.put(cur, values.get(cur));
        else
            action = ()-> Account.this.values.remove(cur);

        history.push(action);
        values.put(cur, value);
    }
    public void restore(){
        this.name = this.state.getName();
        this.values = this.state.getValues();
        this.history = new ArrayDeque<>();
    }
    public void undo(){
        if (history.isEmpty()) throw new EmptyAccountHistoryException("Cannot undo last change: History is empty!");
        Executable changer = history.pop();
        changer.execute();
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
