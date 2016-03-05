package com.example.akihiro.schoolwork8.GameScene;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/20.
 * ゲームのシーンを継承できるような基底クラス
 */
public interface SceneI {
    void Init(GL10 gl);
    SceneI Update();                //更新
    void onDraw(GL10 gl);          //描画
}
