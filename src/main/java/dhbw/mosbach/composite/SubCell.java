package dhbw.mosbach.composite;

public class SubCell extends CellConstruct {
    public SubCell(){
        for (int i = 0; i < 5; i++) {
            addUnit(new Cell());
        }
    }

    @Override
    public void charge() {
        for (CellConstruct unit : units) {
            unit.charge();
        }
    }

    @Override
    public void discharge(int amount) {
        for (CellConstruct unit : units) {
            if (amount <= 0) break;
            unit.discharge(1);
            amount--;
        }
    }

    @Override
    public int countCellsHavingEnergy() {
        int count = 0;
        for (CellConstruct unit : units) {
            if (((Cell)unit).hasEnergy()) { // Safe cast since SubCell only contains Cell instances
                count++;
            }
        }
        return count;
    }

}
