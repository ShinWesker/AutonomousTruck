package dhbw.mosbach.builder.components.chassis;

import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.components.CargoSpace;
import dhbw.mosbach.builder.components.Pivot;

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


    public static class TrailerChassisBuilder {
        private BrakeLight[] brakeLights;
        private Axle[] axles;
        private TurnSignal[] turnSignals;

        public TrailerChassisBuilder TrailerChassisBuilder(){
            return this;
        }

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
