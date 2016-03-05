package com.example.akihiro.schoolwork8.Common.Usuful.Controller;

/**
 * Created by akihiro on 2016/01/24.
 * ライトに関するものをいじるやつ
 */
public class LightControll {

    private final static LightControll light = new LightControll();

    // 光源アンビエント（環境光）
    float[] lightAmbient = new float[] { 0.5f, 0.5f, 0.5f, 1.0f };
    // 光源ディフューズ（反射光）
    float[] lightDiffuse = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
    // 光源位置(w=0なので平行光源)
    float[] lightPos = new float[] { 1, 1, -1, 0 };

    public enum LIGHT_TYPE{
        L_POS,      //位置
        L_DIF,      //反射光
        L_AMB,      //環境光
    };



    private LightControll()
    {

    }

    public static LightControll GetInstance()
    {
        return light;
    }

    //値をセットする
    public void SetLightValue(LIGHT_TYPE type,float x,float y,float z,float a)
    {
        switch(type)
        {
            case L_POS:
                lightAmbient[0] = x;
                lightAmbient[1] = y;
                lightAmbient[2] = z;
                lightAmbient[3] = a;
                break;
            case L_DIF:
                lightDiffuse[0] = x;
                lightDiffuse[1] = y;
                lightDiffuse[2] = z;
                lightDiffuse[3] = a;
                break;
            case L_AMB:
                lightPos[0] = x;
                lightPos[1] = y;
                lightPos[2] = z;
                break;
        }
    }



    //位置
    public float[] getLightPos()
    {
        return lightPos;
    }
    //環境光　どこまでも続く光
    public float[] getLightAmbient()
    {
        return lightAmbient;
    }
    //拡散光
    public float[] getLightDiffuse()
    {
        return lightDiffuse;
    }

}
