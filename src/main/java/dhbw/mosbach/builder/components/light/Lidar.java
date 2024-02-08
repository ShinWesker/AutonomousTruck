package dhbw.mosbach.builder.components.light;

import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.mediator.ITruckMediator;
import dhbw.mosbach.visitor.IControl;
import dhbw.mosbach.visitor.IPartVisitor;
import dhbw.mosbach.visitor.IPart;

public class Lidar extends ElectronicComponent implements IPart {
    private Defect defect;

    public Lidar(ITruckMediator mediator, Position position) {
        super(mediator,position, HorizontalPosition.FRONT);
    }

    @Override
    public void activate() {
        mediator.activate(this);
    }

    @Override
    public void deactivate() {
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
