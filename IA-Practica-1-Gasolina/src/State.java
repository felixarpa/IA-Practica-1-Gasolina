import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;

public class State {
    private ArrayList<Truck> trucks;

    public State() {
        this.trucks = new ArrayList<>();
    }

    public static State initialState() {
        return null;
    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        for(Truck truck : trucks) {
            totalProfit += truck.getTotalProfit();
        }
        return totalProfit;
    }
}
