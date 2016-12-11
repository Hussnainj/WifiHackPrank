package wifihackprank.android.dgaps.wifihackprank;

/**
 * Created by wasim on 12/11/2016.
 */

public class Element {

    public  String title;
    public  String security;
    public  String level;
    public  String conName;

    public Element(String title, String security, String level, String conName) {
        this.title = title;
        this.security = security;
        this.level = level;
        this.conName=conName;
    }

    public String getTitle() {
        return title;
    }

    public String getSecurity() {
        return security;
    }

    public String getLevel() {
        return level;
    }

    public String getConName() {
        return conName;
    }
}
