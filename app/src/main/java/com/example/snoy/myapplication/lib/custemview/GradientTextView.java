package com.example.snoy.myapplication.lib.custemview;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 左上角倾斜45度,设置斜的文字
 * @author Administrator
 *
 */
public final class GradientTextView extends TextView {

	public GradientTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		double tangent=Math.atan2(getMeasuredHeight(),getMeasuredWidth());
		float rotateAngle=(float) (tangent/Math.PI)*180;
		canvas.rotate(-rotateAngle, getMeasuredWidth()/2, getMeasuredHeight()/2);
		super.onDraw(canvas);
	}
	
}
