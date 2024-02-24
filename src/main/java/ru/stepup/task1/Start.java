package ru.stepup.task1;

public class Start {
    public static void main(String[] args) {
        Account acc = new Account("vasia");
        acc.save();
        System.out.println(acc);
        acc.putCurrencyValue(Currency.EUR, 100);
        acc.putCurrencyValue(Currency.RUB, 100);
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);

        acc.restore();
        acc.putCurrencyValue(Currency.RUB, 100);
        acc.putCurrencyValue(Currency.EUR, 100);
        System.out.println(acc);

    }
}
