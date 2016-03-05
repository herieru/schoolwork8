package com.example.akihiro.schoolwork8.Common.Usuful;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.GLUtils;

import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Vector4;
import com.example.akihiro.schoolwork8.GameRenderer;
import com.example.akihiro.schoolwork8.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Created by ohs30355 on 2015/12/09.
 */
public class Pad2d {

    Context context;
    GL10 gl;
    private static Pad2d pad = new Pad2d();

    //テクスチャリソースのID
    final int[]   tex_res_id = {
            R.mipmap.ic_launcher,
            R.mipmap.sprite2,
            R.mipmap.damege,
            R.mipmap.zero,
            R.mipmap.iti_off,
            R.mipmap.ni_off,
            R.mipmap.san_off,
            R.mipmap.yon_off,
            R.mipmap.go_off,
            R.mipmap.roku_off,
            R.mipmap.nana_off,
            R.mipmap.hati_off,
            R.mipmap.kyuu_off
    };
    //テクスチャのenum
    public enum  TEX_INDEX{
        IC_LAU(0),
        SPRITE(1),
        SUN(2),
        ZERO(3),
        ITI(4),
        NI(5),
        SAN(6),
        YON(7),
        GO(8),
        ROKU(9),
        NANA(10),
        HATI(11),
        KYUU(12);
        private final int id;
        private TEX_INDEX(final int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    };

    //色情報
    Vector4 color;
    //テクスチャインデックス格納用
    int[] textureIndex  =new int[tex_res_id.length];

    //テクスチャのサイズを格納する構造体
    class TextureSize{
        int texWidth;
        int texHeight;
        TextureSize()
        {
            texWidth = 0;
            texHeight = 0;
        }
    };
    TextureSize[] texture_size = new TextureSize[tex_res_id.length];

    public static Pad2d GetInstance()
    {
        return pad;
    }

    //コンストラクタ
    private Pad2d()
    {
        for(int i = 0; i < tex_res_id.length;i++)
        {
            texture_size[i] = new TextureSize();
        }
    }

    //初期化
    public void Init(GL10 gl,Context context) {
        this.context = context;
        this.gl = gl;
        color = new Vector4(1.0f,1.0f,1.0f,1.0f);
        //テクスチャまとめて読み込み
        //GLテクスチャインデックス値を必要数だけ生成
        gl.glGenTextures(tex_res_id.length, textureIndex, 0);
        for (int i = 0; i < tex_res_id.length; i++) {
            //対象テクスチャインデックスをバインドして初期値を設定
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex[i]);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
            //ビットマップリソースをいったんビットマップとして読み込む
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), tex_res_id[i]);
            Matrix matrix = new Matrix();
           matrix.preScale(1.0f, -1.0f);              //上下反転   画像が反転されるため
            Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
           // Bitmap bitmap2 = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight());
            texture_size[i].texWidth = bitmap.getWidth();
            texture_size[i].texHeight = bitmap.getHeight();
            //GLテクスチャをビットマップから生成
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            //用済みのビットマップを破棄するように指示
            bitmap.recycle();
        }

    }

    //テクスチャNOを指定して、そのテクスチャの大きさを取ってくる
   public void draw(GL10 gl,TEX_INDEX tex_no,int width,int height,int pos_x,int pos_y)
    {
        int tex_id = tex_no.getId();                //表示するテクスチャの番号
        //カラー、半透明値設定
        gl.glColor4f(color.x,color.y,color.z,color.a);//RGBA
        //テクスチャ設定
        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex[tex_id]);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        int[] rect = {0,0,texture_size[tex_id].texWidth,texture_size[tex_id].texHeight};

        ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, rect, 0);
        //2D描画 //Comment 30,30のところが、ポリゴンのサイズになる
        if(CheackSize(width) && CheackSize(height)) {
            //指定したサイズ
            ((GL11Ext) gl).glDrawTexiOES(pos_x - texture_size[tex_id].texWidth / 2, pos_y - texture_size[tex_id].texHeight / 2, 0, width,height);
        }
        else{
            //テクスチャーサイズ
            ((GL11Ext) gl).glDrawTexiOES(pos_x- texture_size[tex_id].texWidth / 2, pos_y - texture_size[tex_id].texHeight / 2, 0,
                    texture_size[tex_id].texWidth,texture_size[tex_id].texHeight );
        }
    }

    //テクスチャなどの大きさも決定できる。
    public void draw(TEX_INDEX tex_no,int width,int height,int pos_x,int pos_y)
    {
        int tex_id = tex_no.getId();                //表示するテクスチャの番号
        //カラー、半透明値設定
        gl.glColor4f(color.x,color.y,color.z,color.a);//RGBA
        //テクスチャ設定
        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex[tex_id]);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        int[] rect = {0,0,texture_size[tex_id].texWidth,texture_size[tex_id].texHeight};

        ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, rect, 0);
        //2D描画 //Comment 30,30のところが、ポリゴンのサイズになる
        if(CheackSize(width) && CheackSize(height)) {
            //指定したサイズ
            ((GL11Ext) gl).glDrawTexiOES(pos_x, pos_y, 0, width,height);
        }
        else{
            //テクスチャーサイズ
            ((GL11Ext) gl).glDrawTexiOES(pos_x- texture_size[tex_id].texWidth / 2, pos_y - texture_size[tex_id].texHeight / 2, 0,
                    texture_size[tex_id].texWidth,texture_size[tex_id].texHeight );
        }
    }



    private boolean CheackSize(int size)
    {
        if(size < 0)
            return false;
        if(size > 2000)         //一番最大枠
            return false;
        return true;
    }



}
