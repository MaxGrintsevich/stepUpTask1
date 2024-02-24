package ru.stepup.task1;

public class Start {
    public static void main(String[] args) {
        Account acc = new Account("vasia");
        AccountState state = new AccountState(acc);
        System.out.println(acc);
        acc.setCurrencyValue(Currency.EUR, 100);
        acc.setCurrencyValue(Currency.RUB, 100);
        System.out.println(acc);

        acc.restore(state);
        acc.setCurrencyValue(Currency.RUB, 100);
        acc.setCurrencyValue(Currency.EUR, 100);
        System.out.println(acc);

    }
}
