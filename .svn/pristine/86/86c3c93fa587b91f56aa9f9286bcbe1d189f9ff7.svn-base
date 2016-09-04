package cn.nj.www.my_module.tools;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import de.greenrobot.event.EventBus;

/**
 * 实现实位回调监听
 */
public class MyLocationListener implements BDLocationListener
{

    public void onReceiveLocation(BDLocation location) {
        //Receive Location
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());//获得当前时间
        sb.append("\nerror code : ");
        sb.append(location.getLocType());//获得erro code得知定位现状
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());//获得纬度
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());//获得经度
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {//通过GPS定位
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());//获得速度
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\ndirection : ");
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());//获得当前地址
            sb.append(location.getDirection());//获得方位
            sb.append(location.getCity());//获得方位
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//通过网络连接定位
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());//获得当前地址
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());//获得经营商？
            sb.append(location.getCity());//获得方位
        }
        if (GeneralUtils.isNotNullOrZeroLenght(location.getCity())) {
            Global.saveCity(location.getCity());
            Global.saveDistrict(location.getDistrict());
            EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOCATION_CITY_SUCCESS));
        }
        Global.savelangitude(location.getLongitude() + "");//经度
        Global.savelatitude(location.getLatitude() + "");//纬度
        //发个通知
        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOCATION_SUCCESS));
        CMLog.e(Constants.HTTP_TAG, sb.toString());
        CMLog.e(Constants.HTTP_TAG, sb.toString());
        CMLog.e(Constants.HTTP_TAG, sb.toString());
    }
}
