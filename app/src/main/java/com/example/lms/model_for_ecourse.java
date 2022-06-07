package com.example.lms;

public class model_for_ecourse
{
    private String ec_no;
    private String ec_name;
    private int ec_img;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private int cid;

    public String getEc_no()
    {
        return ec_no;
    }

    public void setEc_no(String ec_no)
    {
        this.ec_no = ec_no;
    }

    public String getEc_name()
    {
        return ec_name;
    }

    public void setEc_name(String ec_name)
    {
        this.ec_name = ec_name;
    }

    public int getEc_img()
    {
        return ec_img;
    }

    public void setEc_img(int ec_img)
    {
        this.ec_img = ec_img;
    }
}
