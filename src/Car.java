public class Car {
    private double tankCapacity;
    private double currentFuel;
    private double fuelNeeded;

    public Car(double tankCapacity, double currentFuel, double fuelNeeded) {
        this.tankCapacity = tankCapacity;
        this.currentFuel = currentFuel;
        this.fuelNeeded = Math.min(fuelNeeded, tankCapacity - currentFuel);
    }

    public double getFuelNeeded() {
        return fuelNeeded;
    }

    public void addFuel(double amount) {
        currentFuel += amount;
        fuelNeeded -= amount;
        if (fuelNeeded < 0) fuelNeeded = 0;
    }

    // Add getters and setters as needed
}