package dhbw.mosbach.cor.composite;

import dhbw.mosbach.visitor.IPart;

import java.util.ArrayList;
import java.util.List;

public abstract class TeamMember {
    protected List<TeamMember> units = new ArrayList<>();
    protected TeamMember parent;
    public void setParent(TeamMember parent) {
        this.parent = parent;
    }

    public void addUnit(TeamMember teamMember){
        units.add(teamMember);
    }

    public void removeUnit(TeamMember teamMember){
        units.remove(teamMember);
    }

    public abstract void delegate(IPart part);

    public abstract String getPassword();
}
