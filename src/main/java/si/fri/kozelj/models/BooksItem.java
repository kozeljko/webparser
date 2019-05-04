package si.fri.kozelj.models;

import com.google.gson.annotations.SerializedName;

public class BooksItem {

    @SerializedName("Title")
    private String title;

    @SerializedName("Author")
    private String author;

    @SerializedName("AvgRating")
    private String avgRating;

    @SerializedName("Ratings")
    private String ratings;

    @SerializedName("Score")
    private String score;

    @SerializedName("Votes")
    private String votes;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public String getRatings() {
        return ratings;
    }

    public String getScore() {
        return score;
    }

    public String getVotes() {
        return votes;
    }


    public static final class Builder {
        private String title;
        private String author;
        private String avgRating;
        private String ratings;
        private String score;
        private String votes;

        public Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder avgRating(String avgRating) {
            this.avgRating = avgRating;
            return this;
        }

        public Builder ratings(String ratings) {
            this.ratings = ratings;
            return this;
        }

        public Builder score(String score) {
            this.score = score;
            return this;
        }

        public Builder votes(String votes) {
            this.votes = votes;
            return this;
        }

        public BooksItem build() {
            BooksItem booksItem = new BooksItem();
            booksItem.ratings = this.ratings;
            booksItem.votes = this.votes;
            booksItem.title = this.title;
            booksItem.author = this.author;
            booksItem.score = this.score;
            booksItem.avgRating = this.avgRating;
            return booksItem;
        }
    }
}
