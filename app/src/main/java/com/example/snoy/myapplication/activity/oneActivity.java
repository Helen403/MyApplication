package com.example.snoy.myapplication.activity;

import android.util.Log;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.Utils.ThreadPoolUtils;
import com.example.snoy.myapplication.lib.base.BaseActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**
 * Created by Administrator on 2016/8/23.
 */
public class oneActivity extends BaseActivity {


    @Override
    public int getContentView() {
        return R.layout.fragment_test_1;
    }


    @Override
    public void findViews() {
    }

    @Override
    public void initData() {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String nameSpace = "http://service.security.vada.com/";
                    String methodName = "findUserByAccount";
                    //String soapAction = nameSpace + "userService";
                    String soapAction = "";
                    String url = "http://192.168.1.101/smart/ws/IUserService";

                    Log.i(this.getClass().getName(), "进入线程...........................");

                    // 创建HttpTransportSE对象。通过HttpTransportSE类的构造方法可以指定WebService的WSDL文档的URL
                    HttpTransportSE transport = new HttpTransportSE(url);
                    transport.debug = true;// 是否是调试模式

                    // 指定WebService的命名空间和调用的方法名利用SoapObject类
                    // 第1个参数表示WebService的命名空间，可以从WSDL文档中找到WebService的命名空间。
                    // 第2个参数表示要调用的WebService方法名
                    SoapObject soapObject = new SoapObject(nameSpace, methodName);

                    // 设置调用方法的参数值，这一步是可选的，如果方法没有参数，可以省略这一步
                    // addProperty方法的第1个参数虽然表示调用方法的参数名，但该参数值并不一定与服务端的WebService类中的方法参数名一致，只要设置参数的顺序一致即可。
                    soapObject.addProperty("account", "demo@126.com");

                    // 生成调用WebService方法的SOAP请求信息。该信息由SoapSerializationEnvelope对象描述
                    // 构造方法设置SOAP协议的版本号:该版本号需要根据服务端WebService的版本号设置
                    // SoapSerializationEnvelope对象后，不要忘了设置SoapSerializationEnvelope类的bodyOut属性，该属性的值就是创建的SoapObject对象
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    // 调用SoapSerializationEnvelope的setOutputSoapObject()方法，
                    // 或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为SoapSerializationEnvelope的传出SOAP消息体
                    envelope.dotNet = false; // true--net; false--java;
                    // envelope.bodyOut = soapObject;
                    envelope.setOutputSoapObject(soapObject);

                    // 调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程的web service
                    transport.call(soapAction, envelope);

                    Log.d("Helen", "调用远程的web service...........................");

                    if (envelope.getResponse() != null) {
                        Log.d("Helen", "有返回结果.............===..............");
                        String result = String.valueOf(envelope.getResponse());
                        Log.d("Helen", "结果:" + result);
                         //访问SoapSerializationEnvelope对象的bodyIn属性，
                        // 该属性返回一个SoapObject对象，该对象就代表Web service的返回消息，解析该对象，即可获得调用web
                         //service的返回值
                         SoapObject result1 = (SoapObject) envelope.bodyIn;
                         String show = (String) result1.getProperty(0).toString();
                        L(show);
                    } else {
                        Log.d("Helen", "无返回结果.............@@@..............");
                    }
                } catch (Exception e) {
                    Log.d("Helen", "异常...." + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setListeners() {

    }


}
