package dhbw.mosbach.cor;

import dhbw.mosbach.cor.composite.EmergencyTeamManager;
import dhbw.mosbach.cor.composite.OperationTeamManager;
import dhbw.mosbach.cor.composite.Supervisor;
import dhbw.mosbach.visitor.IPart;

public class ServiceCenter {
    private final SensoryTeam sensoryTeam;

    public ServiceCenter(){
        MotorTeam motorTeam = new MotorTeam(new Supervisor("QuatschPassword", new OperationTeamManager()));
        sensoryTeam = new SensoryTeam(new Supervisor("PasswordOperation", new EmergencyTeamManager()), motorTeam);
    }

    public void handleDefect(Defect defect, IPart part){
        sensoryTeam.repairDefect(defect, part);
    }
}
