package dhbw.mosbach.builder.components.chassis;

import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.axle.SteeringAxle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.components.Cabine;
import dhbw.mosbach.builder.components.Coupling;
import dhbw.mosbach.builder.components.Engine;
import lombok.Getter;

@Getter
public class TruckChassis extends AChassis {
    private final Cabine cabine;
    private final Engine engine;
    private final Coupling coupling;
    private final SteeringAxle steeringAxle;

    private TruckChassis(TruckChassisBuilder truckChassisBuilder) {
        super(
                truckChassisBuilder.brakeLights,
                truckChassisBuilder.axles,
                truckChassisBuilder.turnSignals

        );

        this.cabine = truckChassisBuilder.cabine;
        this.engine = truckChassisBuilder.engine;
        this.coupling = truckChassisBuilder.coupling;
        this.steeringAxle = truckChassisBuilder.steeringAxle;
    }

    public static class TruckChassisBuilder {
        private BrakeLight[] brakeLights;
        private Axle[] axles;
        private TurnSignal[] turnSignals;
        private Cabine cabine;
        private Engine engine;
        private Coupling coupling;
        private SteeringAxle steeringAxle;

        public TruckChassisBuilder setBrakeLights(BrakeLight[] brakeLights) {
            this.brakeLights = brakeLights;
            return this;
        }

        public TruckChassisBuilder setAxles(Axle[] axles) {
            this.axles = axles;
            return this;
        }

        public TruckChassisBuilder setTurnSignals(TurnSignal[] turnSignals) {
            this.turnSignals = turnSignals;
            return this;
        }


        public TruckChassisBuilder setCabine(Cabine cabine) {
            this.cabine = cabine;
            return this;
        }

        public TruckChassisBuilder setEngine(Engine engine) {
            this.engine = engine;
            return this;
        }

        public TruckChassisBuilder setCoupling(Coupling coupling) {
            this.coupling = coupling;
            return this;
        }

        public TruckChassisBuilder setSteeringAxle(SteeringAxle steeringAxle) {
            this.steeringAxle = steeringAxle;
            return this;
        }

        public TruckChassis createTruckChassis() {
            return new TruckChassis(this);
        }
    }
}
