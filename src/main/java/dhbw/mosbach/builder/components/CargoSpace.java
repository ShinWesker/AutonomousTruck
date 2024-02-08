package dhbw.mosbach.builder.components;
import dhbw.mosbach.builder.enums.Position;
import lombok.Getter;

public class CargoSpace {

    @Getter
    private final HoldingArea[] area;
    public CargoSpace() {
        area = new HoldingArea[16];

        for (int i = 0; i < area.length; i++) {
            Position position = i % 2 == 0 ? Position.LEFT : Position.RIGHT;
            area[i] = new HoldingArea(i, position);
        }
    }
    public void load(Pallet pallet, int index){
        area[index].load(pallet);
    }

    public void unload(int index){
        area[index].unload();
    }
}
