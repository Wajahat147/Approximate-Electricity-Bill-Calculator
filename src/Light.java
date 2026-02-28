public class Light extends Appliance {
    public Light() { super("Light / Bulb", "\uD83D\uDCA1"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Annual cost estimate: PKR %.2f", bill * 12.0);
    }
}
