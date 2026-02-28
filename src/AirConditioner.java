public class AirConditioner extends Appliance {
    public AirConditioner() { super("Air Conditioner", "\u2744\uFE0F"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Seasonal (6mo): PKR %.2f",
                bill / 30.0, bill * 6.0);
    }
}