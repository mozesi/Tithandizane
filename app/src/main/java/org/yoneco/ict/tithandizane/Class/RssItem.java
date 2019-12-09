package org.yoneco.ict.tithandizane.Class;

/**
 * Created by Moses on 8/8/2017.
 */
public class RssItem{
private final String title;
private final String link;

        public RssItem(String title, String link) {
            this.title = title;
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }
}

