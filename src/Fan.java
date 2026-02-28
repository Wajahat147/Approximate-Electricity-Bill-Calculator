public class Fan extends Appliance {
    public Fan() { super("Ceiling Fan", "\uD83D\uDCA8"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f", bill / 30.0);
    }
}