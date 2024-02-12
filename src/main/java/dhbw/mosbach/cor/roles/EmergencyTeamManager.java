package dhbw.mosbach.cor.roles;

import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.visitor.IPart;

import java.util.Random;

public class EmergencyTeamManager extends TeamMember {
    TechnicalEngineer[] technicalEngineers;
    Random random = new Random();
    public EmergencyTeamManager (TechnicalEngineer[] technicalEngineers){
        this.technicalEngineers = technicalEngineers;
    }
    @Override
    public void repairDefect(Defect defect, IPart part) {
        if (defect == Defect.E03) {
            repair(defect, part);
        } else if(successor != null){
            successor.repairDefect(defect,part);
        }
    }
    @Override
    public String getPassword() {
        return parent.getPassword();
    }
    private void repair(Defect defect, IPart part) {
        int engineerIndex = random.nextInt(technicalEngineers.length);
        technicalEngineers[engineerIndex].repairDefect(defect,part);
    }
}
