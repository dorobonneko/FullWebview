package com.moe.fullwebview.widget;
import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.content.res.*;
import com.moe.fullwebview.*;
import android.graphics.*;

public class MenuInfo
{
	private CharSequence title;
	private Drawable icon;
	//private boolean vibrate;
	private int color;
	private Context context;
	private float start,end;
	private Point point;
	public MenuInfo(Context context,AttributeSet attrs){
		this.context=context;
		TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.SectorMenu);
		title=ta.getText(R.styleable.SectorMenu_title);
		icon=ta.getDrawable(R.styleable.SectorMenu_icon);
		//vibrate=ta.getBoolean(R.styleable.SectorMenu_vibrate,false);
		color=ta.getColor(R.styleable.SectorMenu_color,0);
	}

	public void setTitle(CharSequence title)
	{
		this.title = title;
	}

	public CharSequence getTitle()
	{
		return title;
	}

	public void setIcon(Drawable icon)
	{
		this.icon = icon;
	}

	public Drawable getIcon()
	{
		return icon;
	}

	/*public void setVibrate(boolean vibrate)
	{
		this.vibrate = vibrate;
	}

	public boolean isVibrate()
	{
		return vibrate;
	}*/

	public void setColor(int color)
	{
		this.color = color;
	}

	public int getColor()
	{
		return color;
	}
	public void setDegress(float start,float end){
		this.start=start;
		this.end=end;
	}
	public float getStartDegress(){
		return start;
	}
	public float getEndDegress(){
		return end;
	}
	public void setPoint(int x,int y){
		if(point==null)point=new Point();
		point.set(x,y);
	}
	public Point getPoint(){
		return point;
	}
}
