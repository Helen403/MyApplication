package com.example.snoy.myapplication.lib.Utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

/**
 * 设备信息工具
 * getAndroidID 获取AndroidID
 * getIMEI 获取设备IMEI码
 * getIMSI 获取设备IMSI码
 * getWifiMacAddr 获取MAC地址
 * getIP 获取网络IP地址(优先获取wifi地址)
 * getWifiIP 获取WIFI连接下的ip地址
 * getGPRSIP 获取GPRS连接下的ip地址
 * getSerial 获取设备序列号
 * getSIMSerial 获取SIM序列号
 * getPhoneNumber 获取手机号码(未获取成功)
 * getMNC 获取网络运营商 46000,46002,46007 中国移动,46001 中国联通,46003 中国电信
 * getCarrier 获取网络运营商：中国电信,中国移动,中国联通
 * getModel 获取硬件型号
 * getBuildBrand 获取编译厂商
 * getBuildHost 获取编译服务器主机
 * getBuildTags 获取描述Build的标签
 * getBuildTime 获取系统编译时间
 * getBuildUser 获取系统编译作者
 * getBuildVersionRelease 获取编译系统版本(5.1)
 * getBuildVersionCodename 获取开发代号
 * getBuildVersionIncremental 获取源码控制版本号
 * getBuildVersionSDK 获取编译的SDK
 * getBuildID 获取修订版本列表(LMY47D)
 * getSupportedABIS CPU指令集
 * getManufacturer 获取硬件制造厂商
 * getBootloader 获取系统启动程序版本号
 * getScreenDisplayID
 * getDisplayVersion 获取系统版本号
 * getLanguage 获取语言
 * getCountry 获取国家
 * getOSVersion 获取系统版本:5.1.1
 * getGSFID 获取GSF序列号
 * getBluetoothMAC 获取蓝牙地址
 * getPsuedoUniqueID Android设备物理唯一标识符
 * getFingerprint 构建标识,包括brand,name,device,version.release,id,version.incremental,type,tags这些信息
 * getHardware 获取硬件信息
 * getProduct 获取产品信息
 * getDevice 获取设备信息
 * getBoard 获取主板信息
 * getRadioVersion 获取基带版本(无线电固件版本 Api14以上)
 * getUA 获取的浏览器指纹(User-Agent)
 * getDensity 获取得屏幕密度
 * getGoogleAccounts 获取google账号
 */
public final class DeviceUtils {


