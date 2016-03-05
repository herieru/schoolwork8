package com.example.akihiro.schoolwork8.Common.GameObj;

import com.example.akihiro.schoolwork8.Common.Usuful.Controller.CameraControll;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.GLWorld;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.ModelManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.TouchController;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Shadow;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/23.
 */
public class Player extends Obj{


    private TouchController touch_cont = null;
    private Shadow shadow = null;
    private CameraControll camera = null;

    private float SLOW_WALK_LENGTH = 110.0f;//動き出す範囲
    private float FIST_WALK_LENGTH = 165.0f;//速度が上がるまでの

    private float SLOW_SPEED    =0.04f;
    private float FIST_SPEED = 0.1f;
    //コンストラクタ
    public Player()
    {
        model = null;
        if(this.model == null)
            model = ModelManage.GetInstance();
        if(touch_cont == null)
            touch_cont = TouchController.GetInstance();
        if(shadow == null)
            shadow = Shadow.GetInstance();
        if(camera == null)
            camera = CameraControll.GetInstance();
        IsLife = true;
        move_vec = new Vector3();//移動量調整
        w = new GLWorld();
        w._scale.Init(0.1f,0.3f,0.1f);//スケール調整
        w._pos.Init(0,0,0);
        objtype = OBJTYPE.PLAYER;
    }

    @Override
    public void Init() {
        IsLife = true;
    }

    @Override
    public boolean Update() {
       if(UpdateMoveContoroll())
        {
            camera.SetLook(w._pos);
        }

        return IsLife;
    }

    public boolean UpdateMoveContoroll()
    {
        //長さ取得
        float length = touch_cont.GetMoveLength();
        //フリックの状態だったら(ある一定の範囲の時間内に、一定の距離を超えている)
        if(touch_cont.SpeedFlick())
        {
            FlikMove();
            return true;
        }
        //fastSpeed
       if(length >= FIST_WALK_LENGTH)
        {
            SetMoveModel(FIST_SPEED);
            return true;
        }
        //SlowSpeed
        if(length >= SLOW_WALK_LENGTH)
        {
            SetMoveModel(SLOW_SPEED);
            return true;
        }
        return false;
    }


    @Override
    public void Draw(GL10 gl) {
        if(model != null) {
            gl.glPushMatrix();
            //奥行きにいきたいので、ｚとｙを入れ替え
            gl.glTranslatef(w._pos.x, w._pos.y, w._pos.z);
            gl.glRotatef(w._rad.x, 0, 0, 1);
            gl.glRotatef(w._rad.y, 0, 1, 0);
            gl.glRotatef(w._rad.z, 1, 0, 0);
            gl.glScalef(w._scale.x, w._scale.y, w._scale.z);
            model.Draw(gl, ModelManage.PLAYER);
            gl.glPopMatrix();

            //shadow.DrawModelShadow(gl,ModelManage.PLAYER);

        }

        touch_cont.TouchInfoDraw();
    }

    //移動量を渡してモデルを動かす
    private void SetMoveModel(float rate)
    {
        move_vec = touch_cont.GetVector3Power();                        //タッチの移動量で力が入ってくる。
        move_vec =move_vec.VectorChangePower(move_vec, rate);           //移動量をレートに合わせて変換
        //移動(位置 + 移動量)
        w._pos.BlendVector3F(w._pos, move_vec.x,move_vec.z,move_vec.y);
        move_vec.ZeroPower();                                   //移動量を消す
    }


    private void FlikMove()
    {
        move_vec = touch_cont.GetVector3Power();
        move_vec.Nomarize();
        w._pos.BlendVector3F(w._pos, move_vec.x,move_vec.z,move_vec.y);
        move_vec.ZeroPower();

    }
}
