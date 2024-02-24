import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stepup.task1.Account;
import ru.stepup.task1.AccountState;
import ru.stepup.task1.Currency;
import ru.stepup.task1.EmptyAccountHistoryException;

import java.util.HashMap;

public class Tester {
    @Test
    public void testAccountConstructor(){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> new Account(null));
        Assertions.assertThrows(IllegalArgumentException.class, ()->new Account(""));
        Account acc = new Account("vasia");
        Assertions.assertEquals("vasia", acc.getName());
    }
    @Test
    public void testAccountSetters(){
        Account acc = new Account("vasia");
        acc.setName("igor");
        Assertions.assertEquals("igor", acc.getName());
        acc.putCurrencyValue(Currency.RUB, 100);
        Assertions.assertEquals(acc.getValues().get(Currency.RUB), 100);
        Assertions.assertThrows(IllegalArgumentException.class
                            , ()-> acc.putCurrencyValue(Currency.RUB, -10));
        Assertions.assertThrows(IllegalArgumentException.class
                , ()-> acc.putCurrencyValue(Currency.RUB, null));
    }
    @Test
    public void testAccountUndo(){
        Account account = new Account("vasia");
        Assertions.assertFalse(account.isUndoAvailable());
        account.setName("igor");
        Assertions.assertTrue(account.isUndoAvailable());
        account.putCurrencyValue(Currency.RUB, 100);
        account.putCurrencyValue(Currency.RUB, 200);
        account.undo();
        account.undo();
        account.undo();
        Assertions.assertFalse(account.isUndoAvailable());
        Assertions.assertThrows(EmptyAccountHistoryException.class
                , account::undo);
    }
    @Test
    public void testAccountRestore(){
        Account acc = new Account("vasia");
        acc.putCurrencyValue(Currency.EUR, 5);
        acc.putCurrencyValue(Currency.RUB, 10);
        HashMap<Currency, Integer> values = acc.getValues();
        acc.save();
        acc.setName("igor");
        acc.putCurrencyValue(Currency.RUB, 200);
        acc.restore();
        Assertions.assertEquals(acc.getName(), "vasia");
        Assertions.assertEquals(acc.getValues().get(Currency.EUR), 5);
        Assertions.assertEquals(acc.getValues(), values);
    }



}
