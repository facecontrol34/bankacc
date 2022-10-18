import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Баланс равен: 1000");

        Account acc =  new Account();
        Add add = new Add(acc);
        Remove rem = new Remove(acc);
        new Thread(rem).start();
        new Thread(add).start();
    }
}
class Account extends Thread{
    int remove;

    public int balance = 1000;
    public synchronized void addMoney(){
        while(balance >= remove) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        while(balance < remove){
            balance += 100;
            System.out.println("Добавлено 100 рублей, баланс равен: " + balance);
        }
        notify();
    }
    public synchronized void removeMoney() {

        System.out.println("Введите сумму снятия");
        Scanner in = new Scanner(System.in);
        remove = in.nextInt();

        while(balance < remove){
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        balance -= remove;
        System.out.println("Снято " + remove + " рублей, баланс равен: " + balance);
        notify();
    }
}
class Add extends Thread{
    Account acc;

    Add(Account acc){
        this.acc = acc;
    }
    public void run(){
        boolean exit = true;
        while (exit)
            acc.addMoney();
    }
}
class Remove extends Thread{
    Account acc;

    Remove(Account acc){
        this.acc = acc;
    }
    public void run(){
        boolean exit = true;
        while (exit)
            acc.removeMoney();
    }
}