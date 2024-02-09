package dhbw.mosbach.builder.components;

import lombok.Getter;

@Getter
public class Pallet {
    private String item;
    public Pallet(String item){
        this.item = item;
    }

    @Override
    public String toString(){
        return String.format("""
                Pallet: %d
                Item: %s
                """,this.hashCode(), item);
    }
}
