package com.tsy.sample.myokhttp;

import android.app.Activity;
import android.content.Context;
import android.net.TrafficStats;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * @author pujiang
 * @date 2017-8-25 17:37
 * @mail 515210530@qq.com
 * @Description:
 */
public class TestActivity extends Activity {
    int i =1;
    TextView tv_info;
    TelephonyManager telephoneManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_info = (TextView)findViewById(R.id.tv_info);
        telephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephoneManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        type = telephoneManager.getNetworkType();
        ((Button)findViewById(R.id.btn_get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWifiInfo();
            }
        });
        ((Button)findViewById(R.id.btn_txt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Environment.getExternalStorageDirectory()+"/okhttp2/";
                String fileName = "log.txt";
                TxtFileUitls.writeTxtToFile("你好，txt.log", filePath, fileName);
            }
        });
        ((Button)findViewById(R.id.btn_wifi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                WiFiUitls wifiAdmin = new WiFiUitls(TestActivity.this);
                wifiAdmin.openWifi();
                wifiAdmin.startScan();
                List<WifiConfiguration> wifiAdminConfiguration = wifiAdmin.getConfiguration();
                if (wifiAdminConfiguration!=null){
                    for (int i=0;i<wifiAdminConfiguration.size();i++){
                        WifiConfiguration wifiConfiguration = wifiAdminConfiguration.get(i);
                        String wifiInfo = wifiConfiguration.SSID+" "+wifiConfiguration.toString();
                        tv_info.append("index = "+i+" ##wifiInfo="+wifiInfo+"\n "+"\n");
                        tv_info.append("\n\n");
                        tv_info.append("***********************");
                        tv_info.append("\n\n");
                    }
                }
                //连接已配置好的
//                if (i%2==0){
//                    wifiAdmin.connectConfiguration(8);
//                }else {
//                    wifiAdmin.connectConfiguration(11);
//                }
                if (i%2==0){
                    wifiAdmin.connectetworkId(17);
                }else {
                    wifiAdmin.connectetworkId(18);
                }

//                wifiAdmin.startScan();
//                List<ScanResult> list = wifiAdmin.getWifiList();
//                if (list!=null){
//                    for (int i=0;i<list.size();i++){
//                        ScanResult scanResult = list.get(i);
//                        String wifiInfo = scanResult.SSID+" "+scanResult.toString();
//                        tv_info.append("wifiInfo="+wifiInfo);
//                        tv_info.append("\n");
//                    }
//                }

            }
        });

    }

    private void getWifiInfo(){
        /** 获取手机通过 2G/3G 接收的字节流量总数 */
        long getMobileRxBytes = TrafficStats.getMobileRxBytes();
        Log.d("pj","WifiInfo----------->getMobileRxBytes="+getMobileRxBytes+"    ***获取手机通过 2G/3G 接收的字节流量总数");
        /** 获取手机通过 2G/3G 接收的数据包总数 */
        long getMobileRxPackets = TrafficStats.getMobileRxPackets();
        Log.d("pj","WifiInfo----------->getMobileRxPackets="+getMobileRxPackets+"    ***获取手机通过 2G/3G 接收的数据包总数");
        /** 获取手机通过 2G/3G 发出的字节流量总数 */
        long getMobileTxBytes = TrafficStats.getMobileTxBytes();
        Log.d("pj","WifiInfo----------->getMobileTxBytes="+getMobileTxBytes+"    ***获取手机通过 2G/3G 发出的字节流量总数");
        /** 获取手机通过 2G/3G 发出的数据包总数 */
        long getMobileTxPackets = TrafficStats.getMobileTxPackets();
        Log.d("pj","WifiInfo----------->getMobileTxPackets="+getMobileTxPackets+"    ***获取手机通过 2G/3G 发出的数据包总数");
        /** 获取手机通过所有网络方式接收的字节流量总数(包括 wifi) */
        long getTotalRxBytes = TrafficStats.getTotalRxBytes();
        Log.d("pj","WifiInfo----------->getTotalRxBytes="+getTotalRxBytes+"    ***获取手机通过所有网络方式接收的字节流量总数(包括 wifi)");
        /** 获取手机通过所有网络方式接收的数据包总数(包括 wifi) */
        long getTotalRxPackets = TrafficStats.getTotalRxPackets();
        Log.d("pj","WifiInfo----------->getTotalRxPackets="+getTotalRxPackets+"    ***获取手机通过所有网络方式接收的数据包总数(包括 wifi)");
        /** 获取手机通过所有网络方式发送的字节流量总数(包括 wifi) */
        long getTotalTxBytes = TrafficStats.getTotalTxBytes();
        Log.d("pj","WifiInfo----------->getTotalTxBytes="+getTotalTxBytes+"    ***获取手机通过所有网络方式发送的字节流量总数(包括 wifi) ");
        /** 获取手机通过所有网络方式发送的数据包总数(包括 wifi) */
        long getTotalTxPackets = TrafficStats.getTotalTxPackets();
        Log.d("pj","WifiInfo----------->getTotalTxPackets="+getTotalTxPackets+"    ***获取手机通过所有网络方式发送的数据包总数(包括 wifi)");
        /** 获取手机指定 UID 对应的应程序用通过所有网络方式接收的字节流量总数(包括 wifi) */
        //TrafficStats.getUidRxBytes(uid);
        /** 获取手机指定 UID 对应的应用程序通过所有网络方式发送的字节流量总数(包括 wifi) */
        //TrafficStats.getUidTxBytes(uid);
        tv_info.append("接收字节流量总数:"+getTotalRxBytes+"  接收数据包总数:"+getTotalRxPackets+"  发送字节流量总数"+getTotalTxBytes+" 发送数据包总数"+getTotalTxPackets);
        tv_info.append("\n");
    }

    int type = 0;
    PhoneStateListener phoneStateListener = new PhoneStateListener(){
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            StringBuffer sb = new StringBuffer();
            String strength = String.valueOf(signalStrength
                    .getGsmSignalStrength());
            if (type == TelephonyManager.NETWORK_TYPE_UMTS
                    || type == TelephonyManager.NETWORK_TYPE_HSDPA) {
                sb.append("联通3g").append("信号强度:").append(strength);
            } else if (type == TelephonyManager.NETWORK_TYPE_GPRS
                    || type == TelephonyManager.NETWORK_TYPE_EDGE) {
                sb.append("移动或者联通2g").append("信号强度:").append(strength);
            }else if(type==TelephonyManager.NETWORK_TYPE_CDMA){
                sb.append("电信2g").append("信号强度:").append(strength);
            }else if(type==TelephonyManager.NETWORK_TYPE_EVDO_0
                    ||type==TelephonyManager.NETWORK_TYPE_EVDO_A){
                sb.append("电信3g").append("信号强度:").append(strength);

            }else{
                sb.append("非以上信号").append("信号强度:").append(strength);
            }
            tv_info.append(sb.toString());
            tv_info.append("\n");
        }
    };

}
