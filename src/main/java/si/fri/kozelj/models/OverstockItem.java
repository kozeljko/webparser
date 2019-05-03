package si.fri.kozelj.models;

import com.google.gson.annotations.SerializedName;

public class OverstockItem {

    @SerializedName("Title")
    private String title;

    @SerializedName("ListPrice")
    private String listPrice;

    @SerializedName("Price")
    private String price;

    @SerializedName("Saving")
    private String saving;

    @SerializedName("SavingPercent")
    private String savingPercent;

    @SerializedName("Content")
    private String content;

    public String getTitle() {
        return title;
    }

    public String getListPrice() {
        return listPrice;
    }

    public String getPrice() {
        return price;
    }

    public String getSaving() {
        return saving;
    }

    public String getSavingPercent() {
        return savingPercent;
    }

    public String getContent() {
        return content;
    }


    public static final class Builder {
        private String title;
        private String listPrice;
        private String price;
        private String saving;
        private String savingPercent;
        private String content;

        public Builder() {

        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder listPrice(String listPrice) {
            this.listPrice = listPrice;
            return this;
        }

        public Builder price(String price) {
            this.price = price;
            return this;
        }

        public Builder saving(String saving) {
            this.saving = saving;
            return this;
        }

        public Builder savingPercent(String savingPercent) {
            this.savingPercent = savingPercent;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public OverstockItem build() {
            OverstockItem overstockItem = new OverstockItem();
            overstockItem.saving = this.saving;
            overstockItem.title = this.title;
            overstockItem.listPrice = this.listPrice;
            overstockItem.content = this.content;
            overstockItem.price = this.price;
            overstockItem.savingPercent = this.savingPercent;
            return overstockItem;
        }
    }
}
