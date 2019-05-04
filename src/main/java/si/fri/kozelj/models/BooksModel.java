package si.fri.kozelj.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksModel {

    @SerializedName("data")
    private List<BooksItem> booksItems;

    public BooksModel(List<BooksItem> booksItems) {
        this.booksItems = booksItems;
    }

    public List<BooksItem> getBooksItems() {
        return booksItems;
    }
}
