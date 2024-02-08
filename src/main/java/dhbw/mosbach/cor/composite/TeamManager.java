package dhbw.mosbach.cor.composite;

import dhbw.mosbach.visitor.IPart;

import java.util.Random;

public abstract class TeamManager extends TeamMember {
    public TeamManager() {
        super();
    }

    public String getPassword() {
        if (parent != null) {
            return parent.getPassword();
        } else {
            throw new IllegalStateException("No parent set for this team manager.");
        }
    }

    @Override
    public void delegate(IPart part) {
        if (!units.isEmpty()) {
            long currentTime = System.currentTimeMillis();
            Random random = new Random(currentTime);
            int index = random.nextInt(units.size());

            TeamMember randomMember = units.get(index);
            randomMember.delegate(part);
        } else {
            System.out.println("No team members to delegate to.");
        }
    }
}
