package com.easyphotos.utils.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.easyphotos.EasyPhotos;
import com.easyphotos.callback.PuzzleCallback;
import com.easyphotos.callback.SelectCallback;
import com.easyphotos.engine.ImageEngine;
import com.easyphotos.models.album.entity.Photo;
import com.easyphotos.ui.EasyPhotosActivity;
import com.easyphotos.ui.PuzzleActivity;
import com.tiktokdemo.lky.tiktokdemo.VideoCropConstant;

import java.util.ArrayList;

/**
 * HolderFragment
 *
 * @author joker
 * @date 2019/4/9.
 */
public class HolderFragment extends Fragment {

    private SelectCallback mSelectCallback;
    private PuzzleCallback mPuzzleCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startEasyPhoto(SelectCallback callback) {
        mSelectCallback = callback;
        EasyPhotosActivity.start(this, VideoCropConstant.HOLDER_SELECT_REQUEST_CODE);
    }

    public void startPuzzleWithPhotos(ArrayList<Photo> photos, String puzzleSaveDirPath, String puzzleSaveNamePrefix, boolean replaceCustom, @NonNull ImageEngine imageEngine, PuzzleCallback callback) {
        mPuzzleCallback = callback;
        PuzzleActivity.startWithPhotos(this, photos, puzzleSaveDirPath, puzzleSaveNamePrefix, VideoCropConstant.HOLDER_PUZZLE_REQUEST_CODE, replaceCustom, imageEngine);
    }

    public void startPuzzleWithPaths(ArrayList<String> paths, String puzzleSaveDirPath, String puzzleSaveNamePrefix, boolean replaceCustom, @NonNull ImageEngine imageEngine, PuzzleCallback callback) {
        mPuzzleCallback = callback;
        PuzzleActivity.startWithPaths(this, paths, puzzleSaveDirPath, puzzleSaveNamePrefix, VideoCropConstant.HOLDER_PUZZLE_REQUEST_CODE, replaceCustom, imageEngine);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case VideoCropConstant.HOLDER_SELECT_REQUEST_CODE:
                    if (mSelectCallback != null) {
                        ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                        ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                        boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
                        mSelectCallback.onResult(resultPhotos, resultPaths, selectedOriginal);
                    }
                    break;
                case VideoCropConstant.HOLDER_PUZZLE_REQUEST_CODE:
                    if (mPuzzleCallback != null) {
                        Photo puzzlePhoto = data.getParcelableExtra(EasyPhotos.RESULT_PHOTOS);
                        String puzzlePath = data.getStringExtra(EasyPhotos.RESULT_PATHS);
                        mPuzzleCallback.onResult(puzzlePhoto, puzzlePath);
                    }
                    break;
                default:
                    Log.e("EasyPhotos", "requestCode error : " + requestCode);
                    break;
            }
        } else {
            Log.e("EasyPhotos", "resultCode is not RESULT_OK: " + resultCode);
        }
    }
}
