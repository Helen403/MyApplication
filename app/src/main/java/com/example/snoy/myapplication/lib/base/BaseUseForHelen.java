package com.example.snoy.myapplication.lib.base;

/**
 * 装载经常使用的代码
 */
public final class BaseUseForHelen {
    /*
      //布局调整
      LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
      content.setLayoutParams(rl);

//*********************************************************************************
      //布局填充
     LayoutInflater inflater = LayoutInflater.from(context);
     View view = inflater.inflate(R.layout, null);

//*********************************************************************************
     //相对布局
       <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/rl_"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#fff"
        android:gravity="center">
        </RelativeLayout>

//*********************************************************************************
    //线性布局
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/ll_"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#fff"
        android:gravity="center"
        android:orientation="vertical">
        </LinearLayout>

//*********************************************************************************
    //图片布局
    <ImageView
        android:id="@+id/iv_"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="0dp"
        android:background="#fff"
        android:padding="0dp"
        android:scaleType="fitXY"
        android:src="@mipmap/1icon_logo" />

//*********************************************************************************
    //文字布局
   <TextView
        android:id="@+id/tv_"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="0dp"
        android:background="#fff"
        android:gravity="center"
        android:padding="0dp"
        android:singleLine="true"
        android:text="输入内容"
        android:textColor="#000000"
        android:textSize="18sp" />

//*********************************************************************************
     //横线
      <View
         android:layout_width="match_parent"
         android:layout_height="1dp"
         android:background="#D3D3D3" />

//*********************************************************************************
        <ListView
        android:id="@+id/listview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

//**********************************************************************************
 <EditText
        android:id="@+id/edittext"
        android:layout_width="0dp"
        android:layout_height="30dp"
        android:layout_gravity="center"
        android:layout_weight="0.6"
        android:background="@drawable/custermview_edittext"
        android:focusable="true"
        android:focusableInTouchMode="true"
        android:hint="请输入商家/商品名称"
        android:textColor="#D2D2D2"
        android:textSize="12sp" />

//*********************************************************************************

     //shape
     <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android" >
       <!-- 圆角 -->
       <corners
        android:radius="9dp"
        android:topLeftRadius="2dp"
        android:topRightRadius="2dp"
        android:bottomLeftRadius="2dp"
        android:bottomRightRadius="2dp"/><!-- 设置圆角半径 -->
    <!-- 渐变 -->
    <gradient
        android:startColor="@android:color/white"
        android:centerColor="@android:color/black"
        android:endColor="@android:color/black"
        android:useLevel="true"
        android:angle="45"
        android:type="radial"
        android:centerX="0"
        android:centerY="0"
        android:gradientRadius="90"/>
    <!-- 间隔 -->
    <padding
        android:left="2dp"
        android:top="2dp"
        android:right="2dp"
        android:bottom="2dp"/><!-- 各方向的间隔 -->
    <!-- 大小 -->
    <size
        android:width="50dp"
        android:height="50dp"/><!-- 宽度和高度 -->
    <!-- 填充 -->
    <solid
        android:color="@android:color/white"/><!-- 填充的颜色 -->
    <!-- 描边 -->
    <stroke
        android:width="2dp"
        android:color="@android:color/black"
        android:dashWidth="1dp"
        android:dashGap="2dp"/>
    </shape>


//*********************************************************************************
    //selector
  <?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 默认时的背景图片-->
    <item android:drawable="@mipmap/1icon_logo" />
    <!-- 没有焦点时的背景图片 -->
    <item android:drawable="@mipmap/1icon_logo" android:state_window_focused="false" />
    <!-- 非触摸模式下获得焦点并单击时的背景图片 -->
    <item android:drawable="@mipmap/1icon_logo" android:state_focused="true" android:state_pressed="true" />
    <!-- 触摸模式下单击时的背景图片-->
    <item android:drawable="@mipmap/1icon_logo" android:state_focused="false" android:state_pressed="true" />
    <!--选中时的图片背景-->
    <item android:drawable="@mipmap/1icon_logo" android:state_selected="true" />
    <!--获得焦点时的图片背景-->
    <item android:drawable="@mipmap/1icon_logo" android:state_focused="true" />
</selector>

//*********************************************************************************
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true" android:color="#D5A5EF"/>
    <item android:state_pressed="true" android:color="#D5A5EF"/>
    <item android:color="#949494"/>
</selector>



//***********************************************************************************

//保留一位小数
 DecimalFormat df = new DecimalFormat("######0.0");
 df.format(tmp)



     */
}
