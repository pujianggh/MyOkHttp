package com.tsy.sample.myokhttp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.DownloadResponseHandler;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * myokhttp 请求代码 仅供参考 不可运行
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private MyOkHttp mMyOkhttp;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_post_json).setOnClickListener(this);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_put).setOnClickListener(this);
        findViewById(R.id.btn_patch).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_upload).setOnClickListener(this);
        findViewById(R.id.btn_download).setOnClickListener(this);
        findViewById(R.id.btn_download_mgr).setOnClickListener(this);
        findViewById(R.id.btn_cookie).setOnClickListener(this);
        tv_show= (TextView)findViewById(R.id.tv_show);

        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }

    @Override
    protected void onDestroy() {
        mMyOkhttp.cancel(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post:
                doPost();
                break;

            case R.id.btn_post_json:
                startActivity(new Intent(this,NetTestActivity.class));

//                String value = ChannelNameUitls.getChannelNameActivity(this,"com.pj.key");
//                String value2 = ChannelNameUitls.getChannelNameApplication(this,"key_channel");
//                Log.d(TAG, "ChannelNameUitls---->" + value+"/"+value2);
//                if (!TextUtils.isEmpty(value)){
//                    tv_show.setText(value);
//                }
//                if (!TextUtils.isEmpty(value2)){
//                    tv_show.append(value2);
//                }
//                Log.d(TAG, "ChannelNameUitls---->" );
//                startActivity(new Intent(this,TestActivity.class));
                //doPostJSON();
                break;

            case R.id.btn_get:
                doGet();
                break;

            case R.id.btn_put:
                doPut();
                break;

            case R.id.btn_patch:
                doPatch();
                break;

            case R.id.btn_delete:
                doDelete();
                break;

            case R.id.btn_upload:
                doUpload();
                break;

            case R.id.btn_download:
                doDownload();
                break;

            case R.id.btn_download_mgr:
                startActivity(new Intent(this, DownloadMgrActivity.class));
                break;

            case R.id.btn_cookie:
                doCookie();
                break;
        }
    }

    /**
     * POST请求 + Json返回
     */
    private void doPost() {
        String url = "https://m.flyfnd.com/fnd/channels";
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("size", "10");
        params.put("isRecom","");

        mMyOkhttp.post()
                .url(url)
                .params(params)
                .tag(this)
                .enqueue(new GsonResponseHandler<UploadModel>() {
            @Override
            public void onSuccess(int statusCode, UploadModel response) {
                tv_show.setText(response.getMsg()+"\t"+response.getCode());
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

        mMyOkhttp.post()
                .url(url)
                .params(params)
                .tag(this)
                .enqueue(new JsonResponseHandler() {

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        Log.d(TAG, "doPost onProgress currentBytes:" + currentBytes);
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        tv_show.setText(response+"");
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                    }
                });
    }

    /**
     * POST请求Json参数 + Json返回
     */
    private void doPostJSON() {
        String url = "https://m.flyfnd.com/fnd/channels";

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", "tsy");
            jsonObject.put("age", 24);
            jsonObject.put("type", "json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(url)
                .jsonParams(jsonObject.toString())          //与params不共存 以jsonParams优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPostJSON onSuccess JSONObject:" + response);
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPostJSON onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPostJSON onFailure:" + error_msg);
                    }
                });
    }

    /**
     * GET请求 + Raw String返回
     */
    private void doGet() {
        String url = "http://192.168.2.135/myokhttp/get.php";

        mMyOkhttp.get()
                .url(url)
                .addParam("name", "tsy")
                .addParam("id", "5")
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.d(TAG, "doGet onSuccess:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doGet onFailure:" + error_msg);
                    }
                });
    }

    /**
     * PUT请求 + Json返回
     */
    private void doPut() {
        String url = "http://192.168.2.135/myokhttp/put.php/id/5/name/tsy/age/15";

        mMyOkhttp.put()
                .url(url)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPut onSuccess:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPut onFailure:" + error_msg);
                    }
                });
    }

    /**
     * PATCH请求 + Json返回
     */
    private void doPatch() {
        String url = "http://192.168.2.135/myokhttp/patch.php/id/5/name/tsy";

        mMyOkhttp.patch()
                .url(url)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPatch onSuccess:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPatch onFailure:" + error_msg);
                    }
                });
    }

    /**
     * DELETE请求 + Json返回
     */
    private void doDelete() {
        String url = "http://192.168.2.135/myokhttp/delete.php/id/5";

        mMyOkhttp.delete()
                .url(url)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doDelete onSuccess:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doDelete onFailure:" + error_msg);
                    }
                });
    }

    /**
     * UPLOAD + Gson返回
     */
    private void doUpload() {
        String url = "http://192.168.2.135/myokhttp/upload.php";

        mMyOkhttp.upload()
                .url(url)
                .addParam("name", "tsy")
                .addFile("avatar",
                        new File(Environment.getExternalStorageDirectory()
                                + "/ahome/sasas.jpg"))        //上传已经存在的File
//                .addFile("avatar2", "asdsda.png", byteContents)    //直接上传File bytes
                .tag(this)
                .enqueue(new GsonResponseHandler<UploadModel>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doUpload onFailure:" + error_msg);
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        Log.d(TAG, "doUpload onProgress:" + currentBytes + "/" + totalBytes);
                    }

                    @Override
                    public void onSuccess(int statusCode, UploadModel response) {
                        Log.d(TAG, "doUpload onSuccess:" + response.ret + " " + response.msg);
                    }
                });
    }

    /**
     * Download
     */
    private void doDownload() {
        String url = "https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=ca5abb5b7bf0f736ccf344536b3cd87c/29381f30e924b899c83ff41c6d061d950a7bf697.jpg";

        mMyOkhttp.download()
                .url(url)
                .filePath(Environment.getExternalStorageDirectory() + "/okhttp/test.jpg")
                .tag(this)
                .enqueue(new DownloadResponseHandler() {
                    @Override
                    public void onStart(long totalBytes) {
                        Log.d(TAG, "doDownload onStart");
                    }

                    @Override
                    public void onFinish(File downloadFile) {
                        Log.d(TAG, "doDownload onFinish:");
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {
                        Log.d(TAG, "doDownload onProgress:" + currentBytes + "/" + totalBytes);
                    }

                    @Override
                    public void onFailure(String error_msg) {
                        Log.d(TAG, "doDownload onFailure:" + error_msg);
                    }
                });
    }

    /**
     * cookie测试
     *
     * php代码：
     * if(empty($_COOKIE['mycookie'])) {
     *      setcookie('mycookie','value', time()+20);
     *      die("no cookie 'mycookie', set 'mycookie' => 'value'");
     * }
     * die("has cookie 'mycookie' => " . $_COOKIE['mycookie']);
     *
     */
    private void doCookie() {
        String url = "http://192.168.2.135/myokhttp/cookie.php";

        mMyOkhttp.post()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.d(TAG, "doCookie onSuccess:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doCookie onFailure:" + error_msg);
                    }
                });
    }
}
