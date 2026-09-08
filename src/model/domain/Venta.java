package model.domain;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private List<ItemVenta> items;

    public Venta() {
        items = new ArrayList<>();
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }

    public void agregarItem(ItemVenta item) {
        items.add(item);
    }
}
