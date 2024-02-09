package dhbw.mosbach.composite;

public class Cell extends CellConstruct {

    private boolean energy;
    public Cell() {
        this.energy = true;
    }

    @Override
    public void charge() {
        this.energy = true;
    }

    @Override
    public void discharge(int amount) {
        if (amount > 0) {
            this.energy = false;
        }
    }

    @Override
    public int countCellsHavingEnergy() {
        return energy ? 1 : 0;
    }

    public boolean hasEnergy() {
        return energy;
    }
}
