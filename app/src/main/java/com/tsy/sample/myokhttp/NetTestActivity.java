package com.tsy.sample.myokhttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsy.sample.myokhttp.utils.Md5Utils;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pujiang
 * @date 2017-9-1 13:04
 * @mail 515210530@qq.com
 * @Description:
 */

public class NetTestActivity extends Activity {
    TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);
        tv_info = (TextView)findViewById(R.id.tv_info);
        ((Button)findViewById(R.id.btn_get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPost();
            }
        });
        ((Button)findViewById(R.id.btn_post)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * POST请求
     */
    private void doPost() {
        MyOkHttp mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        String url = "http://192.168.14.10:8082/fnd/projects";
        Map<String, String> params = new HashMap<>();
        params.put("jhVer", "2.0");

        mMyOkhttp.post()
                .url(url)
                .addHeader("loginTerm", "android")
                .addHeader("jhVer", "2.0")
                .addHeader("accessToken", "")
                .addHeader("long","")
                .addHeader("lati","")
                .addHeader("ip","192.168.7.2")
                .addHeader("equipNum","")
                .addHeader("loginChannel","android")
                .params(params)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response, String Header) {
                        tv_info.append("5@------->\nHeader="+Header+"\n"+response);
                    }
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        tv_info.append("1@------->\n"+statusCode+"\n"+response);
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        tv_info.setText("3@------->\n"+statusCode+"\n"+response);
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        tv_info.setText("2@------->\n"+statusCode + "\n" + error_msg);
                    }
                });
    }

    public String savePassword(String password){
        String safePassword="";
        String md5Password= Md5Utils.getMd5Value(password);
        return md5Password;
    }

}
