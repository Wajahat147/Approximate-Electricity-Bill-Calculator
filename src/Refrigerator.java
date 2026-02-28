public class Refrigerator extends Appliance {
    public Refrigerator() { super("Refrigerator", "\uD83C\uDF61"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Annual cost: PKR %.2f", bill * 12.0);
    }
}