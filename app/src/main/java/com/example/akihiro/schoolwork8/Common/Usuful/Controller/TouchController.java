package com.example.akihiro.schoolwork8.Common.Usuful.Controller;

import android.util.Log;
import android.view.MotionEvent;

import com.example.akihiro.schoolwork8.Common.Usuful.Backyard.ValueManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Pad2d;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;
import com.example.akihiro.schoolwork8.GameRenderer;

/**
 * Created by akihiro on 2016/01/19.
 * This class Singleton
 *
 */
class Vector2{
    float x;
    float y;

    Vector2()
    {
        x = 0;
        y = 0;
    }

    Vector2(float x,float y)
    {
        this.x = x;
        this.y = y;
    }
}

public class TouchController {
    //タッチから動くまでの許容範囲 FIXスクリーン座標系でとったほうがいいかも？
    private final float ACCEPTABLE_RANGE_TOUCH = 120.0f;                //タッチから動いた長さ
    private final long  ACCEPTABLE_FAST_TOUCHOUT_TIME = 50;            //フリックの時間(ms)
    private final long  TOUCHWAIT_TIME = ACCEPTABLE_FAST_TOUCHOUT_TIME * 4;    //じっとして別のコマンドを出すためのもの


    private static ValueManage value = ValueManage.GetInstance();
    private static TouchController touchcont = new TouchController();
    private float start_touch_x,start_touch_y;         //タッチした瞬間の座標
    private float move_touch_x,move_touch_y;           //タッチした後から画面から指を話していないときの座標
    private long press_touch_time;                      //タッチしている時間 64biｔ整数
    private boolean touch_flg;                          //タッチしているかどうか
    private boolean wait_touch_flg;                    //タッチして待っている時間
    private Vector2  length;                             //最初の位置からタッチしている量
    private Vector3  tmpvec;
    private float press_power;                          //タッチの強さ

    //FIX::しんぐるとんのシングルトンはできない？
   Pad2d pad = Pad2d.GetInstance();



    //インスタンスを取得
    public static TouchController GetInstance() {
        return touchcont;
    }

    private TouchController()
    {
        this.start_touch_x = 0.0f;
        this.start_touch_y = 0.0f;
        this.move_touch_x = 0.0f;
        this.move_touch_y = 0.0f;
        this.press_touch_time = 0;
        touch_flg = false;
        press_power = 0;
        wait_touch_flg = false;
        length = new Vector2();
        tmpvec = new Vector3();

    }
    //==タッチアクションに関するもの
    public void TouchAction(MotionEvent event)
    {
        //タッチの場所
        float touch_x = event.getX();
        float touch_y = event.getY();
        press_power = event.getPressure();//タッチの強さ獲得
        //なんのアクションか？
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //長さが０にする
                length.x = 0;
                length.y = 0;
                //初期位置を取得
                this.start_touch_x = touch_x;
                this.start_touch_y = touch_y;
                //タッチしたてはmoveも一緒
                this.move_touch_x = touch_x;
                this.move_touch_y = touch_y;
                touch_flg = true;
                press_touch_time = 0;               //押している時間を初期化
                break;
            case MotionEvent.ACTION_MOVE:
                this.move_touch_x = touch_x;
                this.move_touch_y = touch_y;
                //初期のタッチ位置から許容の範囲の外なら値を入れる
                if(CreateLength(this.start_touch_x -touch_x,this.start_touch_y - touch_y) >= ACCEPTABLE_RANGE_TOUCH)
                {

                    //距離を算出する
                    length.x = this.start_touch_x - this.move_touch_x;
                    length.y = this.start_touch_y - this.move_touch_y;
                }
                else
                {
                    length.x = 0.0f;
                    length.y = 0.0f;
                    if(press_touch_time >= TOUCHWAIT_TIME)
                        wait_touch_flg = true;
                }
                this.press_touch_time = event.getEventTime() - event.getDownTime();
                break;
            case MotionEvent.ACTION_UP:
                touch_flg = false;
                wait_touch_flg = false;
                this.press_touch_time = event.getEventTime() - event.getDownTime();
                //長さが０になるので
                //length.x = 0;
                //length.y = 0;
                break;
        }

    }
    //==================取得系統
    //タッチしている時間を見る
    public long GetTouchTime() {
        return this.press_touch_time;
    }

    //スピード
    public boolean SpeedFlick()
    {
        //範囲を超えていて、指定の時間の範囲だったら
        if(CreateLength(length.x,length.y) >= ACCEPTABLE_RANGE_TOUCH * 3
                //時間制限
        && this.press_touch_time >= ACCEPTABLE_FAST_TOUCHOUT_TIME &&
                this.press_touch_time <= TOUCHWAIT_TIME) {
           press_touch_time = 0;//2回目以降同じ処理されないように
            return true;
        }

        return false;
    }
    //初期位置との移動量
    public float GetMoveLength()
    {
        return CreateLength(length.x,length.y);
    }

    //待ち時間が成立しているか？
    public boolean GetTouchWaitFlg()
    {
        return wait_touch_flg;
    }

    private float CreateLength(float x,float y)
    {
        double tmp;             //一時ファイル
        float sprt_length;      //一時ファイル
        tmp = Math.sqrt((double)(x * x + y * y));
        sprt_length = (float)tmp;
        return sprt_length;
    }

    //タッチしているか
    public boolean IsTouch(){
        return this.touch_flg;
    }
    //タッチの強さ
    public float GetPressPower(){
        return this.press_power;
    }

    public Vector3 GetVector3Power()
    {
        tmpvec.x = length.x;
        tmpvec.y = length.y;
        tmpvec.Nomarize();//正規化
        length.x = 0;
        length.y = 0;
        return tmpvec;
    }

    //未実装　タッチによる動作イメージ
    public void TouchInfoDraw()
    {
        if(touch_flg) {
            pad.draw(Pad2d.TEX_INDEX.SPRITE, 30, 30, (int) start_touch_x, (int)(value.screenHeight - (int)start_touch_y));
            pad.draw(Pad2d.TEX_INDEX.SPRITE,100,100,(int)move_touch_x,(int)(value.screenHeight - (int)move_touch_y));
        }

    }

    //デバックによる表示
    public void OnDebagDraw()
    {
        //文字列を描画
        //タッチの位置、タッチの強さ、タイム
    }











}
