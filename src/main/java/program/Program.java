package program;

import service.ParkingService;

public class Program {
    public static void main(String[] args) {
        ParkingService parkingService = new ParkingService( 10,300,1,4);
        parkingService.start();
    }
}
