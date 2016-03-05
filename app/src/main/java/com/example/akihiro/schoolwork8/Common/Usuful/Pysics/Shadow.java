package com.example.akihiro.schoolwork8.Common.Usuful.Pysics;

import com.example.akihiro.schoolwork8.Common.Usuful.Controller.ModelManage;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akihiro on 2016/02/07.
 */
public class Shadow {

    private static Shadow shadow = new Shadow();
    private float[] m=new float[16];        //影行列
    ModelManage model = ModelManage.GetInstance();

    GLWorld world;


    private Shadow(){
        world = new GLWorld();
        model = ModelManage.GetInstance();
    }

    public static Shadow GetInstance()
    {
        return shadow;
    }


    //影行列作成
    public void CreateShadowMatrix(float[] l,float[] g,float[] n)
    {
        float d=(n[0]*l[0])+(n[1]*l[1])+(n[2]*l[2]);
        float c=(g[0]*n[0])+(g[1]*n[1])+(g[2]*n[2])-d;
        m[0]=l[0]*n[0]+c;     m[1]=l[1]*n[0];       m[2]=l[2]*n[0];       m[3]=n[0];
        m[4]=l[0]*n[1];       m[5]=l[1]*n[1]+c;     m[6]=l[2]*n[1];       m[7]=n[1];
        m[8]=l[0]*n[2];       m[9]=l[1]*n[2];       m[10]=l[2]*n[2]+c;    m[11]=n[2];
        m[12]=-l[0]*c-l[0]*d; m[13]=-l[1]*c-l[1]*d; m[14]=-l[2]*c-l[2]*d; m[15]=-d;
    }

    public void DrawModelShadow(GL10 gl,int model_no)
    {
        gl.glColor4f(0,0,0,1);
        gl.glPushMatrix();
        gl.glMultMatrixf(m,0);
        model.Draw(gl,model_no);
        gl.glPopMatrix();
    }
}
