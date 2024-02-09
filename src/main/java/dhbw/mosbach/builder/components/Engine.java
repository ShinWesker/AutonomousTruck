package dhbw.mosbach.builder.components;

import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.visitor.DefectUtils;
import dhbw.mosbach.visitor.IControlVisitor;
import dhbw.mosbach.visitor.IPartVisitor;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Engine implements IPart {
    private Boolean isOn;
    private final TruckBatteryControl control;
    @Setter
    private Defect defect;
    private int speed;

    public Engine(TruckBatteryControl control){
        this.control = control;
    }

    public void activate(){
        if (DefectUtils.checkDefect(this)) {
            DefectUtils.printDefect(this);
            return;
        }
        isOn = true;
    }
    public void deactivate(){
        if (DefectUtils.checkDefect(this)) {
            DefectUtils.printDefect(this);
            return;
        }
        isOn = false;
    }

    public void move(int amount){
        if (DefectUtils.checkDefect(this)) {
            DefectUtils.printDefect(this);
            return;
        }

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
    public void acceptPartVisitor(IPartVisitor iPartVisitor) {
        iPartVisitor.repair(this);
    }

    public void repair() {
        defect = null;
        System.out.println("Repairing in object: " + this.getClass().getSimpleName());
    }

    @Override
    public void acceptControl(IControlVisitor control) {
        control.detect(this);
    }
}
