package com.example.akihiro.schoolwork8.Common.GameObj;

import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.GLWorld;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.ModelManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;
import com.example.akihiro.schoolwork8.Common.Usuful.SoundSe;

import javax.microedition.khronos.opengles.GL10;
/**
 * Created by akihiro on 2016/01/24.
 */
public class Coin extends Obj{



    public Coin()
    {
        model = null;
        if(this.model == null)
            model = ModelManage.GetInstance();
        w = new GLWorld();
        w._scale.Init(0.2f,0.2f,0.2f);//スケール調整
        IsLife = true;
        objtype = OBJTYPE.COIN;
        move_vec = new Vector3();
        se = SoundSe.GetInstance();
    }

    @Override
    public void Init() {
        IsLife = true;
        w._pos.Init(0.0f,0.0f,5.0f);
    }

    @Override
    public boolean Update() {
        //生きている間は更新
        if(IsLife)
        {
            Move();
        }
        return IsLife;
    }

    private void Move()
    {
        w._rad.y+=3.0f;
        w._pos.x += move_vec.x;
        w._pos.y += move_vec.y;
        w._pos.z += move_vec.z;
    }

    @Override
    public void Draw(GL10 gl) {
        if(model != null && IsLife) {
            gl.glPushMatrix();
            gl.glTranslatef(w._pos.x, w._pos.y, w._pos.z);
            gl.glRotatef(w._rad.x, 0, 0, 1);
            gl.glRotatef(w._rad.y, 0, 1, 0);
            gl.glRotatef(w._rad.z, 1, 0, 0);
            gl.glScalef(w._scale.x,w._scale.y,w._scale.z);
            model.Draw(gl, ModelManage.COIN);
            gl.glPopMatrix();
        }
    }

    @Override
    public void ChangeLife() {
        super.ChangeLife();
        se.SoundSe((char) 0);
    }
}
