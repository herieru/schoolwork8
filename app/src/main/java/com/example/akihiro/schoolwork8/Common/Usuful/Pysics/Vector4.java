package com.example.akihiro.schoolwork8.Common.Usuful.Pysics;

/**
 * Created by akihiro on 2016/01/24.
 */
public class Vector4 {
    public float   x, y, z,//ベクトル
                    a;      //アルファ
    public Vector4()
    {
        x = 1.0f;
        y = 1.0f;
        z = 1.0f;
        a = 1.0f;
    }

    public Vector4(float x,float y,float z,float a)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }

    //初期化float4つ
    public void Init(float x,float y,float z,float a)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }
}
