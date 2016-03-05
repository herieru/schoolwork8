package com.example.akihiro.schoolwork8.Common.Usuful.Pysics.HitBox;

/**
 * Created by akihiro on 2016/02/15.
 */
public class HitBoxInterface {


    public float xr,yr,zr;//中心位置、半径(どれくらいか？)

    public enum HIT_TYPE
    {
        SPHERE(1),          //３D円
        BOX(2),             //3Dボックス
        OBB(3),            //３DOBB
        SPHERE2D(4),        //2D円
        BOX2D(5);           //２D□
        private final int id;
        private HIT_TYPE(final int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }
    }



    protected HIT_TYPE type;      //当たりの範囲
    //タイプとその半径および大きさ
    public HitBoxInterface(HIT_TYPE types,float rx,float ry,float rz)
    {
        this.type = types;
        if(types == HIT_TYPE.SPHERE ||types == HIT_TYPE.SPHERE2D)
        {
            xr = yr = zr = rx;
        }
        else
        {
            xr = rx;
            yr = ry;
            zr = rz;
        }
    }

    //ヒットの範囲
    public HIT_TYPE GetHitInfo()
    {
        return type;
    }








}
