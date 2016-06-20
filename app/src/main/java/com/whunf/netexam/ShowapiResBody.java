package com.whunf.netexam;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ShowapiResBody {


    private int allPages;
    private int ret_code;
    private Joke[] contentlist;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public Joke[] getContentlist() {
        return contentlist;
    }

    public void setContentlist(Joke[] contentlist) {
        this.contentlist = contentlist;
    }


    @Override
    public String toString() {
        return "ShowapiResBody{" +
                "allPages=" + allPages +
                ", ret_code=" + ret_code +
                ", contentlist=" + Arrays.toString(contentlist) +
                '}';
    }
}
