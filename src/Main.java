public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(7);
        simulation.run(10);
        simulation.printResults();
    }
}