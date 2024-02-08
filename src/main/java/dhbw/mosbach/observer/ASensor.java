package dhbw.mosbach.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class ASensor {
    protected List<ISensoricListener> listeners = new ArrayList<>();
    public void addListener(ISensoricListener listener){
        listeners.add(listener);
    }
    public void removeListener(ISensoricListener listener){
        listeners.remove(listener);
    }
}
