package program;

import service.ParkingService;

public class Program {
    public static void main(String[] args) {
        ParkingService parkingService = new ParkingService( 20,15,1,12);
        parkingService.start();
    }
}
