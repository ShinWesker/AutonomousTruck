package dhbw.mosbach.builder.components;
import dhbw.mosbach.builder.enums.Position;
import lombok.Getter;

@Getter
public class ExteriorMirror {
    private final Camera camera;
    private final Lidar lidar;
    private final Position position;

    public ExteriorMirror(Camera camera, Lidar lidar, Position position) {
        this.camera = camera;
        this.lidar = lidar;
        this.position = position;
    }
}
