public class Iron extends Appliance {
    public Iron() { super("Iron", "\uD83D\uDDBD"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Cost per session (~1hr): PKR %.2f", bill / (30.0 * hoursUsed));
    }
}