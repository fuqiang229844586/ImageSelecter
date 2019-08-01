package com.media.library.gallery;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.easyphotos.EasyPhotos;
import com.easyphotos.callback.SelectCallback;
import com.easyphotos.constant.Type;
import com.easyphotos.models.album.entity.Photo;
import com.media.library.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private RxPermissions mRxPermissions;

    /**
     * 选择的媒体集
     */
    private ArrayList<Photo> selectedPhotoList = new ArrayList<>();
    /**
     * 选择的媒体路径集
     */
    private ArrayList<String> selectedPhotoPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRxPermissions = new RxPermissions(this);
    }


    public void openCamera(View view) {
        mRxPermissions.request(permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) { //已获取权限
                            EasyPhotos.createAlbum(MainActivity.this, true, GlideEngine.getInstance())
                                    .setSelectMutualExclusion(true) //选择结果互斥（不能同时选择图片或视频）
                                    .setVideoCount(1)
                                    .setPictureCount(9)
                                    .setVideoMinSecond(10)
                                    .filter(Type.all())
                                    .setMaxRecordDuration(25000)
                                    .setMinRecordDuration(10000)
                                    .setCleanMenu(true)
                                    .setGif(true)
                                    .start(callback);
                        } else {
                            Toast.makeText(MainActivity.this, "给点权限行不行？", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 媒体选择回调
     */
    private SelectCallback callback = new SelectCallback() {
        @Override
        public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
            selectedPhotoList.clear();
            selectedPhotoList.addAll(photos);
            selectedPhotoPathList.clear();
            selectedPhotoPathList.addAll(paths);
            for (Photo pic : photos) {
                System.out.println("选择的地址是=====================" + pic.path);
            }
        }
    };
}
