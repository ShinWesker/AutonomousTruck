package dhbw.mosbach.cor.roles;

import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.visitor.IPart;


public class OperationTeamManager extends TeamMember{
    TechnicalEngineer[] technicalEngineers;

    public OperationTeamManager(TechnicalEngineer[] technicalEngineers){
        this.technicalEngineers = technicalEngineers;
    }
    @Override
    public void repairDefect(Defect defect, IPart part) {
        if (defect == Defect.E01 || defect == Defect.E02) {
            repair(part);
        } else if(successor != null){
            successor.repairDefect(defect,part);
        }
    }

    @Override
    public String getPassword() {
        return parent.getPassword();
    }

    public void repair(IPart part){
        technicalEngineers[0].repair(part, getPassword());
    }
}
