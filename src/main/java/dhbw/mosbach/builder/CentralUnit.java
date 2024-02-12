package dhbw.mosbach.builder;

import dhbw.mosbach.builder.components.HoldingArea;
import dhbw.mosbach.builder.components.LoadingPlanItem;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.command.ICommand;
import dhbw.mosbach.key.IEncryption;
import dhbw.mosbach.key.SHA256;
import dhbw.mosbach.observer.ISensoricListener;
import lombok.Getter;
import lombok.Setter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CentralUnit implements ISensoricListener {
    @Setter
    private ICommand command;
    private final List<LoadingPlanItem> loadedItems = new ArrayList<>();
    @Getter
    private Boolean loadingPlanStatus = false;
    @Setter
    private AutonomousTruck truck;

    public void execute(){
        command.execute();
    }
    public void receiveSignal(String signal) {
        if (signal.equals("3468c6d5dd8dc44c969ea94c09de933b33d14c88c71ccf18fb01469b393855eb")) {
            truck.changeState();
        } else{
            System.out.println("wrong password!");
        }
    }
    @Override
    public void detect() {
        truck.setConnected(true);
        System.out.println("Trailer successfully connected!");
    }
    @Override
    public void detect(HoldingArea holdingArea) {
        checkItemLoaded(holdingArea);
    }
    private void checkItemLoaded(HoldingArea holdingArea) {
        for (LoadingPlanItem item : loadedItems) {
            if (item.index() == holdingArea.getIndex()
                    && Objects.equals(item.position(), holdingArea.getPosition().toString())
                    && item.item().equals(holdingArea.getPallet().getItem())) {
                loadedItems.remove(item);
                break;
            }
        }

        if (loadedItems.isEmpty()) {
            loadingPlanStatus = true;
            System.out.println("All items loaded according to the loading plan.");
        }
    }

    public void loadLoadingPlan(String filePath) {
        try (InputStream is = new FileInputStream(filePath)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                int index = object.getInt("index");
                String position = object.getString("position");
                String item = object.getString("item");

                LoadingPlanItem loadingPlanItem = new LoadingPlanItem(index, position, item);
                loadedItems.add(loadingPlanItem);
            }
            loadingPlanStatus = false;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }
}
