package com.zlb.memo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.vondear.rxtools.RxFileTool;
import com.vondear.rxtools.view.RxToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RxPhotoTool {
    public static final int GET_IMAGE_BY_CAMERA = 5001;
    public static final int GET_IMAGE_FROM_PHONE = 5002;
    public static final int CROP_IMAGE = 5003;
    public static Uri imageUriFromCamera;
    public static Uri cropImageUri;

    public RxPhotoTool() {
    }

    public static void openCameraImage(Activity activity) {
        imageUriFromCamera = createImagePathUri(activity);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", imageUriFromCamera);
        activity.startActivityForResult(intent, 5001);
    }

    public static void openCameraImage(Fragment fragment) {
        imageUriFromCamera = createImagePathUri(fragment.getContext());
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", imageUriFromCamera);
        fragment.startActivityForResult(intent, 5001);
    }

    public static void openLocalImage(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, 5002);
    }

    public static void openLocalImage(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, 5002);
    }

    public static void cropImage(Activity activity, Uri srcUri) {
        cropImageUri = createImagePathUri(activity);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("output", cropImageUri);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, 5003);
    }

    public static void cropImage(Fragment fragment, Uri srcUri) {
        cropImageUri = createImagePathUri(fragment.getContext());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("output", cropImageUri);
        intent.putExtra("return-data", true);
        fragment.startActivityForResult(intent, 5003);
    }

    public static Uri createImagePathUri(Context context) {
        Uri[] imageFilePath = new Uri[]{null};
        if (ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
            imageFilePath[0] = Uri.parse("");
            RxToast.error("请先获取写入SDCard权限");
        } else {
            String status = Environment.getExternalStorageState();
            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
            long time = System.currentTimeMillis();
            String imageName = timeFormatter.format(new Date(time));
            ContentValues values = new ContentValues(3);
            values.put("_display_name", imageName);
            values.put("datetaken", Long.valueOf(time));
            values.put("mime_type", "image/jpeg");
            if (status.equals("mounted")) {
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
            }
        }

        Log.i("", "生成的照片输出路径：" + imageFilePath[0].toString());
        return imageFilePath[0];
    }

    public static String getRealFilePath(Context context, Uri uri) {
        if (null == uri) {
            return null;
        } else {
            String scheme = uri.getScheme();
            String data = null;
            if (scheme == null) {
                data = uri.getPath();
            } else if ("file".equals(scheme)) {
                data = uri.getPath();
            } else if ("content".equals(scheme)) {
                String[] projection = new String[]{"_data"};
                Cursor cursor = context.getContentResolver().query(uri, projection, (String) null, (String[]) null, (String) null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex("_data");
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }

                    cursor.close();
                }
            }

            return data;
        }
    }

    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context != null && imageUri != null) {
            if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, imageUri)) {
                String docId;
                String[] split;
                String type;
                if (RxFileTool.isExternalStorageDocument(imageUri)) {
                    docId = DocumentsContract.getDocumentId(imageUri);
                    split = docId.split(":");
                    type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else {
                    if (RxFileTool.isDownloadsDocument(imageUri)) {
                        docId = DocumentsContract.getDocumentId(imageUri);
                        Uri split1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId).longValue());
                        return RxFileTool.getDataColumn(context, split1, (String) null, (String[]) null);
                    }

                    if (RxFileTool.isMediaDocument(imageUri)) {
                        docId = DocumentsContract.getDocumentId(imageUri);
                        split = docId.split(":");
                        type = split[0];
                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        String selection = "_id=?";
                        String[] selectionArgs = new String[]{split[1]};
                        return RxFileTool.getDataColumn(context, contentUri, selection, selectionArgs);
                    }
                }
            } else {
                if ("content".equalsIgnoreCase(imageUri.getScheme())) {
                    if (RxFileTool.isGooglePhotosUri(imageUri)) {
                        return imageUri.getLastPathSegment();
                    }

                    return RxFileTool.getDataColumn(context, imageUri, (String) null, (String[]) null);
                }

                if ("file".equalsIgnoreCase(imageUri.getScheme())) {
                    return imageUri.getPath();
                }
            }

            return null;
        } else {
            return null;
        }
    }
}

