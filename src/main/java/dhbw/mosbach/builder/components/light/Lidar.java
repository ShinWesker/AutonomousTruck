package dhbw.mosbach.builder.components.light;

import dhbw.mosbach.enums.HorizontalPosition;
import dhbw.mosbach.enums.Position;
import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.mediator.ITruckMediator;
import dhbw.mosbach.visitor.DefectUtils;
import dhbw.mosbach.visitor.IControlVisitor;
import dhbw.mosbach.visitor.IPartVisitor;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lidar extends ElectronicComponent implements IPart {
    private Defect defect;

    public Lidar(ITruckMediator mediator, Position position) {
        super(mediator,position, HorizontalPosition.FRONT);
    }

    @Override
    public void activate() {
        if (DefectUtils.checkDefect(this)) {
            DefectUtils.printDefect(this);
            return;
        }
        mediator.activate(this);
    }

    @Override
    public void deactivate() {
        if (DefectUtils.checkDefect(this)) {
            DefectUtils.printDefect(this);
            return;
        }
        mediator.deactivate(this);
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
                Boolean.TRUE.equals(status) ? "On" : "Off",
                this.hashCode());
    }

    @Override
    public void acceptPartVisitor(IPartVisitor iPartVisitor) {
        iPartVisitor.repair(this);
    }

    @Override
    public void repair() {
        defect = null;
        System.out.println("Repairing in object: " + this.getClass().getSimpleName());
    }

    @Override
    public void acceptControl(IControlVisitor control) {
        control.detect(this);
    }
}
