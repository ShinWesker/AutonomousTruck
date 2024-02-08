package dhbw.mosbach.cor.composite;

public class EmergencyTeamManager extends TeamManager{
    public EmergencyTeamManager(){
        super();
        for (int i = 0; i < 3; i++) {
            TechnicalEngineer tech = new TechnicalEngineer();
            tech.setParent(this);
            addUnit(tech);
        }
    }
}
