package dhbw.mosbach.builder.components;

import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.visitor.IControl;
import dhbw.mosbach.visitor.IPartVisitor;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;

@Getter
public class Engine implements IPart {
    private Boolean isOn;
    private final TruckBatteryControl control;
    private Defect defect;
    private int speed;

    public Engine(TruckBatteryControl control){
        this.control = control;
    }

    public void activate(){
        isOn = true;
    }
    public void deactivate(){
        isOn = false;
    }

    public void move(int amount){
        speed = amount;
        control.takeEnergy(amount * 2);
    }

    @Override
    public String toString() {
        return String.format(
                """
                Type: %s
                Status: %s
                HashCode: %d
                """,
                this.getClass().getSimpleName(),
                Boolean.TRUE.equals(isOn) ? "On" : "Off",
                this.hashCode());
    }

    @Override
    public void accept(IPartVisitor iPartVisitor) {
        iPartVisitor.repair(this);
    }

    public void repair() {
        defect = null;
        System.out.println("Repairing in object: " + this.getClass().getSimpleName());
    }

    @Override
    public Defect control(IControl control) {
        return defect;
    }
}
