package dhbw.mosbach.cor.composite;

public class OperationTeamManager extends TeamManager{
    public OperationTeamManager(){
        super();
        for (int i = 0; i < 3; i++) {
            TechnicalEngineer tech = new TechnicalEngineer();
            tech.setParent(this);
            addUnit(tech);
        }
    }
}
