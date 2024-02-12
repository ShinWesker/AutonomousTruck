import com.google.common.hash.Hashing;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.command.*;
import dhbw.mosbach.eventbus.events.*;
import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.chassis.TruckChassis;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.light.Lidar;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.composite.Battery;
import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.cor.MotorTeam;
import dhbw.mosbach.cor.SensoryTeam;
import dhbw.mosbach.cor.ServiceCenter;
import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.cor.roles.TechnicalEngineer;
import dhbw.mosbach.eventbus.ThreePoleConnector;
import dhbw.mosbach.key.ElectronicKey;
import dhbw.mosbach.key.ReceiverModule;
import dhbw.mosbach.mediator.TruckMediator;
import dhbw.mosbach.proxy.Robot;
import dhbw.mosbach.state.Active;
import dhbw.mosbach.state.Inactive;
import dhbw.mosbach.visitor.Examiner;
import dhbw.mosbach.visitor.IPart;
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

class FunctionalityTests {
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

        autonomousTruck = testUtil.createTruck();
        CentralUnit centralUnit = autonomousTruck.getCentralUnit();
        TruckChassis chassis = autonomousTruck.getTruckChassis();
        Position position = Position.LEFT;

        // BlinkerOn LEFT
        centralUnit.setCommand(new TurnSignalOn(chassis.getTurnSignals(),position));
        centralUnit.execute();

