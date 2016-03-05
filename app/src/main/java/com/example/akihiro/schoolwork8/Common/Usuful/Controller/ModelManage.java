package com.example.akihiro.schoolwork8.Common.Usuful.Controller;

import android.content.Context;

import com.example.akihiro.schoolwork8.Common.Usuful.ObjLoader;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ohs30355 on 2016/01/11.
 *this class singleton
 *
 * 描画と、３Dモデルの格納のみ。
 */
public class ModelManage {

    private Context context;
    private static ModelManage modelmgr = new ModelManage();

    private final static int MAX_MODEL =4;                          //モデルの取り扱い数
    private ObjLoader model_file[] =  new ObjLoader[MAX_MODEL];      //格納する配列
    //private
    //==============格納しているものにアクセスするためのenum
    public static final int COIN = 0;
    public static final int PLAYER = 1;
    public static final int MAP = 2;
    public static final int MONSTER = 3;
    //ファイルパス　アクセス整数と対応
   private String file_pass_name[] = {
            "coin.obj",
            "cube.obj",
            "map.obj",
            "chara.obj",
    };

    //========================================================
    //ここのインスタンスを持ってくる
    public static ModelManage GetInstance(){
        return modelmgr;
    }
    //=================初期化
    public void Init(Context context,GL10 gl)
    {
        this.context = context;
        //初期化
        for(int i = 0; i <MAX_MODEL;i++)
        {
            model_file[i] = new ObjLoader(context);
            model_file[i].load(file_pass_name[i],gl);
        }
    }

    //描画するモデルをNoを使って呼び出し
    public void Draw(GL10 gl,int call_no)
    {
        model_file[call_no].draw(gl);
    }





}
