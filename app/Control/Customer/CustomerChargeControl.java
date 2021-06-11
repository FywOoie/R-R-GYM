package app.Control.Customer;

import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;

/**
 * This class is the control class for the changePassword.
 * This class will control the changing password of users.
 * It also controls the action of the elements in the GUI.
 */
public class CustomerChargeControl{

    /**
     * This method is used for the customer to charge.
     *
     * @param amount The amount of money to be charged
     * @return Indicate whether the charge process is successful
     */
    public boolean charge(String amount) {
        CustomerAccount customerAccount = (CustomerAccount) CurrentAccount.getCurAccount();
        int charge = customerAccount.topup(customerAccount.getUid(),Double.parseDouble(amount));
        CurrentAccount.getCurAccount().setBalance(Double.parseDouble(amount) + CurrentAccount.getCurAccount().getBalance());
        return charge != 0;
    }
}