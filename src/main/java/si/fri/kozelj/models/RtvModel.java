package si.fri.kozelj.models;

import com.google.gson.annotations.SerializedName;

public class RtvModel {

    @SerializedName("Author")
    private String author;

    @SerializedName("PublishedTime")
    private String publishedTime;

    @SerializedName("Title")
    private String title;

    @SerializedName("SubTitle")
    private String subTitle;

    @SerializedName("Lead")
    private String lead;

    @SerializedName("Content")
    private String content;

    public String getAuthor() {
        return author;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getLead() {
        return lead;
    }

    public String getContent() {
        return content;
    }


    public static final class Builder {
        private String author;
        private String publishedTime;
        private String title;
        private String subTitle;
        private String lead;
        private String content;

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder publishedTime(String publishedTime) {
            this.publishedTime = publishedTime;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder subTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder lead(String lead) {
            this.lead = lead;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public RtvModel build() {
            RtvModel rtvModel = new RtvModel();
            rtvModel.lead = this.lead;
            rtvModel.title = this.title;
            rtvModel.content = this.content;
            rtvModel.author = this.author;
            rtvModel.publishedTime = this.publishedTime;
            rtvModel.subTitle = this.subTitle;
            return rtvModel;
        }
    }
}
