package dhbw.mosbach.composite;

public class MainCell extends CellConstruct {

    public MainCell(){
        for (int i = 0; i < 100; i++) {
            addUnit(new SubCell());
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
         for (CellConstruct unit : units){
            if (amount <= 0) break;
            int amountPerSubCell = Math.min(amount, 5);
            unit.discharge(amountPerSubCell);
            amount -= amountPerSubCell;
        }
    }

    @Override
    public int countCellsHavingEnergy() {
        int count = 0;
        for (CellConstruct unit : units) {
            count += unit.countCellsHavingEnergy(); // Sum of all SubCell counts
        }
        return count;
    }
}
