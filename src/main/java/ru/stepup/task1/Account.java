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
        history.push(new NameChanger(this));
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
        history.push(new ValueChanger(this, cur));
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

    class ValueChanger implements Executable{
        Currency currency;
        Integer value;
        Account account;
        ValueChanger(Account account, Currency currency){
            this.currency = currency;
            this.value = account.values.containsKey(currency) ? values.get(currency) : null;
            this.account = account;
        }


        @Override
        public void execute() {
            if (this.value == null)
                account.values.remove(currency);
            else account.values.put(currency, value);
        }

        @Override
        public String toString() {
            return "ValueChanger{" +
                    "currency=" + currency +
                    ", value=" + value +
                    '}';
        }
    }

    class NameChanger implements Executable{
        Account account;
        String name;
        NameChanger(Account account){
            this.account = account;
            this.name = account.name;
        }

        @Override
        public void execute() {
            account.name = name;
        }

        @Override
        public String toString() {
            return "NameChanger{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
