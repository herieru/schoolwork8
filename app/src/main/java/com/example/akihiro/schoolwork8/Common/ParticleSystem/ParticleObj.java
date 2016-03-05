package com.example.akihiro.schoolwork8.Common.ParticleSystem;

import com.example.akihiro.schoolwork8.Common.Usuful.Pad2d;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.GLWorld;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/31.
 */
public class ParticleObj {

    GLWorld world;          //表示する場所
    Vector3 move;           //移動位置
    boolean IsLife;         //生きているか？
    int life_cnt;



    ParticleObj()
    {
        world = new GLWorld();
        move = new Vector3();
    }

    void Init (GLWorld set_world,Vector3 set_move,int set_life_cnt)
    {
        world = set_world;              //ポジションなど
        move = set_move;                //動く量
        IsLife = true;                  //生存しているか？
        life_cnt = set_life_cnt;        //生きている時間
    }

    void Update()
    {
        //生きている間動作
        if(IsLife)
        {
            //生きている時間なくなったら死亡
            if(life_cnt <= 0)
            {
                IsLife = false;
                return;
            }
            world._pos.x += move.x;
            world._pos.y += move.y;
            world._pos.z += move.z;
            life_cnt--;
        }
    }

    void onDraw(GL10 gl)
    {
    }



}
