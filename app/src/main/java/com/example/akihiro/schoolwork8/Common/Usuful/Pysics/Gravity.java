package com.example.akihiro.schoolwork8.Common.Usuful.Pysics;

/**
 * Created by akihiro on 2016/01/27.
 * 重力
 */
public class Gravity {

    Vector3 grv;

    private float GRAVITY_POWER = -1.0f;

    Gravity()
    {
        grv = new Vector3(0,GRAVITY_POWER,0);
    }
    //重力を加えた値を渡す
    public Vector3 G(Vector3 out)
    {
        out.BlendVector3(out,grv);
        return out;
    }



}
