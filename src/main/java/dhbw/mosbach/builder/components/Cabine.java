package dhbw.mosbach.builder.components;

import dhbw.mosbach.builder.components.light.HeadLight;
import lombok.Getter;

@Getter
public class Cabine {
    private final HeadLight[] headLight;
    private final ExteriorMirror[] exteriorMirrors;

    public Cabine(HeadLight[] headLight, ExteriorMirror[] exteriorMirrors) {
        this.headLight = headLight;
        this.exteriorMirrors = exteriorMirrors;
    }
}
