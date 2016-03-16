package com.custom.dashboardview.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by dev on 14/12/21.
 */
public class Util {
    public static final String DAY_NIGHT_MODE = "daynightmode";
    public static final String DEVIATION = "deviationrecalculation";
    public static final String JAM = "jamrecalculation";
    public static final String TRAFFIC = "trafficbroadcast";
    public static final String CAMERA = "camerabroadcast";
    public static final String SCREEN = "screenon";
    public static final String THEME = "theme";
    public static final String ISEMULATOR = "isemulator";


    public static final String ACTIVITYINDEX = "activityindex";

    public static final int SIMPLEHUDNAVIE = 0;
    public static final int EMULATORNAVI = 1;
    public static final int SIMPLEGPSNAVI = 2;
    public static final int SIMPLEROUTENAVI = 3;


    public static final boolean DAY_MODE = false;
    public static final boolean NIGHT_MODE = true;
    public static final boolean YES_MODE = true;
    public static final boolean NO_MODE = false;
    public static final boolean OPEN_MODE = true;
    public static final boolean CLOSE_MODE = false;

    Context context;

    public Util(Context context) {
        this.context = context;
    }

    //检查是否支持蓝牙
    public static boolean checkBluetooth(Context context){
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            return false;
        }else {
            return true;
        }
    }

    //关闭软键盘
    public static boolean closeInputMethod(Activity context) {
        boolean hasFocus = context.getCurrentFocus() != null;
        if (hasFocus) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow
                    (context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return hasFocus;
    }

    public static float curve(float start) {
        double angle = start * Math.PI / 2;
        double ret = Math.pow(Math.sin(angle), 0.3);
        //   if(start<max/2){
        //      return (float)ret;

        //     ret+=Math.sin(10*Math.PI*start/max)*(max-start)/max*0.2;
        return (float) ret;
    }

    class DX {
        double x;
        double y;
    }


    private DX bs(int index, int sum, DX dx, float x) {
        DX re = new DX();
        re.x = dx.x * Math.pow(1 - x, sum - index) * Math.pow(x, index);

        return null;
    }

    private float pl(float x, float tot) {
        if (x == 0) return 1;
        float sum = 1f;
        for (; x <= tot; x++) {

        }
        return 1;

    }


    // model  属性复制
    public static void modelCopy(Object target, Object resource) {
        if (target == null || resource == null) {
            return;
        }

        Class tarClass = target.getClass();
        Class tarSupClass = tarClass.getSuperclass();
        Class resClass = resource.getClass();
        Class resSupClass = resClass.getSuperclass();

        Field[] tarFields = tarClass.getDeclaredFields();
        Field[] tarSupFields = tarSupClass.getDeclaredFields();

        Field[] resFields = resClass.getDeclaredFields();
        Field[] resSupFields = resSupClass.getFields();

        for (Field i : tarFields) {
            String tarName = i.getName();
            for (Field j : resFields) {
                if (tarName.equals(j.getName())) {
                    try {
                        Object res = j.get(resource);
                        i.set(target, res);
                    } catch (Exception e) {
                    }
                    break;
                }
            }

            for (Field j : resSupFields) {
                if (tarName.equals(j.getName())) {
                    try {
                        Object res = j.get(resource);
                        i.set(target, res);
                    } catch (Exception e) {
                    }
                    break;
                }
            }
        }

        for (Field i : tarSupFields) {
            String tarName = i.getName();
            for (Field j : resFields) {
                if (tarName.equals(j.getName())) {
                    try {
                        Object res = j.get(resource);
                        i.set(target, res);
                    } catch (Exception e) {
                    }
                    break;
                }
            }

            for (Field j : resSupFields) {
                if (tarName.equals(j.getName())) {
                    try {
                        Object res = j.get(resource);
                        i.set(target, res);
                    } catch (Exception e) {
                    }
                    break;
                }
            }
        }
    }

    public static String timeFormat(long time, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatYearDate(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatWeek(long time) {
        DateFormat formatter = new SimpleDateFormat("EEEE");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatNormal(long time) {
        DateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatDate(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormat(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatMin(long time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatSecond(long time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatMinSecond(long time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatDay(long time) {
        DateFormat formatter = new SimpleDateFormat("dd");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatMonth(long time) {
        DateFormat formatter = new SimpleDateFormat("MM");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatOnlySecond(long time) {
        DateFormat formatter = new SimpleDateFormat("ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatYear(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatYearDateCh(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String formatTime(long duration) {
        long sec = duration % 60;
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60);
        if (hour > 0) {
            return String.format("%d:%d:%d", hour, min, sec);
        }
        if (min > 0) {
            return min + " min";
        }
        if (sec > 0) {
            return sec + " sec";
        }
        return "0";
    }

    public static String formatTimeDuration(long duration) {
        long sec = duration % 60;
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60) % 24;
//        if (hour > 0) {
//            return String.format("%d:%d:%d", hour, min, sec);
//        }
//        if (min > 0) {
//            return min + " min";
//        }
//        if (sec > 0) {
//            return sec + " sec";
//        }
        if (duration > 0){
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
        return "0";
    }

    public static String formatTimeDurationMin(long duration) {
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60) % 24;
        if (hour > 0) {
            return String.format("%02d:%02d", hour, min);
        }
        if (min > 0) {
            return String.format("%02d:%02d", 0, min);
        }
        return "00:00";
    }

    public static String formatTimeDurationSec(long duration) {
        long sec = duration % 60;
        if (sec > 0) {
            return String.format(":%02d", sec);
        }
        return ":00";
    }

    public static String timeDifference(long startTime) {
//        long endTime = System.currentTimeMillis() / 1000;
//        System.out.println("======+++++-------" + System.currentTimeMillis());

        long timeLong = System.currentTimeMillis() / 1000 - startTime;
        if (timeLong < 60)
            return timeLong + "秒前";
        else if (timeLong < 60 * 60) {
            timeLong = timeLong / 60;
            return timeLong + "分钟前";
        } else if (timeLong < 60 * 60 * 24) {
            timeLong = timeLong / 60 / 60;
            return timeLong + "小时前";
        } else if (timeLong < 60 * 60 * 24 * 7) {
            timeLong = timeLong / 60 / 60 / 24;
            return timeLong + "天前";
        } else if (timeLong < 60 * 60 * 24 * 7 * 4) {
            timeLong = timeLong / 60 / 60 / 24 / 7;
            return timeLong + "周前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return sdf.format(startTime * 1000);
        }
    }

    public static String mConverkm(float m) {
        float distanceValue = Math.round((m / 10f)) / 100f;
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(distanceValue);
    }

    public static int getDisplayMetricsWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        return screenWidth;
    }

    public static long getCurrentTimeStamp() {
        long currentTimeMillis = System.currentTimeMillis() / 1000;

        return currentTimeMillis;
    }

    public static long timeGet(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        try {
            date = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long time() {
        return System.currentTimeMillis() / 1000;
    }

    public static long mtime() {
        return System.currentTimeMillis();
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));

        }

        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        byte data[] = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            data[i / 2] = (Integer.decode("0x" + s.charAt(i) + s.charAt(i + 1))).byteValue();
        }
        return data;
    }

    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    public static Typeface TypeFace(Context context, String type) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), type);
        } catch (Exception e) {

        }
        return typeface;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value = b[i];
            value = value << 8;
        }
        return value;
    }

    public static double distanceBetweenGeoPoints(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }


    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void changeFonts(ViewGroup root, Activity act) {

        Typeface tf = Typeface.createFromAsset(act.getAssets(),
                "DINPro-Regular.ttf");

        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, act);
            }
        }
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //16进制字符串转2进制
    public static String hexStringToBinary(String hexString) {
        long l = Long.parseLong(hexString, 16);
        String binaryString = Long.toBinaryString(l);
        int shouldBinaryLen = hexString.length() * 4;
        StringBuffer addZero = new StringBuffer();
        int addZeroNum = shouldBinaryLen - binaryString.length();
        for (int i = 1; i <= addZeroNum; i++) {
            addZero.append("0");
        }
        return addZero.toString() + binaryString;
    }

    /**
     * @Description Float类型的小数点
     * @param newScale
     * @param value
     * @return
     */
    public static Float floatRound(Float value, Integer newScale) {
        BigDecimal bd = new BigDecimal(value);
        value = bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).floatValue();
        return value;
    }

    //手机是否有SD卡
    public static String isSDcard() {
        File sdcardDir = null;
        boolean isSDExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
            return sdcardDir.toString();
        } else {
            return null;
        }
    }

}
