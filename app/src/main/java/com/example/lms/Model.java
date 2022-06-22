package com.example.lms;

public class Model
{
    private String weekedit;
    private int imgname;
    private String img_url;
    private String name_of_task;
    private String url_of_task;
    private String username;


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName_of_task() {
        return name_of_task;
    }

    public void setName_of_task(String name_of_task) {
        this.name_of_task = name_of_task;
    }

    public String getUrl_of_task() {
        return url_of_task;
    }

    public void setUrl_of_task(String url_of_task) {
        this.url_of_task = url_of_task;
    }



    public String getWeekedit()
    {
        return weekedit;
    }

    public void setWeekedit(String weekedit)
    {
        this.weekedit = weekedit;

    }

    public int getImgname()
    {
        return imgname;

    }

    public void setImgname(int imgname)
    {
        this.imgname = imgname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
