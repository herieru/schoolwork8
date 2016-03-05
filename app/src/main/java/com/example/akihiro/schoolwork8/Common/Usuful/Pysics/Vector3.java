package com.example.akihiro.schoolwork8.Common.Usuful.Pysics;

/**
 * Created by ohs30355 on 2016/01/13.
 */
public class Vector3 {
    public float x,y,z;

    //========コンストラクタ====================
    public Vector3()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
    //======引数ありのコンストラクタ=============
    public Vector3(float x,float y,float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    //==============コンストラクタ===============
    public Vector3(Vector3 Inputvec)
    {
        this.x = Inputvec.x;
        this.y = Inputvec.y;
        this.z = Inputvec.z;
    }

    //初期化float3つ
    public void Init(float x,float y,float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    //Vector型での初期化
    public void Init(Vector3 InputVec)
    {
        this.x = InputVec.x;
        this.y = InputVec.y;
        this.z = InputVec.z;
    }




    public void ZeroPower()
    {
        this.x = this.y = this.z = 0;
    }

    //合成
    public Vector3 BlendVector3(Vector3 out,Vector3 in)
    {
        out.x = out.x + in.x;
        out.y = out.y + in.y;
        out.z = out.z + in.z;
        return out;
    }

    //float型で足したい時の合成方法
    public Vector3 BlendVector3F(Vector3 out,float x,float y,float z)
    {
        out.x += x;
        out.y += y;
        out.z += z;
        return out;
    }

    //逆ベクトル
    public void InverseVector3()
    {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    //ノーマライズ   単位ベクトル
    public void Nomarize()
    {
        float power = this.VectorLength();//斜辺の長さ　ベクトルの力の長さを求める

        this.x = this.x / power;
        this.y = this.y / power;
        this.z = this.z / power;

    }
    //ベクトルの力
    public float VectorLength()
    {
        float length;

        length = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));

        return length;

    }
    //内積
    public Vector3 Dot(Vector3 Input1,Vector3 Input2)
    {
        return Input1;
    }

    //外積   二つのベクトルに対して、垂直のものがでてくる。
    public Vector3 Cross(Vector3 Input1,Vector3 Input2 )
    {


        return Input1;
    }
    //現在のベクトルを倍率でかける
    public Vector3 VectorChangePower(Vector3 In,float change_power)
    {
        In.x = In.x * change_power;
        In.y = In.y * change_power;
        In.z = In.z * change_power;
        return In;
    }





}
