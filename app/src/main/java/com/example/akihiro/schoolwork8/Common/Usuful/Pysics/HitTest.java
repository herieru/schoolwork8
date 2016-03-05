package com.example.akihiro.schoolwork8.Common.Usuful.Pysics;

import com.example.akihiro.schoolwork8.Common.GameObj.Obj;
import com.example.akihiro.schoolwork8.Common.Usuful.Backyard.MyMath;
import com.example.akihiro.schoolwork8.Common.Usuful.Pysics.HitBox.HitBoxInterface;

/**
 * Created by akihiro on 2016/02/13.
 */
public class HitTest {

    //振り分け用
    public boolean AmangHitType(Obj obj1,Obj obj2)
    {
        switch (obj1.GetHitType())
        {
            case SPHERE:
                switch (obj2.GetHitType())
                {
                    case SPHERE:
                        break;
                    case BOX:
                        break;
                    case OBB:
                        break;
                   default:
                        break;
                }
                break;
            case BOX:
                switch (obj2.GetHitType())
                {
                    case SPHERE:
                        break;
                    case BOX:
                        break;
                    case OBB:
                        break;
                    default:
                        break;
                }
                break;
            case OBB:
                switch (obj2.GetHitType())
                {
                    case SPHERE:
                        break;
                    case BOX:
                        break;
                    case OBB:
                        break;
                    case SPHERE2D:
                        break;
                    case BOX2D:
                        break;
                }
                break;
            case SPHERE2D:
                switch (obj2.GetHitType())
                {
                    case SPHERE2D:
                        break;
                    case BOX2D:
                        break;
                    default:
                        break;
                }
                break;
            case BOX2D:
                switch (obj2.GetHitType())
                {
                    case SPHERE2D:
                        break;
                    case BOX2D:
                        break;
                    default:
                        break;
                }
                break;
        }
        return false;
    }


    private boolean IsBoXHit(Obj obj1,Obj obj2)
    {
        if(obj1.w._pos.x >= 1)
        {

        }

        return false;
    }


    //円形でのヒット
    public boolean IsCircleHit(Obj obj1,Obj obj2)
    {
        //３つのスケールの大きさから一番小さいものをとってくる。
        float o1_sc = Math.min(Math.min(obj1.w._scale.x,obj1.w._scale.y),obj1.w._scale.z);
        float o2_sc = Math.min(Math.min(obj2.w._scale.x,obj2.w._scale.y),obj2.w._scale.z);

        if((obj1.w._pos.x - obj2.w._pos.x) * (obj1.w._pos.x - obj2.w._pos.x) +
             (obj1.w._pos.y - obj2.w._pos.y) * (obj1.w._pos.y - obj2.w._pos.y) +
                (obj1.w._pos.z - obj2.w._pos.z) * (obj1.w._pos.z - obj2.w._pos.z)
                <= (o1_sc + o2_sc)* (o1_sc + o2_sc))//FIX 1R + 2R*2乗
        {
            return true;
        }

        return false;
    }



}
