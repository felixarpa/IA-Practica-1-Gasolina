import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;

public class State {
    private ArrayList<Truck> trucks;
    private double totalBenefit;

    public State() {
        this.totalBenefit = 0.0;
        this.trucks = new ArrayList<>();
    }

    public static State initialState() {
        return null;
    }
}