        for (TurnSignal t : chassis.getTurnSignals()){
            if (t.getPosition() == position) {
                assertTrue(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }

        // BlinkerOn Right
        position = Position.RIGHT;
        centralUnit.setCommand(new TurnSignalOn(chassis.getTurnSignals(),position));
        centralUnit.execute();
        for (TurnSignal t : chassis.getTurnSignals()){
            if (t.getPosition() == position) {
                assertTrue(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }

        // BlinkerOff LEFT
        position = Position.LEFT;
        centralUnit.setCommand(new TurnSignalOff(chassis.getTurnSignals(),position));
        centralUnit.execute();

        for (TurnSignal t : chassis.getTurnSignals()){
            if (t.getPosition() == position) {
                assertFalse(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }

        // BlinkerOff Right
        position = Position.RIGHT;
        centralUnit.setCommand(new TurnSignalOff(chassis.getTurnSignals(),position));
        centralUnit.execute();
        for (TurnSignal t : chassis.getTurnSignals()){
            if (t.getPosition() == position) {
                assertFalse(t.getStatus());
            } else{
                assertFalse(t.getStatus());
            }
        }

        //BrakeLightOn
        centralUnit.setCommand(new BrakeLightOn(chassis.getBrakeLights()[0]));
        centralUnit.execute();

        for (BrakeLight b : chassis.getBrakeLights()){
            assertTrue(b.getStatus());
        }

        //BrakeLightOff
        centralUnit.setCommand(new BrakeLightOff(chassis.getBrakeLights()[0]));
        centralUnit.execute();

        for (BrakeLight b : chassis.getBrakeLights()){
            assertFalse(b.getStatus());
        }

        // CameraOn
        centralUnit.setCommand(new CameraOn(chassis.getCabine().getExteriorMirrors()[0].getCamera()));
        centralUnit.execute();

        for (ExteriorMirror ex : chassis.getCabine().getExteriorMirrors()){
            assertTrue(ex.getCamera().getStatus());
        }

        // CameraOff
        centralUnit.setCommand(new CameraOff(chassis.getCabine().getExteriorMirrors()[0].getCamera()));
        centralUnit.execute();

        for (ExteriorMirror ex : chassis.getCabine().getExteriorMirrors()){
            assertFalse(ex.getCamera().getStatus());
        }

        // LidarOn
        centralUnit.setCommand(new LidarOn(chassis.getCabine().getExteriorMirrors()[0].getLidar()));
        centralUnit.execute();

        for (ExteriorMirror ex : chassis.getCabine().getExteriorMirrors()){
            assertTrue(ex.getLidar().getStatus());
        }

        // LidarOff
        centralUnit.setCommand(new LidarOff(chassis.getCabine().getExteriorMirrors()[0].getLidar()));
        centralUnit.execute();

        for (ExteriorMirror ex : chassis.getCabine().getExteriorMirrors()){
            assertFalse(ex.getLidar().getStatus());
        }

        // CBrake
        int percentage = 35;
        centralUnit.setCommand(new CBrake(chassis.getAxles(), percentage));
        centralUnit.execute();

        for (Axle a : chassis.getAxles()){
            for (Brake b : a.getBrakes()){
                assertEquals(percentage ,b.getPercentage());
            }
        }

        // EngineStart
        centralUnit.setCommand(new EngineStart(chassis.getEngine()));
        centralUnit.execute();

        assertTrue(chassis.getEngine().getIsOn());

        // EngineShutDown
        centralUnit.setCommand(new EngineShutDown(chassis.getEngine()));
        centralUnit.execute();

        assertFalse(chassis.getEngine().getIsOn());

        // Move Straight
        centralUnit.setCommand(new MoveStraight(chassis.getSteeringAxle(),75, chassis.getEngine()));
        centralUnit.execute();

        assertEquals(Position.STRAIGHT,  chassis.getSteeringAxle().getPosition());
        assertEquals(0, chassis.getSteeringAxle().getDegree());
        assertEquals(75,chassis.getEngine().getSpeed());

        // Turn Left
        centralUnit.setCommand(new TurnLeft(chassis.getEngine(),chassis.getSteeringAxle(),65, 35));
        centralUnit.execute();

        assertEquals(Position.LEFT,  chassis.getSteeringAxle().getPosition());
        assertEquals(35, chassis.getSteeringAxle().getDegree());
        assertEquals(65,chassis.getEngine().getSpeed());

        // Turn Right
        centralUnit.setCommand(new TurnRight(chassis.getEngine(),chassis.getSteeringAxle(),50, 15));
        centralUnit.execute();

        assertEquals(Position.RIGHT,  chassis.getSteeringAxle().getPosition());
        assertEquals(15, chassis.getSteeringAxle().getDegree());
        assertEquals(50,chassis.getEngine().getSpeed());


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

    @Test
    @Order(12)
    @DisplayName("Visitor test")
    void visitorTest() {
        Supervisor supervisor = testUtil.createSupervisor();
        MotorTeam motorTeam = new MotorTeam(supervisor);
        SensoryTeam sensoryTeam = new SensoryTeam(supervisor);
        sensoryTeam.setSuccessor(motorTeam);

        ServiceCenter serviceCenter = new ServiceCenter(sensoryTeam);
        Examiner examiner = spy(new Examiner(serviceCenter));

        autonomousTruck = testUtil.createTruck();

        Engine engine = spy(new Engine(null));
        Camera camera = spy(new Camera(null, null));
        Lidar lidar = spy(new Lidar(null, null));

        autonomousTruck.getTruckChassis().setEngine(engine);
        autonomousTruck.getTruckChassis().getCabine().getExteriorMirrors()[0].setCamera(camera);
        autonomousTruck.getTruckChassis().getCabine().getExteriorMirrors()[0].setLidar(lidar);

        engine.setDefect(Defect.E03);
        camera.setDefect(Defect.E02);
        lidar.setDefect(Defect.E01);

        autonomousTruck.examineParts(examiner);


        verify(engine, times(1)).acceptControl(any(Examiner.class));
        verify(camera, times(1)).acceptControl(any(Examiner.class));
        verify(lidar, times(1)).acceptControl(any(Examiner.class));

        verify(examiner, times(1)).detect(engine);
        verify(examiner, times(1)).detect(camera);
        verify(examiner, times(1)).detect(lidar);
        verify(examiner, times(3)).contactServiceTeam(any(IPart.class));

        verify(engine, times(1)).acceptPartVisitor(any(Robot.class));
        verify(camera, times(1)).acceptPartVisitor(any(Robot.class));
        verify(lidar, times(1)).acceptPartVisitor(any(Robot.class));

        verify(engine, times(1)).repair();
        verify(camera, times(1)).repair();
        verify(lidar, times(1)).repair();
    }

    @Test
    @Order(13)
    @DisplayName("CoR testing")
    void CoRTest(){
        TechnicalEngineer mockEmergencyEngineerMO = mock(TechnicalEngineer.class);
        TechnicalEngineer mockOperationEngineerMO = mock(TechnicalEngineer.class);
        TechnicalEngineer mockEmergencyEngineerSE = mock(TechnicalEngineer.class);
        TechnicalEngineer mockOperationEngineerSE = mock(TechnicalEngineer.class);

        MotorTeam motorTeam = new MotorTeam(testUtil.createTeamWithMockedEngineers(mockOperationEngineerMO,mockEmergencyEngineerMO));
        SensoryTeam sensoryTeam = new SensoryTeam(testUtil.createTeamWithMockedEngineers(mockOperationEngineerSE,mockEmergencyEngineerSE));
        sensoryTeam.setSuccessor(motorTeam);

        Engine engine = new Engine(null);
        Lidar lidar = new Lidar(null,null);
        Camera camera = new Camera(null,null);

        ServiceCenter serviceCenter = new ServiceCenter(sensoryTeam);

        serviceCenter.handleDefect(Defect.E01, engine);
        serviceCenter.handleDefect(Defect.E02, engine);
        serviceCenter.handleDefect(Defect.E03, engine);
        verify(mockOperationEngineerMO, times(2)).repair(Mockito.any(Engine.class),any());
        verify(mockEmergencyEngineerMO, times(1)).repair(Mockito.any(Engine.class),any());

        serviceCenter.handleDefect(Defect.E01, camera);
        serviceCenter.handleDefect(Defect.E02, camera);
        serviceCenter.handleDefect(Defect.E03, camera);
        verify(mockOperationEngineerSE, times(2)).repair(Mockito.any(Camera.class),any());
        verify(mockEmergencyEngineerSE, times(1)).repair(Mockito.any(Camera.class),any());

        serviceCenter.handleDefect(Defect.E01, lidar);
        serviceCenter.handleDefect(Defect.E02, lidar);
        serviceCenter.handleDefect(Defect.E03, lidar);
        verify(mockOperationEngineerSE, times(2)).repair(Mockito.any(Lidar.class),any());
        verify(mockEmergencyEngineerSE, times(1)).repair(Mockito.any(Lidar.class),any());
    }

    @Test
    @Order(14)
    @DisplayName("Eventbus test")
    void eventBusTest(){
        autonomousTruck = testUtil.createTruck();
        trailer = testUtil.createTrailer();

        autonomousTruck.connect(trailer);
        ThreePoleConnector connector = autonomousTruck.getThreePoleConnector();

        // EventTurnSignalOn LEFT
        connector.getTurnSignalBus().post(new EventTurnSignalOn(Position.LEFT));
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            if (t.getPosition() == Position.LEFT) {
                assertTrue(t.getStatus());
            }
        }

        // EventTurnSignalOff LEFT
        connector.getTurnSignalBus().post(new EventTurnSignalOff(Position.LEFT));
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            if (t.getPosition() == Position.LEFT) {
                assertFalse(t.getStatus());
            }
        }

        // EventTurnSignalOn RIGHT
        connector.getTurnSignalBus().post(new EventTurnSignalOn(Position.RIGHT));
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            if (t.getPosition() == Position.RIGHT) {
                assertTrue(t.getStatus());
            }
        }

        // EventTurnSignalOff RIGHT
        connector.getTurnSignalBus().post(new EventTurnSignalOff(Position.RIGHT));
        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            if (t.getPosition() == Position.RIGHT) {
                assertFalse(t.getStatus());
            }
        }

        // EventBrakeLightOn
        connector.getBrakeLightBus().post(new EventBrakeLightOn());
        for (BrakeLight b : trailer.getTrailerChassis().getBrakeLights()){
            assertTrue(b.getStatus());
        }
        // EventBrakeLightOff
        connector.getBrakeLightBus().post(new EventBrakeLightOff());
        for (BrakeLight b : trailer.getTrailerChassis().getBrakeLights()){
            assertFalse(b.getStatus());
        }

        // EventBrake with value 25
        connector.getBrakeBus().post(new EventBrake(25));
        for (Axle a : trailer.getTrailerChassis().getAxles()){
            for (Brake b : a.getBrakes()){
                assertEquals(25, b.getPercentage());
            }
        }
    }
}
