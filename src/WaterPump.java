public class WaterPump extends Appliance {
    public WaterPump() { super("Water Pump", "\uD83D\uDCA7"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Yearly: PKR %.2f",
                bill / 30.0, bill * 12.0);
    }
}
