package com.example.akihiro.schoolwork8.GameScene;

import android.content.Context;

import com.example.akihiro.schoolwork8.Common.GameObj.Coin;
import com.example.akihiro.schoolwork8.Common.GameObj.Monster;
import com.example.akihiro.schoolwork8.Common.GameObj.Obj;
import com.example.akihiro.schoolwork8.Common.GameObj.Player;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.ModelManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.TouchController;
import com.example.akihiro.schoolwork8.Common.Usuful.Pad2d;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.HitTest;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;


import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/20.
 */
public class ScenePlay implements SceneI {

    private Context context;
    private int MAX_OBJECT  = 0;
    //うまくいけば下にモデルは終結
    ModelManage model_manage;               //モデルを一括管理クラス
    //TouchController touch_cont;             //タッチを一括管理クラス
    Vector3 tmpvec;
    private HitTest hit;
    int score;                              //スコア
    int score_box[] = new int[5];      //スコア格納
    Pad2d pad;
    int time;


    Obj object[];// = new Obj[MAX_OBJECT];
    private Obj.OBJTYPE setobjtype[] = {
            Obj.OBJTYPE.PLAYER,
            Obj.OBJTYPE.COIN,
            Obj.OBJTYPE.COIN,
            Obj.OBJTYPE.COIN,
            Obj.OBJTYPE.COIN,
            Obj.OBJTYPE.COIN,
            Obj.OBJTYPE.MONTER,
            Obj.OBJTYPE.MONTER,
            };




    //コンストラクタ Contextも他のクラスが全部管理した上でそれを持つ。
    public ScenePlay(Context context){
        model_manage =ModelManage.GetInstance();
        this.context = context;
        tmpvec = new Vector3();
        MAX_OBJECT = setobjtype.length;
        object = new Obj[MAX_OBJECT];
        hit = new HitTest();
        score = 100;
        CreateObject();             //オブジェクトの生成

        pad = Pad2d.GetInstance();
        time = 1000;
    }

    //オブジェクトを作成する
    private void CreateObject()
    {
        for(int i = 0; i<MAX_OBJECT;i++)
        {
            //格納されている配列を見てその順番通りに作成する。
            switch(setobjtype[i])
            {
                case PLAYER:
                    object[i] = new Player();
                    break;
                case COIN:
                    tmpvec.x = (float)Math.random() * 2;
                    tmpvec.z = (float)Math.random() * 2;
                    object[i] = new Coin();
                    object[i].Init(tmpvec.x,tmpvec.y,tmpvec.z);
                    tmpvec.ZeroPower();
                    break;
                case BLOCK:
                    break;
                case MOVE_BLOCK:
                    break;
                case MONTER:
                    object[i] = new Monster();
                    int rorl = (int)((Math.random()*10))%2;
                    tmpvec.y = 0;
                    if(rorl==0) {
                        tmpvec.x = (float)Math.random() * 2;
                        tmpvec.z = 0.5f;
                        object[i].SetPosition(tmpvec);

                    }//if
                    else {
                        tmpvec.x = 0.5f;
                        tmpvec.z = (float) Math.random() * 2;
                        object[i].SetPosition(tmpvec);
                    }//else
                    tmpvec.x = (float)Math.random()/10;
                    tmpvec.y = (float)Math.random()/10;
                    tmpvec.z = 0;
                    object[i].SetVector(tmpvec);
                    break;
            }//switch
        }//for

        score_box[0] = (score/10000)%10;
        score_box[1] = (score/1000)%10;
        score_box[2] = (score/100)%10;
        score_box[3] = (score/10)%10;
        score_box[4] = score%10;
    }

    @Override
    public void Init(GL10 gl) {
        model_manage.Init(context, gl);

    }

