package com.example.akihiro.schoolwork8.Common.Usuful;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;

import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector3;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Created by ohs30355 on 2016/01/13.
 */
public class Texture2D {

    Context context;
    int tex_width,tex_height;         //テクスチャの横幅と縦幅

    Texture2D(Context context)
    {
        this.context = context;
    }

    //GLとテクスチャID番号が振られる
    public void InitLoad(GL10 gl,int tex_res_id)
    {
        //テクスチャ読み込み
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        //ビットマップリソースをいったんビットマップとして読み込む
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), tex_res_id);
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);              //上下反転
        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
        tex_width = bitmap.getWidth();
        tex_height = bitmap.getHeight();
        //GLテクスチャをビットマップから生成
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        //用済みのビットマップを破棄するように指示
        bitmap.recycle();
        bmp.recycle();
    }

    //テクスチャのインデックス
    public void Draw(GL10 gl,int texture_Index,Vector3 pos)
    {
        //カラー、半透明値設定
        gl.glColor4f(1.0f,1.0f,1.0f,1.0f);//RGBA
        //テクスチャ設定
        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D,texture_Index);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        int[] rect = {0,0,tex_width,tex_height};
        ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES,rect,0);
        //2D描画
        ((GL11Ext)gl).glDrawTexiOES((int)(pos.x - tex_width/2),(int)(pos.y - tex_height/2),0,tex_width,tex_height);

    }

}
