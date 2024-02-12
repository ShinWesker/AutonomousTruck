package dhbw.mosbach.builder.components;
import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.light.Lidar;
import dhbw.mosbach.builder.enums.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ExteriorMirror {
    @Setter
    private Camera camera;
    @Setter
    private Lidar lidar;
    private final Position position;

    public ExteriorMirror(Camera camera, Lidar lidar, Position position) {
        this.camera = camera;
        this.lidar = lidar;
        this.position = position;
    }
}
