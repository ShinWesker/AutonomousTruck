package dhbw.mosbach.cor;


import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.visitor.IPart;

import java.util.ArrayList;
import java.util.List;

public class SensoryTeam extends Team {
    private final List<String> permissions;
    public SensoryTeam(Supervisor supervisor) {
        super(supervisor);
        permissions = new ArrayList<>();
        permissions.add("Camera");
        permissions.add("Lidar");
    }
    @Override
    public void repairPart(Defect defect, IPart part) {
        if (canHandlePart(part ,permissions)) {
            supervisor.repairDefect(defect ,part);
        } else{
            super.repairPart(defect,part);
        }
    }
}
