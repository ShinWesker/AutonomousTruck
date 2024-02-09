package dhbw.mosbach.observer;

import dhbw.mosbach.builder.components.HoldingArea;

public interface ISensoricListener {
    void detect();
    void detect(HoldingArea holdingArea);
}
