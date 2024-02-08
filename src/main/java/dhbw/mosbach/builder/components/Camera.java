package dhbw.mosbach.builder.components;

import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.visitor.IControl;
import dhbw.mosbach.visitor.IPartVisitor;
import dhbw.mosbach.visitor.IPart;

public class Camera implements IPart {

    private Boolean isOn;
    private Defect defect;
    public void activate(){
        isOn = true;
    }
    public void deactivate(){
        isOn = false;
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
                isOn ? "On" : "Off",
                this.hashCode());
    }

    @Override
    public void accept(IPartVisitor iPartVisitor) {
        iPartVisitor.repair(this);
    }

    @Override
    public void repair() {
        defect = null;
        System.out.println("Repairing in object: " + this.getClass().getSimpleName());

    }

    @Override
    public Defect control(IControl control) {
        return defect;
    }
}
