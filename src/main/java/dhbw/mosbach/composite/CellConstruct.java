package dhbw.mosbach.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class CellConstruct {
    protected List<CellConstruct> units = new ArrayList<>();

    public void addUnit(CellConstruct cell){
        units.add(cell);
    }
    public void removeUnit(CellConstruct cell){
        units.remove(cell);
    }
    public abstract void charge();
    public abstract void discharge(int amount);
    public abstract int countCellsHavingEnergy();
}
