package MultiThreading;

import Station.Station;
import Student.Student;

import java.io.IOException;

public class MultiThreadStudent extends Thread{
    @Override
    public void run() {


        try {
            sleep(2000);
        } catch (InterruptedException e) {

        }
        try {
            Student.readStudent();
        } catch (IOException e) {

        }

    }
}
