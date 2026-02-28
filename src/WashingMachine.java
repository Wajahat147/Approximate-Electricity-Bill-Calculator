public class WashingMachine extends Appliance {
    public WashingMachine() { super("Washing Machine", "\uD83E\uDDF5"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Per cycle (~1hr): PKR %.2f",
                bill / 30.0, (bill / 30.0));
    }
}
