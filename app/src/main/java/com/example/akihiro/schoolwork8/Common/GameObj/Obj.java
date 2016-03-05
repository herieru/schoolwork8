package com.example.akihiro.schoolwork8.Common.GameObj;

import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.GLWorld;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.ModelManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.HitBox.HitBoxInterface;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;
import com.example.akihiro.schoolwork8.Common.Usuful.SoundSe;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/20.
 */



public class Obj implements ObjMethod {

    protected ModelManage model = null;
    protected boolean IsLife;
    protected SoundSe se;


    public GLWorld w;               //pos,rad,scale
    public Vector3 move_vec;       //移動量
    public OBJTYPE objtype;         //この物体のタイプ
    public HitBoxInterface hit_type;   //当たり判定の種類

    //OBJのタイプが何かを判断させる
    public enum OBJTYPE{
        PLAYER,
        BLOCK,
        MOVE_BLOCK,
        COIN,
        MONTER,
    };

    @Override
    public void Init() {
    }

    public void Init(float pos_x,float pos_y,float pos_z){
        IsLife = true;
        w._pos.Init(pos_x,pos_y,pos_z);
    }

    @Override
    public boolean Update() {
        return false;
    }

    @Override
    public void Draw(GL10 gl) {

    }

    public void ChangeLife()
    {
        IsLife = !IsLife;
    }
//生きているか？を返すだけ
    public boolean GetLifeInfo()
    {
        return IsLife;
    }
    public HitBoxInterface.HIT_TYPE GetHitType()
    {
        return hit_type.GetHitInfo();
    }
    //ポジションセット
    public void SetPosition(Vector3 newpos)
    {
        this.w._pos.x  = newpos.x;
        this.w._pos.y  = newpos.y;
        this.w._pos.z  = newpos.z;
    }
    //回転量
    public void SetRotation(Vector3 newrot)
    {
        this.w._rad.x = newrot.x;
        this.w._rad.y = newrot.y;
        this.w._rad.z = newrot.z;
    }
    //スケールセット
    public void SetScale(Vector3 scale)
    {
        this.w._scale.x = scale.x;
        this.w._scale.y = scale.y;
        this.w._scale.z = scale.z;
    }
    //移動量セット
    public void SetVector(Vector3 vec)
    {
        move_vec.x = vec.x;
        move_vec.y = vec.z;
        move_vec.z = vec.y;
    }



}
