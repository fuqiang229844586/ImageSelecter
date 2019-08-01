package com.tiktokdemo.lky.tiktokdemo;

import android.os.Environment;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by lky on 2018/12/11
 */
public class VideoCropConstant {

    /**
     * 视频的总长度
     */
    public static final int VIDEO_LENGTH = 180;
    public static final String APP_LOCAL_DIR = File.separator + "社区人";
    public static final int HOLDER_SELECT_REQUEST_CODE = 0x44;
    public static final int HOLDER_PUZZLE_REQUEST_CODE = 0x55;
    public static final int HOLDER_CROP_REQUEST_CODE = 0x56;
    public static final String PIC_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_LOCAL_DIR;
    public static final String DIC_FILE = BaseApplication.mContext.getCacheDir().getAbsolutePath() + APP_LOCAL_DIR;

    public static final String RECORD_AUDIO_CACHE_PATH_TEMP = DIC_FILE + File.separator + "record_audio_cache";
    public static final String IMAGE_SAVE_PATH = PIC_FILE + File.separator + "image";
    public static final String RECORD_VIDEO_PATH_TEMP = DIC_FILE + File.separator + "record_video";
    public static final String RECORD_VIDEO_PATH = PIC_FILE + File.separator + "record_video";
    public static final String CUT_AUDIO_CACHE_PATH = PIC_FILE + File.separator + "cut";
    public static final String RECORD_CROP_PHOTO_CACHE_PATH = DIC_FILE + File.separator + "record_crop";
    public static final String RECORD_VIDEO_TEMP_PATH = PIC_FILE + File.separator + "record_video_temp";
    public static final String SPEED_AUDIO_CACHE_PATH = DIC_FILE + File.separator + "speed_audio";

    /**
     * 是否视频
     * @param file
     * @return
     */
    public static boolean isVideo(File file){
        String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
        Pattern p = Pattern.compile(reg);
        return p.matcher(file.getName()).find();
    }
}
