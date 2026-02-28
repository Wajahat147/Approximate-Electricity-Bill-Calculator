public class Geyser extends Appliance {
    public Geyser() { super("Geyser / Water Heater", "\uD83D\uDEBF"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Winter season (4mo): PKR %.2f",
                bill / 30.0, bill * 4.0);
    }
}
