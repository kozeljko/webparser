package si.fri.kozelj.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OverstockModel {
    @SerializedName("data")
    private List<OverstockItem> overstockItems;

    public OverstockModel(List<OverstockItem> overstockItems) {
        this.overstockItems = overstockItems;
    }

    public List<OverstockItem> getOverstockItems() {
        return overstockItems;
    }
}
