package app.Entity.Account;

/**
 * This class is an entity class for current active account.
 * This class is mainly used to store the account which is
 * currently using our software. Since the {@code Account} is
 * the superclass for three different kinds of accounts. We use
 * polymorphism in the class to store different subclasses.
 */
public class CurrentAccount {
    private static Account curAccount; // The current active account.

    /**
     * This method is used to get the current account and
     * obtain information from it.
     *
     * @return The current active account
     */
    public static Account getCurAccount() {
        return curAccount;
    }

    /**
     * This method is only used when the user logs in.
     *
     * @param curAccount The current active account
     */
    public static void setCurAccount(Account curAccount) {
        CurrentAccount.curAccount = curAccount;
    }
}
