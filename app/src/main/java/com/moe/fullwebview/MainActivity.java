package com.moe.fullwebview;

import android.app.*;
import android.os.*;
import com.moe.fullwebview.widget.*;

public class MainActivity extends Activity 
{
	private SectorButton mSectorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(mSectorButton=new SectorButton(this));
		mSectorButton.inflaterMenu(R.menu.sector_menu);
		mSectorButton.setIcon(getResources().getDrawable(R.drawable.menu));
		
	}
}
