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
    @Override
    public void charge() {
        for (MainCell cell : cells) {
            cell.charge();
        }
        System.out.println("Battery fully charged.");
    }

    @Override
    public void discharge(int amount) {
        System.out.println("Battery discharged by " + amount);
        for (MainCell cell : cells) {
            if (amount <= 0) break;
            int amountPerMainCell = Math.min(amount, 100 * 5);
            cell.discharge(amountPerMainCell);
            amount -= amountPerMainCell;
        }
    }
}
