package dhbw.mosbach.builder.components.chassis;

import dhbw.mosbach.builder.components.HoldingArea;
import dhbw.mosbach.builder.components.Pallet;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.components.CargoSpace;
import dhbw.mosbach.builder.components.Pivot;
import dhbw.mosbach.builder.truck.AutonomousTruck;

public class TrailerChassis extends AChassis {
    private final Pivot pivot;
    private final CargoSpace cargoSpace;

    private TrailerChassis(TrailerChassisBuilder trailerChassisBuilder) {
        super(
                trailerChassisBuilder.brakeLights,
                trailerChassisBuilder.axles,
                trailerChassisBuilder.turnSignals

        );

        this.pivot = new Pivot(false);
        this.cargoSpace = new CargoSpace();
    }

    public void connect(AutonomousTruck autonomousTruck) {
        pivot.setTruck(autonomousTruck);

        for (HoldingArea holdingArea : cargoSpace.getArea())
            holdingArea.getSensor().addListener(autonomousTruck.getCentralUnit());
    }

    public void load(Pallet pallet, int index) {
        cargoSpace.load(pallet,index);
    }


    public static class TrailerChassisBuilder {
        private BrakeLight[] brakeLights;
        private Axle[] axles;
        private TurnSignal[] turnSignals;

        public TrailerChassisBuilder setBrakeLights(BrakeLight[] brakeLights) {
            this.brakeLights = brakeLights;
            return this;
        }

        public TrailerChassisBuilder setAxles(Axle[] axles) {
            this.axles = axles;
            return this;
        }

        public TrailerChassisBuilder setTurnSignals(TurnSignal[] turnSignals) {
            this.turnSignals = turnSignals;
            return this;
        }


        public TrailerChassis createTrailerChassis() {
            return new TrailerChassis(this);
        }
    }
}
