public class ElectricKettle extends Appliance {
    public ElectricKettle() { super("Electric Kettle", "\u2615"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        // Typically runs ~5-10 min per use
        double costPerUse = (bill / 30.0) / (getHoursUsed() * 6); // ~6 uses/hr approx
        return String.format("Daily cost: PKR %.2f | Per boil (~10 min): PKR %.2f",
                bill / 30.0, costPerUse);
    }
}
