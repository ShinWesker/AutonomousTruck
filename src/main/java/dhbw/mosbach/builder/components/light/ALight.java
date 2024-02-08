package dhbw.mosbach.builder.components.light;

import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import lombok.Getter;

@Getter
public abstract class ALight {
    protected Boolean isOn = false;
    protected Position position;
    protected HorizontalPosition horizontalPosition;

    protected ALight(Position position, HorizontalPosition horizontalPosition){
        this.position = position;
        this.horizontalPosition = horizontalPosition;

    }

    public void activate(){
        isOn = true;
    }
    public void deactivate(){
        isOn = false;
    }
    @Override
    public String toString() {
        return String.format(
                """
                LightType: %s
                Status: %s
                HashCode: %d
                """,
                this.getClass().getSimpleName(),
                isOn ? "On" : "Off",
                this.hashCode());
    }
}
