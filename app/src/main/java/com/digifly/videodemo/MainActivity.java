package com.digifly.videodemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity  implements MediaPlayer.OnPreparedListener , MediaPlayer.OnCompletionListener{
    private static final String TAG = "PlayVideo2Activity";
    private static final int REQUEST_CODE = 1;

    private VideoView vv;
    private MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.vv = (VideoView) this.findViewById(R.id.vv);

        // 設定影片控制面板
        this.mc = new MediaController(this);
        this.vv.setMediaController(this.mc);

        this.vv.setOnPreparedListener(this);
        this.vv.setOnCompletionListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "取得影片路徑，立即播放");
        // 若用傳進來的 MediaPlayer 播放，沒有控制面板
        // mp.start();
        vv.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        vv.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent it) {
        super.onActivityResult(requestCode, resultCode, it);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "選檔失敗");
            return;
        }
        if (requestCode != REQUEST_CODE) {
            Log.d(TAG, "不是選檔");
            return;
        }
        Uri uri = it.getData();
        Log.d(TAG, "取得影片：" + uri.getPath());
        Log.d(TAG, "將影片路徑傳給 VideoView");
        this.vv.setVideoURI(uri);
    }

    public void onClick(View v) {
        // 挑選影片
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("video/*");
//        this.startActivityForResult(it, REQUEST_CODE);
        vv.setVisibility(View.VISIBLE);

        this.vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.samplevideo));


    }
}
