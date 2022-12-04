package MultiThreading;

import Station.Station;

import java.io.IOException;

public class MultiThreadStation extends Thread{
    @Override
    public void run()
    {
        try {
            Station.readStation();

        } catch (IOException e) {

        }


    }

}
