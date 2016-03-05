package com.example.akihiro.schoolwork8.Common.Usuful.Controller;

import android.content.Context;

import com.example.akihiro.schoolwork8.Common.Usuful.Controller.TouchController;

/**
 * Created by akihiro on 2016/01/20.
 * Contextなどのすべての取得が必要なところをアクセスできるようにするもの
 */
public class ContentManage {
    Context context;
    public TouchController touch;
    public int SCREEN_HIGHT;
    public int SCREEN_WIDTH;

    //ここですべてをnewなどをしていく
    ContentManage(Context context)
    {
        this.context = context;
    }

    void SurfaceChange(int width,int height)
    {
        SCREEN_WIDTH = width;
        SCREEN_HIGHT = height;
    }





}
