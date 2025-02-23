import java.util.Random;

public class Simulation {
    private GasStation gasStation;
    private int currentTime;
    private Random random;
    private static final int MINUTES_PER_DAY = 1440;
    private static final int REFILL_INTERVAL = 1440; // Refill once a day
    private static final double REFILL_AMOUNT = 5000.0; // Liters
    private int totalCarsGenerated;

    public Simulation(int initialDispenserCount) {
        gasStation = new GasStation(initialDispenserCount);
        currentTime = 0;
        random = new Random();
        totalCarsGenerated = 0;
    }

    public void run(int days) {
        for (int day = 0; day < days; day++) {
            for (int minute = 0; minute < MINUTES_PER_DAY; minute++) {
                currentTime++;

                if (shouldAddCar(minute)) {
                    Car car = generateRandomCar();
                    gasStation.addCar(car);
                    totalCarsGenerated++;
                }

                gasStation.update(1);

                if (currentTime % REFILL_INTERVAL == 0) {
                    gasStation.refillDispensers(REFILL_AMOUNT);
                }

                if (gasStation.getAverageWaitTime() > 12 && currentTime % (2 * MINUTES_PER_DAY) == 0) {
                    gasStation.addDispenser();
                }
            }
        }
    }

    private boolean shouldAddCar(int minute) {
        // Adjust probabilities based on time of day
        double probability;
        if (minute < 360 || minute >= 1320) { // 12 AM to 6 AM, 10 PM to 12 AM
            probability = 0.05;
        } else if (minute < 540 || minute >= 1140) { // 6 AM to 9 AM, 7 PM to 10 PM
            probability = 0.3;
        } else { // 9 AM to 7 PM
            probability = 0.2;
        }
        return random.nextDouble() < probability;
    }

    private Car generateRandomCar() {
        double tankCapacity = 40 + random.nextDouble() * 60; // 40 to 100 liters
        double currentFuel = random.nextDouble() * tankCapacity * 0.5; // Up to 50% of tank capacity
        double fuelNeeded = random.nextDouble() * (tankCapacity - currentFuel);
        return new Car(tankCapacity, currentFuel, fuelNeeded);
    }

    public void printResults() {
        System.out.println("Simulation completed.");
        System.out.println("Total days simulated: " + (currentTime / MINUTES_PER_DAY));
        System.out.println("Total cars generated: " + totalCarsGenerated);
        System.out.println("Total cars served: " + gasStation.getTotalCarsServed());
        System.out.println("Average wait time: " + String.format("%.2f", gasStation.getAverageWaitTime()) + " minutes");
        System.out.println("Cars waiting: " + gasStation.getWaitingCarsCount());
        System.out.println("Number of dispensers: " + gasStation.getDispenserCount());
    }
}