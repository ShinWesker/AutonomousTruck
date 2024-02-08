package dhbw.mosbach.builder.components.light;

import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;

public class HeadLight extends ALight{
    public HeadLight(Position position) {
        super(position, HorizontalPosition.FRONT);
    }
}
