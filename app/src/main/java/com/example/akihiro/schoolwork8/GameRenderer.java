package com.example.akihiro.schoolwork8;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.example.akihiro.schoolwork8.Common.Usuful.Backyard.ValueManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.LightControll;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.ModelManage;
import com.example.akihiro.schoolwork8.Common.Usuful.Pad2d;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.TouchController;
import com.example.akihiro.schoolwork8.Common.Usuful.Controller.CameraControll;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.Shadow;
import com.example.akihiro.schoolwork8.Common.Usuful.SoundSe;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/01/19.
 */
public class GameRenderer implements GLSurfaceView.Renderer {
    // コンテキスト
    Context context;
    //タッチ状態
    int touchState;
    float touchX, touchY;
    //スクリーンの縦横
    public int screenWidth;
    public int screenHeight;

    public LightControll lir_cont;

    //２Dモデル
    Pad2d pad2d;

    ModelManage model = ModelManage.GetInstance();

    public GameManager manage;
    public TouchController touch;
    public CameraControll camera;
    public ValueManage value;
    public Shadow shadow;
    public SoundSe se;



    boolean flg = false;

    // コンストラクタ
    public GameRenderer(Context context) {
        manage = new GameManager(context);
        touch = TouchController.GetInstance();
        camera = CameraControll.GetInstance();
        shadow = Shadow.GetInstance();
        this.context = context;
        pad2d = Pad2d.GetInstance();
        lir_cont = LightControll.GetInstance();
        value = ValueManage.GetInstance();
        value.context = context;
        se = SoundSe.GetInstance();
        se.Init(context);
    }

    public void Update()
    {
       manage.Update();
        /*
        if(touch.IsTouch())
        {
            lir_cont.SetLightValue(LightControll.LIGHT_TYPE.L_AMB,0.0f,0.0f,1.0f,1.0f);
            lir_cont.SetLightValue(LightControll.LIGHT_TYPE.L_DIF,0.0f,0.0f,1.0f,1.0f);
        }
        else
        {
            lir_cont.SetLightValue(LightControll.LIGHT_TYPE.L_AMB,0.5f,0.5f,0.5f,1.0f);
            lir_cont.SetLightValue(LightControll.LIGHT_TYPE.L_DIF,1.0f,1.0f,1.0f,1.0f);
        }
        */
    }

    // 画面を描画するときに呼び出される
    public void onDrawFrame(GL10 gl) {
        Update();
        // 画面クリア
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // カメラの設定
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,
                camera.pos.x, camera.pos.y, camera.pos.z,           //position
                camera.look.x, camera.look.y, camera.look.z,        //look
                camera.up.x, camera.up.y, camera.up.z);               //upvec
        // 光源を設定
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lir_cont.getLightPos(), 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lir_cont.getLightAmbient(), 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lir_cont.getLightDiffuse(), 0);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        manage.onDraw(gl);
        //2dモデルを処理、描画
        //pad2d.draw(gl, Pad2d.TEX_INDEX.SYSFONT,200,200,300,300);
       //pad2d.drawFont(gl, Pad2d.TEX_INDEX.MARUBATU,300,300,250,250,0,0);
    }

    // サーフェスが生成された
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //デプステストの有効化
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //入力された深さ値が、格納されている深さ値以下の場合に成功する。
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        // パースペクティブコレクト
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        // 線形補間
        gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
        //カリング
        gl.glEnable(GL10.GL_CULL_FACE); //片面表示有効
        /*
        GL_FRONT_AND_BACK...どっちも描画しない
        GL_FRONT...裏面描画
        GL_BACK...表面描画
         */
        gl.glCullFace(GL10.GL_BACK);

        float[] floorPos    ={0.0f,0.01f,0.0f};    //床の位置
        float[] floorNormal ={0.0f,-1.0f,0.0f};    //床の法線の逆

        //影行列作成   光源位置、床位置、床の法線の逆
       shadow.CreateShadowMatrix(lir_cont.getLightPos(),floorPos,floorNormal);

        // 画面クリア色設定
        gl.glClearColor(1.0f, 1.0f, 0.83f, 1.0f);

        // テクスチャ有効
        gl.glEnable(GL10.GL_TEXTURE_2D);

        // アルファブレンド有効
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_BLEND);

        // モデルの初期化
        manage.Init(gl);
        pad2d.Init(gl, context);
        value.context = context;
        value.gl = gl;
    }

    // サーフェスが変更された
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // ビューポート設定
        gl.glViewport(0, 0, width, height);
        //画面サイズを記憶
        screenWidth = width;
        screenHeight = height;

        value.gl = gl;
        value.screenWidth = width;
        value.screenHeight = height;

        // プロジェクションマトリクス設定
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / height, 1.0f, 50.0f);
    }

    // タッチイベント
    public void onTouch(MotionEvent event) {
        touch.TouchAction(event);
    }
}
