package dao;

import model.Account;

public interface AccountDAO {
    void createAccount(Account account);
    boolean emailExists(String username);
    int getAccountIdByEmail(String email);
    Account getAccountByEmail(String email);
    void deleteAccount(int accountId);

}
