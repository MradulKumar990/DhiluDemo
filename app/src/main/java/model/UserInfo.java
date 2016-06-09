package model;

/**
 * Created by Mradul on 6/7/2016.
 */
public class UserInfo {
    int image;
    String title;
    String desc;

    public UserInfo(String title, String desc, int image){
        setTitle(title);
        setDesc(desc);
        setImage(image);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
