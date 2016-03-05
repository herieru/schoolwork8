package com.example.akihiro.schoolwork8;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.akihiro.schoolwork8.GameRenderer;

public class MainActivity extends Activity {
    GLSurfaceView glSurfaceView;
    GameRenderer gameRenderer;

    // アクティビティが生成された
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // タイトルバー無し
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // フルスクリーン
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // GameRendererを作成
        gameRenderer = new GameRenderer(this);

        // GLSurfaceViewを作成
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLConfigChooser(8,8,8,8,16,0);
        glSurfaceView.setRenderer(gameRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(glSurfaceView);
    }

    // アクティビティがアクティブになった
    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    // アクティビティがアクティブでなくなった
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    // 画面にタッチ操作を行った
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gameRenderer != null) {
            // GameRenderer内のタッチイベントハンドラを実行
            gameRenderer.onTouch(event);
        }
        return true;
    }
}