package dhbw.mosbach.cor.composite;

import dhbw.mosbach.visitor.IPart;

public class Supervisor extends TeamMember {
    private final String password;

    public Supervisor(String password, TeamManager teamManager){
        this.password = password;
        teamManager.setParent(this);
        addUnit(teamManager);
    }

    public void delegate(IPart part) {
        for (TeamMember t:units
             ) {
            t.delegate(part);
        }
    }

    public String getPassword() {
        return password;
    }
}