    @Override
    public SceneI Update() {
        int p_no = -1;
        for(int i = 0; i < MAX_OBJECT;i++)
        {//
            if(object[i].objtype == Obj.OBJTYPE.PLAYER)
                p_no = i;//プレイヤーの番号のため保留
            object[i].Update();//それぞれのオブジェクトの更新

            //プレイヤーでない、且つコイン
            if(p_no != i && object[i].objtype == Obj.OBJTYPE.COIN
                    && object[i].GetLifeInfo())
            {
                //コインとの当たり判定
                if(hit.IsCircleHit(object[p_no],object[i]))
                {
                    object[i].ChangeLife();
                    tmpvec.x = ((float)Math.random() -  (float)Math.random())* 2;
                    tmpvec.z = ((float)Math.random() -  (float)Math.random())* 2;
                    object[i].Init(tmpvec.x, tmpvec.y, tmpvec.z);
                    score++;
                    ScoreReSet();
                }
            }
            if(p_no != i && object[i].objtype == Obj.OBJTYPE.MONTER && object[i].GetLifeInfo())
            {
                boolean Ispop = false;//新しく出現させるか？
                //モンスターとプレイヤーが当たったらスコア減少
                if(hit.IsCircleHit(object[p_no],object[i]))
                {

                  score-=10;
                    if(score <= 0)score=0;
                    ScoreReSet();
                    object[i].ChangeLife();
                    Ispop = true;
                }//if

                if(object[i].w._pos.x >= 5 || object[i].w._pos.x <= -5
                        | object[i].w._pos.z >= 5 || object[i].w._pos.z <= -5)
                    Ispop = true;

                //出現
                if(Ispop)
                {
                    tmpvec.ZeroPower();
                    //新しい場所に出現
                    int rorl = (int)((Math.random()*10))%2;
                    tmpvec.y = 0;
                    if(rorl==0) {
                        tmpvec.x = (float)Math.random() * 2;
                        tmpvec.z = 3.5f;

                    }//if
                    else {
                        tmpvec.x = 3.5f;
                        tmpvec.z = (float) Math.random() * 2;
                    }//else
                    object[i].Init(tmpvec.x,tmpvec.y,tmpvec.z);
                    tmpvec.x = -(float)Math.random();
                    tmpvec.y = -(float)Math.random();
                    tmpvec.Nomarize();
                    tmpvec.VectorChangePower(tmpvec,0.01f);
                    tmpvec.z = 0;
                    object[i].SetVector(tmpvec);

                }
            }
        }
        return null;
    }


    @Override
    public void onDraw(GL10 gl)
    {
       gl.glPushMatrix();
        gl.glTranslatef(0, -1, 0);
        gl.glRotatef(0, 0, 0, 1);
        gl.glRotatef(0, 0, 1, 0);
        gl.glRotatef(0, 1, 0, 0);
        gl.glScalef(1, 1, 1);
       model_manage.Draw(gl, model_manage.MAP);
        gl.glPopMatrix();
        for(int i = 0; i < MAX_OBJECT;i++)
        {
            object[i].Draw(gl);
        }
        //pad.draw(gl, Pad2d.TEX_INDEX.ZERO,100,100,250,250);
        DrawScore(gl);
    }

    private void DrawScore(GL10 gl)
    {
        for(int i = 0;i<5;i++)
        {
            switch (score_box[i])
            {
                case 0:
                    pad.draw(gl, Pad2d.TEX_INDEX.ZERO,100,100,250 + i * 100,250);
                    break;
                case 1:
                    pad.draw(gl, Pad2d.TEX_INDEX.ITI,100,100,250 + i * 100,250);
                    break;
                case 2:
                    pad.draw(gl, Pad2d.TEX_INDEX.NI,100,100,250 + i * 100,250);
                    break;
                case 3:
                    pad.draw(gl, Pad2d.TEX_INDEX.SAN,100,100,250 + i * 100,250);
                    break;
                case 4:
                    pad.draw(gl, Pad2d.TEX_INDEX.YON,100,100,250 + i * 100,250);
                    break;
                case 5:
                    pad.draw(gl, Pad2d.TEX_INDEX.GO,100,100,250 + i * 100,250);
                    break;
                case 6:
                    pad.draw(gl, Pad2d.TEX_INDEX.ROKU,100,100,250 + i * 100,250);
                    break;
                case 7:
                    pad.draw(gl, Pad2d.TEX_INDEX.NANA,100,100,250 + i * 100,250);
                    break;
                case 8:
                    pad.draw(gl, Pad2d.TEX_INDEX.HATI,100,100,250 + i * 100,250);
                    break;
                case 9:
                    pad.draw(gl, Pad2d.TEX_INDEX.KYUU,100,100,250 + i * 100,250);
                    break;
            }//switch
        }//for
    }

    //スコアの再セット
    private void ScoreReSet()
    {
        score_box[0] = (score/10000)%10;
        score_box[1] = (score/1000)%10;
        score_box[2] = (score/100)%10;
        score_box[3] = (score/10)%10;
        score_box[4] = score%10;
    }

}
