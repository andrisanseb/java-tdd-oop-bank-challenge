package com.booleanuk.core;

import org.jetbrains.annotations.NotNull;

public class Customer {
    private Account[] accounts;
    private int id;

    public Customer(int id){
        this.id = id;
        accounts = new Account[2];
    }

    public Account getCurrentAccount(){
        return accounts[0];
    }

    public void setCurrentAccount(Account currentAccount){
        this.accounts[0] = currentAccount;
    }

    public void setSavingsAccount(Account savingsAccount){
        this.accounts[1] = savingsAccount;
    }

    public Account getSavingsAccount(){
        return accounts[1];
    }

    public int getId() {
        return id;
    }

    public boolean hasCurrentAccount(){
        if (getCurrentAccount() == null) {
            return false;
        } else {
            return true;
        }
    }
    public boolean hasSavingsAccount(){
        if (getSavingsAccount() == null) {
            return false;
        } else {
            return true;
        }
    }


    public boolean createCurrentAccount(int initalDeposit) {

        if (hasCurrentAccount()){
            System.out.println("Customer already has a current account");
            return false;
        }
        setCurrentAccount(new Account( Account.getUniqueRandomAccountId(),getId(), "current", initalDeposit));
        getCurrentAccount().addDepositStatement(initalDeposit);
        //first id is account id, second is user id
        Bank.addAccount(getCurrentAccount().getId(), getId(), "current", initalDeposit);
        return true;
    }


    public boolean createSavingsAccount(int initalDeposit) {

        if (hasSavingsAccount()) {
            System.out.println("Customer already has a savings account");
            return false;
        }

        setSavingsAccount(new Account(Account.getUniqueRandomAccountId(), getId(), "savings", initalDeposit));
        getSavingsAccount().addDepositStatement(initalDeposit);
        Bank.addAccount(getSavingsAccount().getId(), getId(), "savings", initalDeposit);
        return true;
    }

    public boolean withdraw(@NotNull String accountType, int ammount) {

        if (accountType.equals("current") && hasCurrentAccount()) {

            int balance = getCurrentAccount().getBalance();
            if (balance >= ammount) {
                getCurrentAccount().setBalance(balance - ammount);
                getCurrentAccount().addWithdrawStatement(ammount);
                return true;
            } else {
                return false;   // not enough balance
            }

        } else if (accountType.equals("savings") && hasSavingsAccount()) {

            int balance = getSavingsAccount().getBalance();
            if (balance >= ammount && ammount <= 200) {
                getSavingsAccount().setBalance(balance - ammount);
                getSavingsAccount().addWithdrawStatement(ammount);
                return true;
            } else {
                return false; // not enough balance or ammount needed more than 200
            }

        }
        return false;   // such account does not exist
    }

    public boolean deposit(@NotNull String accountType, int ammount) {

        if (accountType.equals("current") && hasCurrentAccount()) {

            getCurrentAccount().setBalance(getCurrentAccount().getBalance() + ammount);
            getCurrentAccount().addDepositStatement(ammount);
            return true;
        }
        else if (accountType.equals("savings") && hasSavingsAccount()) {

            getSavingsAccount().setBalance(getSavingsAccount().getBalance() + ammount);
            getSavingsAccount().addDepositStatement(ammount);
            return true;
        }

        return false;
    }

    //statement generator for each account type
    public void generateSavingsAccountStatements () {
        if (hasSavingsAccount()) {
            getSavingsAccount().generateStatements();
        } else {
            System.out.println("You do not have a savings account yet!");
        }
    }

    public void generateCurrentAccountStatements () {
        if (hasCurrentAccount()) {
            getCurrentAccount().generateStatements();
        } else {
            System.out.println("You do not have a current account yet!");
        }
    }


}