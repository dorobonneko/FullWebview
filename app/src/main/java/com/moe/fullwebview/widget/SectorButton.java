package com.moe.fullwebview.widget;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.content.res.*;
import com.moe.fullwebview.*;
import java.io.*;
import org.xmlpull.v1.*;
import java.util.*;
import android.graphics.drawable.*;

public class SectorButton extends View implements GestureDetector.OnGestureListener
{
	public final static int LEFT=-1,RIGHT=1,CENTER=0;
	private boolean vibrate;
	private int menu=-1,gravity,radius;
	private Drawable icon;
	private ArrayList<MenuInfo> list=new ArrayList<>(4);
	private GestureDetector mGestureDetector;
	private Rect iconBounds=new Rect();
	public SectorButton(Context context){
		this(context,null);
	}
	public SectorButton(Context context,AttributeSet attrs){
		super(context,attrs);
		TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.SectorButton);
		vibrate=ta.getBoolean(R.styleable.SectorButton_vibrate,true);
		icon=ta.getDrawable(R.styleable.SectorButton_icon);
		int menu=ta.getResourceId(R.styleable.SectorButton_menu,-1);
		if(menu!=-1)
			inflaterMenu(menu);
		setGravity(ta.getInt(R.styleable.SectorButton_gravity,CENTER));
		ta.recycle();
		mGestureDetector=new GestureDetector(this);
		mGestureDetector.setIsLongpressEnabled(true);
		radius=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,72,getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int width=radius*2;
		int height=Math.max(measureDegress(list),radius/3/2)+radius;
		setMeasuredDimension(MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
		iconBounds.left=radius-icon.getIntrinsicWidth()/2;
		iconBounds.top=radius-icon.getIntrinsicHeight()/2;
		iconBounds.bottom=iconBounds.top+icon.getIntrinsicHeight();
		iconBounds.right=iconBounds.left+icon.getIntrinsicWidth();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		//显示图标
		if(icon!=null){
			icon.setBounds(iconBounds);
			icon.draw(canvas);
		}
		if(isPressed()){
			//显示扇形区域
			for(int i=0;i<list.size();i++){
				MenuInfo info=list.get(i);
				if(info.getIcon()==null)continue;
				Point point=info.getPoint();
				Drawable icon=info.getIcon();
				int halfWidth=icon.getIntrinsicWidth()/2,halfHeight=icon.getIntrinsicHeight()/2;
				info.getIcon().setBounds(point.x-halfWidth,point.y-halfHeight,point.x+halfWidth,point.y+halfHeight);
				info.getIcon().draw(canvas);
			}
		}
	}
	public void inflaterMenu(int menuRes){
		this.menu=menuRes;
		list.clear();
		try{
		XmlResourceParser xrp=getResources().getXml(menuRes);
		int event;
			while ((event = xrp.nextToken()) != xrp.END_DOCUMENT)
			{
				switch(event){
					case XmlResourceParser.START_TAG:
						if(xrp.getName().equals("item")){
							list.add(new MenuInfo(getContext(),xrp));
						}
						break;
				}
			}
		}
		catch (IOException e)
		{}
		catch (XmlPullParserException e)
		{}
		measureDegress(list);
		invalidate();
	}
	protected int measureDegress(List<MenuInfo> list){
		if(!isShown())return 0;
		//计算角度
		float swipe=180f/(list.size()-1);
		float start=180-swipe/2f;
		int height=0;
		for(int i=0;i<list.size();i++){
			MenuInfo info=list.get(i);
			info.setDegress(start,(start+=swipe));
			//获取度数
			float degress=(info.getEndDegress()-info.getStartDegress())/2+info.getStartDegress();
			degress+=90;
			while(degress>360)
				degress%=360;
				
			//计算坐标
			if(iconBounds.width()!=0){
				int t;
			}
			double x=iconBounds.centerX()+Math.sin(degress*Math.PI/180)*(radius/3*2);
			double y=iconBounds.centerY()-Math.cos(degress*Math.PI/180)*(radius/3*2);
			info.setPoint((int)x,(int)y);
			if(i==0)
				height=(int) y-iconBounds.centerY();
		}
		return height;
	}
	public void setGravity(int gravity){
		this.gravity=gravity;
		invalidate();
	}

@Override
public boolean onDown(MotionEvent p1)
{
	if(icon!=null){
		//点击图标区域
		if(iconBounds.contains((int)p1.getX(),(int)p1.getY()))return true;
	}
return false;
}

@Override
public boolean onScroll(MotionEvent p1, MotionEvent p2, float p3, float p4)
{
	return false;
}

@Override
public void onShowPress(MotionEvent p1)
{
	setPressed(true);
	invalidate();
}

@Override
public boolean onSingleTapUp(MotionEvent p1)
{
	
	return true;
}

@Override
public boolean onFling(MotionEvent p1, MotionEvent p2, float p3, float p4)
{
	// TODO: Implement this method
	return false;
}

@Override
public boolean onTouchEvent(MotionEvent event)
{
	boolean flag=mGestureDetector.onTouchEvent(event);
	if(event.getAction()==event.ACTION_CANCEL||event.getAction()==event.ACTION_UP){
		setPressed(false);
		invalidate();
		}
	return flag;
}

@Override
public void onLongPress(MotionEvent p1)
{
	
}
public void setIcon(Drawable icon){
	this.icon=icon;
	invalidate();
}
public boolean isVibrate(){
	return vibrate;
}
}
