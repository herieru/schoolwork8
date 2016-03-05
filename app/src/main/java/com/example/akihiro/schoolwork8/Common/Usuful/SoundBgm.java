package com.example.akihiro.schoolwork8.Common.Usuful;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

import com.example.akihiro.schoolwork8.R;

/**
 * Created by ohs30355 on 2015/07/08.
 * サウンドのBGMを鳴らすだけのもの　　これは統合してる部分だけでやる方がいいかもしれない？
 */
public class SoundBgm {


    private static SoundBgm my_bgm = new SoundBgm();
    //固定値
    private static final char BGMSUU = 10;              //曲の取扱数
    private MediaPlayer []bgm = new MediaPlayer[BGMSUU];//bgmを格納する

    public static SoundBgm GetInstance()
    {
        return my_bgm;
    }

    //コンストラクタ
    public SoundBgm() {
        for(int i=0;i<BGMSUU;i++)
        {
            bgm[i] = new MediaPlayer();
        }
    }

    //プログラムのはじめに一度だけInit　ゲーム始まる前
    public void OnceInitStart(Context context)
    {
        //BGMを格納していく
       /*. bgm[0] = MediaPlayer.create(context, R
                raw.bgm_title);
        bgm[0].setLooping(true);//ループ設定
        bgm[1] = MediaPlayer.create(context,R.raw.bgm_game);
        bgm[1].setLooping(true);//ループ設定
        bgm[2] = MediaPlayer.create(context,R.raw.bgm_ranking);
        bgm[2].setLooping(true);//ループ設定
        bgm[3] = MediaPlayer.create(context,R.raw.bgm_clear);
        bgm[3].setLooping(true);//ループ設定
        bgm[4] = MediaPlayer.create(context,R.raw.worning);
        bgm[4].setLooping(true);//ループ設定
        //bgm[5] = MediaPlayer.create(context,R.raw.);
        //bgm[6] = MediaPlayer.create(context,R.raw.);
        //bgm[7] = MediaPlayer.create(context,R.raw.);
        //bgm[8] = MediaPlayer.create(context,R.raw.);
        //bgm[9] = MediaPlayer.create(context,R.raw.);
        */

    }


    //指定したBGMを鳴らす
    public void SoundBGM(char ban) {
        bgm[ban].start();
    }
    //指定したBGMを止める
    public void StopBGM(char ban)
    {
        if(bgm[ban].isPlaying())
        {
            bgm[ban].pause();
        }
    }
    //全てのBGMを止める
    public void StopBgmAll()
    {
        for(int i = 0;i<BGMSUU;i++)
        {
            if(bgm[i].isPlaying())//再生中なら止める
                bgm[i].pause();
            /*
            try{// まずこれを試して、むりだったらキャッチの処理が走る。
                bgm[i].prepare();
            }catch (Exception e){
            }*/
        }
    }
}
