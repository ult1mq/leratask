import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class GasStation {
    private List<Dispenser> dispensers;
    private Queue<Car> waitingCars;
    private int totalCarsServed;
    private int totalWaitTime;

    public GasStation(int initialDispenserCount) {
        dispensers = new ArrayList<>();
        for (int i = 0; i < initialDispenserCount; i++) {
            dispensers.add(new Dispenser(20)); // 20 liters per minute
        }
        waitingCars = new LinkedList<>();
        totalCarsServed = 0;
        totalWaitTime = 0;
    }

    public void addCar(Car car) {
        Dispenser availableDispenser = findAvailableDispenser();
        if (availableDispenser != null) {
            availableDispenser.addCar(car);
        } else {
            waitingCars.offer(car);
        }
    }

    public void update(int minutes) {
        for (Dispenser dispenser : dispensers) {
            if (dispenser.hasCar()) {
                Car car = dispenser.removeCar();
                dispenser.refillCar(car, minutes);
                if (car.getFuelNeeded() > 0) {
                    dispenser.addCar(car);
                } else {
                    totalCarsServed++;
                    if (!waitingCars.isEmpty()) {
                        dispenser.addCar(waitingCars.poll());
                    }
                }
            }
        }
        totalWaitTime += waitingCars.size() * minutes;
    }

    public void addDispenser() {
        dispensers.add(new Dispenser(20));
    }

    private Dispenser findAvailableDispenser() {
        for (Dispenser dispenser : dispensers) {
            if (!dispenser.isOccupied()) {
                return dispenser;
            }
        }
        return null;
    }

    public int getWaitingCarsCount() {
        return waitingCars.size();
    }

    public double getAverageWaitTime() {
        return totalCarsServed > 0 ? (double) totalWaitTime / totalCarsServed : 0;
    }

    public void refillDispensers(double amount) {
        for (Dispenser dispenser : dispensers) {
            dispenser.refillDispenser(amount);
        }
    }
    public int getTotalCarsServed() {
        return totalCarsServed;
    }

    public int getDispenserCount() {
        return dispensers.size();
    }
}