package com.example.akihiro.schoolwork8.Common.Usuful.Pysics;

import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;

/**
 * Created by akihiro on 2016/01/21.
 */
public class GLWorld {
    public Vector3 _pos;             //位置情報
    public Vector3 _rad;             //回転量
    public Vector3 _scale;           //大きさ

    public GLWorld()
    {
        _pos   = new Vector3();
        _rad   = new Vector3();
        _scale = new Vector3(1,1,1);
    }

    public void Init()
    {
        _pos.Init(0,0,0);
        _rad.Init(0,0,0);
        _scale.Init(1,1,1);
    }


}
