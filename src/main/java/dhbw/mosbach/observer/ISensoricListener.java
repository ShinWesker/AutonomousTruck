package dhbw.mosbach.observer;

import dhbw.mosbach.builder.components.HoldingArea;
import dhbw.mosbach.builder.trailer.Trailer;

public interface ISensoricListener {
    void detect();
    void detect(HoldingArea holdingArea, String s);
}
