package com.example.snoy.myapplication.fragmentNav;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.activity.FourActivity;
import com.example.snoy.myapplication.lib.base.BaseFragment;
import com.example.snoy.myapplication.lib.loadingdialog.LoadingDialog;


/**
 * Created by SNOY on 2016/8/23.
 *
 */
public class test5Fragment extends BaseFragment {

    private TextView rowOneItemOne;
    private TextView rowOneItemTwo;
    private ImageView rowTwoItemOne;


    @Override
    public int getContentView() {
        return R.layout.fragment_test__11;
    }

    @Override
    public void findViews() {
        rowOneItemOne = (TextView) contentView.findViewById(R.id.row_one_item_one);
        rowOneItemTwo = (TextView) contentView.findViewById(R.id.row_one_item_two);
        rowTwoItemOne = (ImageView) contentView.findViewById(R.id.row_two_item_one);
    }

    @Override
    public void initData() {
    }


    @Override
    public void setListeners() {
        setOnListeners(rowOneItemOne, rowOneItemTwo);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.row_one_item_one:
                        goToActivityByClass(FourActivity.class);
                        break;
                    case R.id.row_one_item_two:
                        new LoadingDialog(getActivity())
                        .setInterceptBack(false)
                                         .setLoadingText("加载中...")//设置loading时显示的文字
                                .show();


                        break;
                }
            }
        });

    }

    /************************************************************************************/
//    https://github.com/ForgetAll/LoadingDialog
//    LoadingDialog ld = new LoadingDialog(this);
//    ld.setLoadingText("加载中")
//            .setSuccessText("加载成功")//显示加载成功时的文字
//    //.setFailedText("加载失败")
//    .setInterceptBack(intercept_back_event)
//    .setLoadSpeed(speed)
//    .setRepeatCount(repeatTime)
//    .setDrawColor(color)
//    .show();
//
////在你代码中合适的位置调用反馈
//    ld.loadSuccess();
////ld.loadFailed();
    /************************************************************************************/
//    如果你不想要这个动态画出来的效果，你也可以通过closeSuccessAnim()或者closeFailedAnim()关闭它：
//
//    LoadingDialog ld = new LoadingDialog(this);
//    ld.setLoadingText("加载中")
//            .setSuccessText("加载成功")
//    .setInterceptBack(intercept_back_event)
//    .setLoadSpeed(speed)
//    .closeSuccessAnim()
//    .setDrawColor(color)
//    .setRepeatCount(repeatTime)
//    .show();

    /************************************************************************************/

//    setSize(int size)：可以通过这个来设置弹框的尺寸
//    show()：展示你设置的loadingDialog
//    close()：关闭动画释放一些资源
//    setLoadingText(String msg)：设置Loading时的文字
//    setSuccessText(String msg)：设置Loading成功时文字
//    setFailed(String msg)：设置Loading失败时的文字
//    loadSuccess()：调用这个方法展示一个成功的反馈
//    loadFailed()：调用这个方法展示一个失败的反馈
//    closeSuccessAnim()：关闭成功反馈的动态绘制
//    closeFailedAnim()：关闭失败反馈的动态绘制
//    setInterceptBack(boolean interceptBack)：是否拦截用户back，如果设置为true，那么一定要调用close()，不然用户只能把你的程序干掉才能退出了，在我的例子中有一个解决的思路你可以参考一下。
//    getInterceptBack()：返回dialog是否拦截的布尔值
//    setLoadSpeed(Speed speed)：参数是一个枚举，一共两个值，SPEED_ONE是比较慢的，SPEED_TWO比前一个快一点，为毛不再加？处理起来比较麻烦...
//    setDrawColor(int color)：可以改变绘制的颜色，圆和里面的勾啊，叉啊的颜色，不建议你用，不一定好看。
//    setRepeatCount(int count)：设置动态绘制的次数，比如你设置了值为1，那么除了加载的时候绘制一次，还会再绘制一次。如果你有这个需要，可以设置他的重绘次数。

    /************************************************************************************/
    private void showShare() {
//        ShareSDK.initSDK(getActivity());
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("分享");
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("分享");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//
//        // 启动分享GUI
//        oks.show(getActivity());
    }


}
