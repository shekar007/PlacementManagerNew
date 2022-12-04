package MultiThreading;

import java.sql.SQLOutput;

public class WelcomeThread extends Thread{
    @Override
    public void run() {
        System.out.println("Welcome to our Placement Manager Project:");
        System.out.print("Redirecting you to our homepage");
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print(".");
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print(".");
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print(".");
        System.out.println();
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Have a nice journey- meet you on the other side :)");

        try {
            sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }


}
