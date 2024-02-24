import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stepup.task1.Account;
import ru.stepup.task1.AccountState;
import ru.stepup.task1.Currency;
import ru.stepup.task1.EmptyAccountHistoryException;

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
        acc.setCurrencyValue(Currency.RUB, 100);
        Assertions.assertEquals(acc.getValues().get(Currency.RUB), 100);
        Assertions.assertThrows(IllegalArgumentException.class
                            , ()-> acc.setCurrencyValue(Currency.RUB, -10));
        Assertions.assertThrows(IllegalArgumentException.class
                , ()-> acc.setCurrencyValue(Currency.RUB, null));
    }
    @Test
    public void testAccountUndo(){
        Account account = new Account("vasia");
        Assertions.assertFalse(account.isUndoAvailable());
        AccountState s1 = account.save();
        account.setName("igor");
        Assertions.assertTrue(account.isUndoAvailable());
        AccountState s2 = account.save();
        account.setCurrencyValue(Currency.RUB, 100);
        AccountState s3 = account.save();
        account.setCurrencyValue(Currency.RUB, 200);
        account.undo();
        Assertions.assertEquals(s3, account.save());
        account.undo();
        Assertions.assertEquals(s2, account.save());
        account.undo();
        Assertions.assertEquals(s1, account.save());
        Assertions.assertFalse(account.isUndoAvailable());
        Assertions.assertThrows(EmptyAccountHistoryException.class
                , account::undo);
    }

    @Test
    public void testAccountSave(){
        Account acc = new Account("vasia");
        acc.setCurrencyValue(Currency.EUR, 5);
        acc.setCurrencyValue(Currency.RUB, 10);
        AccountState state = acc.save();
        Assertions.assertEquals(state.getName(), acc.getName());
        Assertions.assertEquals(state.getValues(), acc.getValues());
    }
    @Test
    public void testAccountRestore(){
        Account acc = new Account("vasia");
        acc.setCurrencyValue(Currency.EUR, 5);
        acc.setCurrencyValue(Currency.RUB, 10);
        AccountState state = acc.save();
        acc.setName("igor");
        acc.setCurrencyValue(Currency.RUB, 200);
        acc.restore(state);
        Assertions.assertEquals(state, acc.save());
    }



}
