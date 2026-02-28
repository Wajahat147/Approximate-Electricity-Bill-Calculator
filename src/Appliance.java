public abstract class Appliance {
    protected String name;
    protected String icon;
    protected double wattage;
    protected double hoursUsed;

    public Appliance(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public void setWattage(double wattage) { this.wattage = wattage; }
    public void setHoursUsed(double hoursUsed) { this.hoursUsed = hoursUsed; }

    public String getName() { return name; }
    public String getIcon() { return icon; }
    public double getWattage() { return wattage; }
    public double getHoursUsed() { return hoursUsed; }

    public double calculateKWH() {
        return (wattage * hoursUsed * 30) / 1000.0;
    }

    public boolean validateHours() {
        return hoursUsed >= 1 && hoursUsed <= 24;
    }

    public boolean validateWattage() {
        return wattage > 0 && wattage <= 50000;
    }

    public abstract String getExtraInfo(double bill, double kwh);
}