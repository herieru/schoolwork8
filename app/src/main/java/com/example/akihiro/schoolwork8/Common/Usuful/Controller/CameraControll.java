package com.example.akihiro.schoolwork8.Common.Usuful.Controller;

import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;

/**
 * Created by akihiro on 2016/01/21.
 */
public class CameraControll {

    private static CameraControll camera = new CameraControll();
    //モードによってカメラの動きが変わる
    private static int NONE_MODE = 0;           //その場所にて固定
    private static int CHACE_MODE = 1;          //追跡モード
    public Vector3 pos;                //カメラの位置
    public Vector3 look;               //カメラの注視点
    public Vector3 up;                 //カメラのアップベクトル
    private boolean chace_flg = false;//ぐわんと追跡するか？
    //引数
    public static CameraControll GetInstance()
    {
        return camera;
    }

    private CameraControll()
    {
        pos = new Vector3(0,10,-10);
        look = new Vector3(0,0,0);
        up = new Vector3(0,1,0);
        chace_flg = false;
    }

    //未実装
    public void InitMode(int mode)
    {
        if(mode == CHACE_MODE)
            chace_flg = true;
    }

    public void SetPos(Vector3 pos)
    {
        this.pos = pos;
    }

    public void SetLook(Vector3 look)
    {
        this.look = look;
    }

    public void SetPosLook(Vector3 pos,Vector3 look){
        this.pos = pos;
        this.look = look;
    }


}
