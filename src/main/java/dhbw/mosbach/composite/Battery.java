package dhbw.mosbach.composite;

import dhbw.mosbach.bridge.IBattery;

public class Battery implements IBattery {
    private final MainCell[] cells;

    public Battery(){
        cells = new MainCell[500];
        for (int i = 0; i < 500; i++) {
            cells[i] = new MainCell();
        }
    }

    public int getChargeRate() {
        int totalEnergyCells = 0;
        for (MainCell cell : cells) {
            totalEnergyCells += cell.countCellsHavingEnergy();
        }
        return totalEnergyCells;
    }

    @Override
    public void charge() {
        for (MainCell cell : cells) {
            cell.charge();
        }
    }

    @Override
    public void discharge(int amount) {
        for (MainCell cell : cells) {
            if (amount <= 0) break;
            int amountPerMainCell = Math.min(amount, 100 * 5);
            cell.discharge(amountPerMainCell);
            amount -= amountPerMainCell;
        }
    }
}
