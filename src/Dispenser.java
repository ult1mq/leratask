import java.util.LinkedList;
import java.util.Queue;

public class Dispenser {
    private Queue<Car> cars;
    private int refillSpeed;
    private boolean isOccupied;
    private double fuelAvailable;
    private static final double MAX_FUEL_CAPACITY = 1000.0; // Maximum fuel capacity in liters

    public Dispenser(int refillSpeed) {
        this.cars = new LinkedList<>();
        this.refillSpeed = refillSpeed;
        this.isOccupied = false;
        this.fuelAvailable = MAX_FUEL_CAPACITY;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void addCar(Car car) {
        cars.offer(car);
        isOccupied = true;
    }

    public Car removeCar() {
        isOccupied = false;
        return cars.poll();
    }

    public boolean hasCar() {
        return !cars.isEmpty();
    }

    public void refillCar(Car car, double minutes) {
        double fuelToAdd = Math.min(car.getFuelNeeded(), refillSpeed * minutes);
        fuelToAdd = Math.min(fuelToAdd, fuelAvailable);

        car.addFuel(fuelToAdd);
        fuelAvailable -= fuelToAdd;
    }

    public void refillDispenser(double amount) {
        fuelAvailable = Math.min(fuelAvailable + amount, MAX_FUEL_CAPACITY);
    }

    public double getFuelAvailable() {
        return fuelAvailable;
    }

    public void setRefillSpeed(int refillSpeed) {
        this.refillSpeed = refillSpeed;
    }

    public int getRefillSpeed() {
        return refillSpeed;
    }
}
