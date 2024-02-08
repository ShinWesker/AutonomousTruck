import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.ExteriorMirror;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.mediator.TruckMediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

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
    @DisplayName("Test Observer")
    void testCentralUnitObservation(){
        CentralUnit centralUnit = spy(new CentralUnit());

        TruckMediator mediator = new TruckMediator();
        TruckBuilder builder = new TruckBuilder(centralUnit, mediator);
        TruckDirector truckDirector = new TruckDirector();
        autonomousTruck =  truckDirector.build(builder);
        trailer = testUtil.createTrailer();

        autonomousTruck.connect(trailer);

        //TODO trailer.load();
        verify(centralUnit, times(1)).detect();
    }


}
