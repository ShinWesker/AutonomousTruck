package dhbw.mosbach.bridge;

import dhbw.mosbach.composite.Battery;

public abstract class BatteryControl {
    private final IBattery battery;

    public BatteryControl(Battery battery){
        this.battery = battery;
    }
    public void takeEnergy(int amount){
        battery.discharge(amount);
    }

    public void fillEnergy(){
        battery.charge();
    }
}
