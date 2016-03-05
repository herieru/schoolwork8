package com.example.akihiro.schoolwork8.Common.GameObj;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/24.
 * 各オブジェクトのメソッドを実装する。
 */
public interface ObjMethod {

    public void Init();             //初期化
    public void Init(float pos_x,float pos_y,float pos_z);
    public boolean Update();        //更新
    public void Draw(GL10 gl);      //描画
    public void ChangeLife();       //生命の状態変化
}