    /**
     * getAndroidID 获取AndroidID
     */
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * getIMEI 获取设备IMEI码
     */
    public static String getIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * getIMSI 获取设备IMSI码
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId() != null ? tm.getSubscriberId() : null;
    }

    /**
     * getWifiMacAddr 获取MAC地址
     */
    @SuppressWarnings("MissingPermission")
    public static String getWifiMacAddr(Context context) {
        String macAddr = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            macAddr = wifi.getConnectionInfo().getMacAddress();
            if (macAddr == null) {
                macAddr = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddr;
    }

    /**
     * getIP 获取网络IP地址(优先获取wifi地址)
     */
    public static String getIP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled() ? getWifiIP(wifiManager) : getGPRSIP();
    }

    /**
     * getWifiIP 获取WIFI连接下的ip地址
     */
    public static String getWifiIP(WifiManager wifiManager) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ip = intToIp(wifiInfo.getIpAddress());
        return ip != null ? ip : "";
    }

    /**
     * getGPRSIP 获取GPRS连接下的ip地址
     */
    public static String getGPRSIP() {
        String ip = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                for (Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip = null;
        }
        return ip;
    }


    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }


    /**
     * getSerial 获取设备序列号
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * getSIMSerial 获取SIM序列号
     */
    public static String getSIMSerial(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * getPhoneNumber 获取手机号码(未获取成功)
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * getMNC 获取网络运营商 46000,46002,46007 中国移动,46001 中国联通,46003 中国电信
     */
    public static String getMNC(Context context) {
        String providersName = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            providersName = telephonyManager.getSimOperator();
            providersName = providersName == null ? "" : providersName;
        }
        return providersName;
    }

    /**
     * getCarrier 获取网络运营商：中国电信,中国移动,中国联通
     */
    public static String getCarrier(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName().toLowerCase(Locale.getDefault());
    }


    /**
     * getModel 获取硬件型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * getBuildBrand 获取编译厂商
     */
    public static String getBuildBrand() {
        return Build.BRAND;
    }

    /**
     * getBuildHost 获取编译服务器主机
     */
    public static String getBuildHost() {
        return Build.HOST;
    }

    /**
     * getBuildTags 获取描述Build的标签
     */
    public static String getBuildTags() {
        return Build.TAGS;
    }

    /**
     * getBuildTime 获取系统编译时间
     */
    public static long getBuildTime() {
        return Build.TIME;
    }

    /**
     * getBuildUser 获取系统编译作者
     */
    public static String getBuildUser() {
        return Build.USER;
    }

    /**
     * getBuildVersionRelease 获取编译系统版本(5.1)
     */
    public static String getBuildVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * getBuildVersionCodename 获取开发代号
     */
    public static String getBuildVersionCodename() {
        return Build.VERSION.CODENAME;
    }

    /**
     * getBuildVersionIncremental 获取源码控制版本号
     */
    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * getBuildVersionSDK 获取编译的SDK
     */
    public static int getBuildVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * getBuildID 获取修订版本列表(LMY47D)
     */
    public static String getBuildID() {
        return Build.ID;
    }

    /**
     * getSupportedABIS CPU指令集
     */
    public static String[] getSupportedABIS() {
        String[] result = new String[]{"-"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            result = Build.SUPPORTED_ABIS;
        }
        if (result == null || result.length == 0) {
            result = new String[]{"-"};
        }
        return result;
    }

    /**
     * getManufacturer 获取硬件制造厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * getBootloader 获取系统启动程序版本号
     */
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }


    /**
     * getScreenDisplayID
     */
    public static String getScreenDisplayID(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return String.valueOf(wm.getDefaultDisplay().getDisplayId());
    }

    /**
     * getDisplayVersion 获取系统版本号
     */
    public static String getDisplayVersion() {
        return Build.DISPLAY;
    }


    /**
     * getLanguage 获取语言
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * getCountry 获取国家
     */
    public static String getCountry(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Locale locale = Locale.getDefault();
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY ? tm.getSimCountryIso().toLowerCase(Locale.getDefault()) : locale.getCountry().toLowerCase(locale);
    }

    /**
     * getOSVersion 获取系统版本:5.1.1
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * getGSFID 获取GSF序列号
     * <uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES"/>
     */
    public static String getGSFID(Context context) {
        String result;
        final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        final String ID_KEY = "android_id";
        String[] params = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);
        if (c == null || !c.moveToFirst() || c.getColumnCount() < 2) {
            return null;
        } else {
            result = Long.toHexString(Long.parseLong(c.getString(1)));
        }
        c.close();
        return result;
    }

    /**
     * getBluetoothMAC 获取蓝牙地址
     * <uses-permission android:name="android.permission.BLUETOOTH"/>
     */
    @SuppressWarnings("MissingPermission")
    public static String getBluetoothMAC(Context context) {
        String result = null;
        try {
            if (context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH)
                    == PackageManager.PERMISSION_GRANTED) {
                BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
                result = bta.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * getPsuedoUniqueID Android设备物理唯一标识符
     */
    public static String getPsuedoUniqueID() {
        String devIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            devIDShort += (Build.SUPPORTED_ABIS[0].length() % 10);
        } else {
            devIDShort += (Build.CPU_ABI.length() % 10);
        }
        devIDShort += (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            serial = "ESYDV000";
        }
        return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * getFingerprint 构建标识,包括brand,name,device,version.release,id,version.incremental,type,tags这些信息
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * getHardware 获取硬件信息
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * getProduct 获取产品信息
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * getDevice 获取设备信息
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * getBoard 获取主板信息
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * getRadioVersion 获取基带版本(无线电固件版本 Api14以上)
     */
    public static String getRadioVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ? Build.getRadioVersion() : "";
    }

    /**
     * getUA 获取的浏览器指纹(User-Agent)
     */
    public static String getUA(Context context) {
        final String system_ua = System.getProperty("http.agent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return new WebView(context).getSettings().getDefaultUserAgent(context) + "__" + system_ua;
        } else {
            return new WebView(context).getSettings().getUserAgentString() + "__" + system_ua;
        }
    }

    /**
     * getDensity 获取得屏幕密度
     */
    public static String getDensity(Context context) {
        String densityStr = null;
        final int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                densityStr = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityStr = "MDPI";
                break;
            case DisplayMetrics.DENSITY_TV:
                densityStr = "TVDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                densityStr = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                densityStr = "XHDPI";
                break;
            case DisplayMetrics.DENSITY_400:
                densityStr = "XMHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                densityStr = "XXXHDPI";
                break;
        }
        return densityStr;
    }

    /**
     * <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
     * getGoogleAccounts 获取google账号
     */
    @SuppressWarnings("MissingPermission")
    public static String[] getGoogleAccounts(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
            String[] result = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                result[i] = accounts[i].name;
            }
            return result;
        }
        return null;
    }


}