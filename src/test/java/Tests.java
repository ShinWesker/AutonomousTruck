import com.google.common.hash.Hashing;
import dhbw.mosbach.bridge.BatteryControl;
import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.command.BrakeLightOn;
import dhbw.mosbach.command.TurnSignalOn;
import dhbw.mosbach.composite.Battery;
import dhbw.mosbach.key.ElectronicKey;
import dhbw.mosbach.key.ReceiverModule;
import dhbw.mosbach.mediator.ITruckMediator;
import dhbw.mosbach.mediator.TruckMediator;
import dhbw.mosbach.state.Active;
import dhbw.mosbach.state.Inactive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Tests {
    private AutonomousTruck autonomousTruck;
    private Trailer trailer;
    private TestUtil testUtil;

    @BeforeEach
    void setup(){
        testUtil = new TestUtil();
    }
    @Test
    @Order(1)
    @DisplayName("truck is complete")
    void testTruckConfiguration() {
        autonomousTruck = testUtil.createTruck();

        assertNotNull(autonomousTruck);

        assertNotNull(autonomousTruck.getTruckChassis().getEngine());
        assertEquals(2, autonomousTruck.getTruckChassis().getAxles().length);
        assertNotNull(autonomousTruck.getTruckChassis().getSteeringAxle());
        assertNotNull(autonomousTruck.getTruckChassis().getCoupling());

        assertEquals(4, autonomousTruck.getTruckChassis().getTurnSignals().length);

        int leftTurnSignals = 0;
        int rightTurnSignals = 0;
        for (TurnSignal turnSignal : autonomousTruck.getTruckChassis().getTurnSignals()) {
            assertNotNull(turnSignal);
            if (turnSignal.getPosition() == Position.LEFT) {
                leftTurnSignals++;
            } else if (turnSignal.getPosition() == Position.RIGHT) {
                rightTurnSignals++;
            }
        }
        assertEquals(2, leftTurnSignals);
        assertEquals(2, rightTurnSignals);

        assertEquals(2, autonomousTruck.getTruckChassis().getBrakeLights().length);

        assertNotNull(autonomousTruck.getTruckChassis().getCabine());
        assertEquals(2, autonomousTruck.getTruckChassis().getCabine().getExteriorMirrors().length);
        boolean foundLeftMirror = false;
        boolean foundRightMirror = false;
        for (ExteriorMirror mirror : autonomousTruck.getTruckChassis().getCabine().getExteriorMirrors()) {
            assertNotNull(mirror);
            if (mirror.getPosition() == Position.LEFT) {
                foundLeftMirror = true;
                assertNotNull(mirror.getCamera());
                assertNotNull(mirror.getLidar());
            } else if (mirror.getPosition() == Position.RIGHT) {
                foundRightMirror = true;
                assertNotNull(mirror.getCamera());
                assertNotNull(mirror.getLidar());
            }
        }
        assertTrue(foundLeftMirror);
        assertTrue(foundRightMirror);
    }

    @Test
    @Order(2)
    @DisplayName("trailer is complete")
    void testTrailerConfiguration(){
        trailer = testUtil.createTrailer();

        assertNotNull(trailer);
        assertNotNull(trailer.getTrailerChassis());
        assertTrue(trailer.getTrailerChassis().checkParts());
    }

    @Test
    @Order(3)
    @DisplayName("activate truck")
    void testActivateTruck(){
        autonomousTruck = testUtil.createTruck();

        autonomousTruck.activate();

        for (ExteriorMirror mirror : autonomousTruck.getTruckChassis().getCabine().getExteriorMirrors()) {
            assertTrue(mirror.getCamera().getStatus());
            assertTrue(mirror.getLidar().getStatus());
        }
        assertTrue(autonomousTruck.getTruckChassis().getEngine().getIsOn());
        assertEquals(0, autonomousTruck.getTruckChassis().getSteeringAxle().getDegree());
    }

    @Test
    @Order(4)
    @DisplayName("deactivate truck")
    void testDeactivateTruck(){
        autonomousTruck = testUtil.createTruck();

        autonomousTruck.deactivate();

        for (ExteriorMirror mirror : autonomousTruck.getTruckChassis().getCabine().getExteriorMirrors()) {
            assertFalse(mirror.getCamera().getStatus());
            assertFalse(mirror.getLidar().getStatus());
        }

        assertFalse(autonomousTruck.getTruckChassis().getEngine().getIsOn());
        assertEquals(0, autonomousTruck.getTruckChassis().getSteeringAxle().getDegree());
    }

    @Test
    @Order(5)
    @DisplayName("Observer detection")
    void testCentralUnitObservation(){
        CentralUnit centralUnit = spy(new CentralUnit());
        TruckMediator mediator = new TruckMediator();
        TruckBuilder builder = new TruckBuilder(centralUnit, mediator);
        TruckDirector truckDirector = new TruckDirector();
        autonomousTruck =  truckDirector.build(builder);
        trailer = testUtil.createTrailer();

        autonomousTruck.connect(trailer);

        centralUnit.loadLoadingPlan("src/main/resources/loadingPlan.json");

        testUtil.loadAllItemsIntoTrailer(trailer);

        verify(centralUnit, times(1)).detect();
        verify(centralUnit, times(16)).detect(Mockito.any(HoldingArea.class));

        assertTrue(centralUnit.getLoadingPlanStatus());
    }

    @Test
    @Order(6)
    @DisplayName("Commando behavior")
    void testCommandoBehavior(){
        CentralUnit centralUnit = new CentralUnit();
        TruckMediator mediator = new TruckMediator();
        mediator.setTruck(testUtil.createTruck());

        BrakeLight brakeLight = new BrakeLight(mediator,Position.LEFT);
        mediator.addBrakeLight(brakeLight);
        centralUnit.setCommand(new BrakeLightOn(brakeLight));
        centralUnit.execute();

        assertTrue(brakeLight.getStatus());

        TurnSignal turnSignalLeft = new TurnSignal(mediator,Position.LEFT, HorizontalPosition.FRONT);
        TurnSignal turnSignalRight = new TurnSignal(mediator,Position.RIGHT, HorizontalPosition.FRONT);
        mediator.addTurnSignal(turnSignalLeft);
        mediator.addTurnSignal(turnSignalRight);
        TurnSignal[] turnSignals = new TurnSignal[]{turnSignalLeft,turnSignalRight};

        centralUnit.setCommand(new TurnSignalOn(turnSignals,Position.LEFT));
        centralUnit.execute();

        assertTrue(turnSignalLeft.getStatus());

        centralUnit.setCommand(new TurnSignalOn(turnSignals,Position.RIGHT));
        centralUnit.execute();

        assertTrue(turnSignalRight.getStatus());

        // more to go ....
    }

    @Test
    @Order(7)
    @DisplayName("Key password and activation/deactivation tests")
    void keyTests(){
        autonomousTruck = testUtil.createTruck();
        ReceiverModule receiverModule = new ReceiverModule(autonomousTruck.getCentralUnit());
        ElectronicKey electronicKey = new ElectronicKey("Kodiak2024", receiverModule);

        assertEquals(
                Hashing.sha256().hashString("Kodiak2024", StandardCharsets.UTF_8).toString(),
                electronicKey.getPassword());

        //correct key
        electronicKey.sendSignal();
        assertInstanceOf(Active.class, autonomousTruck.getState());

        electronicKey.sendSignal();
        assertInstanceOf(Inactive.class, autonomousTruck.getState());

        //incorrect while inactive
        ElectronicKey key2 = new ElectronicKey("Clown45", receiverModule);
        key2.sendSignal();
        assertInstanceOf(Inactive.class, autonomousTruck.getState());

        //incorrect signal while active
        electronicKey.sendSignal();
        key2.sendSignal();
        assertInstanceOf(Active.class, autonomousTruck.getState());
    }


    @Test
    @Order(8)
    @DisplayName("Truck driving straight")
    void truckStraightDriving(){
        autonomousTruck = testUtil.createTruck();
        trailer = testUtil.createTrailer();

        autonomousTruck.connect(trailer);
        autonomousTruck.moveStraight(75);

        //check all signals to be off
        for (TurnSignal t : autonomousTruck.getTruckChassis().getTurnSignals()){
            assertFalse(t.getStatus());
        }
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            assertFalse(t.getStatus());
        }

        assertEquals(Position.STRAIGHT, autonomousTruck.getTruckChassis().getSteeringAxle().getPosition());
        assertEquals(0, autonomousTruck.getTruckChassis().getSteeringAxle().getDegree());
        assertEquals(75 ,autonomousTruck.getTruckChassis().getEngine().getSpeed());
    }

    @Test
    @Order(9)
    @DisplayName("Truck driving left")
    void truckLeftDriving(){
        autonomousTruck = testUtil.createTruck();
        trailer = testUtil.createTrailer();

        autonomousTruck.connect(trailer);
        autonomousTruck.moveStraight(75);
        autonomousTruck.turnLeft(15,50);

        for (TurnSignal t : autonomousTruck.getTruckChassis().getTurnSignals()){
            if (t.getPosition() == Position.LEFT) {
                assertTrue(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            if (t.getPosition() == Position.LEFT) {
                assertTrue(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }

        assertEquals(Position.LEFT, autonomousTruck.getTruckChassis().getSteeringAxle().getPosition());
        assertEquals(15, autonomousTruck.getTruckChassis().getSteeringAxle().getDegree());

        for (Axle axle : autonomousTruck.getTruckChassis().getAxles()){
            for (Brake b : axle.getBrakes()){
                assertEquals(25,b.getPercentage());
            }
        }

        for (Axle axle : trailer.getTrailerChassis().getAxles()){
            for (Brake b : axle.getBrakes()){
                assertEquals(25,b.getPercentage());
            }
        }

        assertEquals(50 ,autonomousTruck.getTruckChassis().getEngine().getSpeed());
    }

    @Test
    @Order(10)
    @DisplayName("Truck driving right")
    void truckRightDriving(){
        autonomousTruck = testUtil.createTruck();
        trailer = testUtil.createTrailer();

        autonomousTruck.connect(trailer);
        autonomousTruck.moveStraight(75);
        autonomousTruck.turnRight(15,50);

        for (TurnSignal t : autonomousTruck.getTruckChassis().getTurnSignals()){
            if (t.getPosition() == Position.RIGHT) {
                assertTrue(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            if (t.getPosition() == Position.RIGHT) {
                assertTrue(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }

        assertEquals(Position.RIGHT, autonomousTruck.getTruckChassis().getSteeringAxle().getPosition());
        assertEquals(15, autonomousTruck.getTruckChassis().getSteeringAxle().getDegree());

        for (Axle axle : autonomousTruck.getTruckChassis().getAxles()){
            for (Brake b : axle.getBrakes()){
                assertEquals(25,b.getPercentage());
            }
        }

        for (Axle axle : trailer.getTrailerChassis().getAxles()){
            for (Brake b : axle.getBrakes()){
                assertEquals(25,b.getPercentage());
            }
        }

        assertEquals(50 ,autonomousTruck.getTruckChassis().getEngine().getSpeed());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 20",
            "20, 40",
            "30, 60",
            "40, 80",
            "50, 100",
            "60, 120",
            "70, 140",
            "80, 160",
            "90, 180",
            "100, 200"
    })
    @Order(11)
    @DisplayName("Energy usage test with different speeds")
    void energyUsageTest(int speed, int expectedChargeRate) {
        Battery battery = new Battery();
        TruckBatteryControl batteryControl = new TruckBatteryControl(battery);
        Engine engine = new Engine(batteryControl);
        int fullChargeRate = battery.getChargeRate();

        engine.move(speed);

        assertEquals(expectedChargeRate, fullChargeRate - battery.getChargeRate());
    }

}
