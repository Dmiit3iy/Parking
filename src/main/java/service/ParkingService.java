package service;

import model.Transport;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ParkingService {

    private volatile List<Transport> transportList = new CopyOnWriteArrayList<>();
    private volatile ConcurrentLinkedQueue<Transport> queue = new ConcurrentLinkedQueue<>();
    private int parkingLength;
    private int queueLength;
    private int inputGener;
    private int outputGener;
    private boolean carmageddon;

    public ParkingService(int size, int queueLength, int inputGener, int outputGener) {
        this.parkingLength = size;
        this.queueLength = queueLength;
        this.inputGener = inputGener;
        this.outputGener = outputGener;
        carmageddon = false;
    }

    public synchronized void start() {

        Thread thread1 = new Thread(() -> {
            try {
                while (!carmageddon) {
                    loadQueu();
                    addToParking();
                }
            } catch (InterruptedException e) {
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                while (!carmageddon) {
                    putAwayTransport();
                    addToParking();
                }
            } catch (InterruptedException e) {
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                while (!carmageddon) {
                    serviceInfo();
                }
            } catch (InterruptedException e) {
            }
        });


        thread1.start();
        thread2.start();
        thread4.start();

    }

    /**
     * Метод случайной генерации легкового или грузового автомобиля
     *
     * @param
     * @return
     */
    public Transport generTransport() {
        int x = (int) (Math.random() * 10);
        if (x < 3) {
            return new Transport(2);
        } else return new Transport(1);
    }

    /**
     * Метод добавления транспорта в очередь
     *
     * @param
     * @throws InterruptedException
     */
    public void loadQueu() throws InterruptedException {
        long x = (long) (Math.random() * inputGener * 1000);
        Thread.sleep(x);
        Transport transport = generTransport();
        int countPlaceQueu = (int) queue.stream().map(c->c.getCountPlace()).mapToInt(Integer::valueOf).sum()+transport.getCountPlace();
        if (countPlaceQueu<= queueLength) {
            System.out.println("«Легковой/грузовой автомобиль с id = " + transport.getId() + "встал в очередь на въезд.»");
            queue.add(transport);
        } else {
            carmageddon = true;
            System.out.println("Все плохо плох плохо!!! Парковка больше не работает");

        }
    }


    /**
     * Метод удаления транспорта со стоянки
     *
     * @param
     * @throws InterruptedException
     */
    public void putAwayTransport() throws InterruptedException {

        long x = (long) (Math.random() * outputGener * 1000);
        Thread.sleep(x);
        if (carmageddon) throw new InterruptedException();
        if (transportList.size() > 0) {
            int z = new Random().nextInt(transportList.size());
            Transport transport2 = transportList.get(z);
            transportList.remove(transport2);
            System.out.println("Легковой/грузовой автомобиль с id = " + transport2.getId() + " покинул парковку.");
        }
    }


    public synchronized void addToParking() {
        if (queue.peek()!=null){
        int transportSize = queue.peek().getCountPlace();
        int countReal = parkingLength - (int) transportList.stream().map(x -> x.getCountPlace()).mapToInt(Integer::valueOf).sum();
        if (countReal >= transportSize){
                Transport transport = queue.poll();
                transportList.add(transport);
                System.out.println("Легковой/грузовой автомобиль с id = " + transport.getId() + "припарковался.");
            }
        }
    }


    public void serviceInfo() throws InterruptedException {

        Thread.sleep(5000);
        //int freePlace = parkingLength - transportList.size();
        int freePlace = parkingLength - ((int) transportList.stream().map(x -> x.getCountPlace()).mapToInt(Integer::valueOf).sum());
        int car = (int) transportList.stream().filter(x -> x.getCountPlace() == 1).collect(Collectors.toList()).stream().count();
        int cargo = (int) transportList.stream().filter(x -> x.getCountPlace() == 2).collect(Collectors.toList()).stream().count();
        System.out.printf("Свободных мест: %2$d \n Занято мест: %1$d (легковых авто: %3$d и грузовых авто: %4$d)\n" +
                "Автомобилей, ожидающих в очереди: %5$d (длина очереди %6$d) \n", (int) transportList.stream().map(x -> x.getCountPlace()).mapToInt(Integer::valueOf).sum(), freePlace, car, cargo, queue.size(),queue.stream().map(x -> x.getCountPlace()).mapToInt(Integer::valueOf).sum());

    }
}
