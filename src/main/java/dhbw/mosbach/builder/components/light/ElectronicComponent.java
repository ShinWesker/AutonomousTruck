package dhbw.mosbach.builder.components.light;

import dhbw.mosbach.enums.Position;
import dhbw.mosbach.enums.HorizontalPosition;
import dhbw.mosbach.mediator.ITruckMediator;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract non-sealed class ElectronicComponent implements IElectronicComponent {
    @Setter
    protected Boolean status;
    protected Position position;
    protected HorizontalPosition horizontalPosition;
    protected ITruckMediator mediator;

    protected ElectronicComponent(ITruckMediator mediator ,Position position, HorizontalPosition horizontalPosition){
        this.position = position;
        this.horizontalPosition = horizontalPosition;
        this.mediator = mediator;
        this.status = false;

    }
    @Override
    public String toString() {
        return String.format(
                """
                ObjectType: %s
                Status: %s
                HashCode: %d
                """,
                this.getClass().getSimpleName(),
                Boolean.TRUE.equals(status) ? "On" : "Off",
                this.hashCode());
    }
}
