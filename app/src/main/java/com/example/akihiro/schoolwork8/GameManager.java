package com.example.akihiro.schoolwork8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.PaintDrawable;

import com.example.akihiro.schoolwork8.Common.Usuful.Pad2d;
import com.example.akihiro.schoolwork8.GameScene.SceneI;
import com.example.akihiro.schoolwork8.GameScene.ScenePlay;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/20.
 * ゲームのステートなどをここで変化させる。
 */
public class GameManager {

    Context context;
    public SceneI now_scene;
    GameManager(Context context)
    {
        now_scene = new ScenePlay(context);
    }

    public void Init(GL10 gl)
    {
        now_scene.Init(gl);
    }

    void Update()
    {
        now_scene.Update();
    }

    void onDraw(GL10 gl)
    {
        now_scene.onDraw(gl);
    }



}
