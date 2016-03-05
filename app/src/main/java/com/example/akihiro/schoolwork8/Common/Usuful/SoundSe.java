package com.example.akihiro.schoolwork8.Common.Usuful;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.akihiro.schoolwork8.R;

/**
 * Created by ohs30355 on 2015/07/08.
 */
/*
SE系を鳴らすためのもの
 */



public class SoundSe {


    private static SoundSe seint = new SoundSe();
    //固定値
    private static final char SESUU = 10;
    private SoundPool se;
    private int []SoundId = new int[SESUU];//サウンドID

    private boolean IsUse;

    public static SoundSe GetInstance()
    {
        return seint;
    }



    //コンストラクタ
    private SoundSe() {
        //プール数　、　形式　、　クオリティ
        se = new SoundPool(SESUU, AudioManager.STREAM_MUSIC,0);//SEを格納する
        IsUse =false;

    }

    public void Init(Context context)
    {
        //IDと効果音を結びつける。
        SoundId[0] = se.load(context, R.raw.coinget,1);//コインゲット音
        SoundId[1] = se.load(context,R.raw.steel,1);//レーザー音
        /*SoundId[2] = se.load(context,R.raw.pyo1,1);//ピョー音
        SoundId[3] = se.load(context,R.raw.explosion1,1);//爆発音*/
        // SoundId[4] = se.load(context,R.raw.worning,1);//攻撃音
        //SoundId[5] = se.load(context,R.raw.hitting,1);//攻撃音
        //SoundId[6] = se.load(context,R.raw.hitting,1);//攻撃音
        //SoundId[7] = se.load(context,R.raw.hitting,1);//攻撃音
        //SoundId[8] = se.load(context,R.raw.hitting,1);//攻撃音
        //SoundId[9] = se.load(context,R.raw.hitting,1);//攻撃音

        IsUse = true;
    }

    //普通にSEを鳴らす
    public void SoundSe(char ban)
    {
        /*
        soundId:関連付けしたID
        左右のスピーカーから出る音量の大きさ　0.0f～１.0f
        優先度:０が一番優先度高い。
        ループ回数：－１が無限ループ
        再生速度：0.5f～2.0f倍速
         */
        se.play(SoundId[ban],1.0F, 1.0F, 0, 0, 1.0F);
    }

    //SEにボリュームとスピードを付け足した。
    public void SoundSeBS(int ban,float voice,float speed)
    {
        se.play(SoundId[ban],voice, voice, 0, 0, speed);
    }



}
