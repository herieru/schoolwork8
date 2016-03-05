package com.example.akihiro.schoolwork8.Common.Usuful.Backyard;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/02/07.
 */
public class ValueManage {

    private static ValueManage value = new ValueManage();

    //スクリーン座標
    public int screenWidth;
    public int screenHeight;
    public Context context;
    public GL10 gl;



    private ValueManage(){

    }


    public static ValueManage GetInstance(){
        return value;
    }

    public void SetWindowSize(int width,int height)
    {
        screenWidth = width;
        screenHeight = height;
    }
}
