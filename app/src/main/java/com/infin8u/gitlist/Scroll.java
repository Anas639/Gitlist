package com.infin8u.gitlist;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class Scroll extends ScrollView {

    private List<ScrollBehavior> _onScrolls = new ArrayList<>();

    public void addOnScrollBehaviour(ScrollBehavior b)
    {
        _onScrolls.add(b);
    }

    public Scroll(Context context) {
        super(context);
    }

    public Scroll(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public Scroll(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Scroll(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onScrollChanged(int x,int y,int oldx,int oldy)
    {
        super.onScrollChanged(x,y,oldx,oldy);
        //detect if we reached the bottom

        //fire onScrollChange behaviors
        for(int i = 0;i< _onScrolls.size();i++)
        {
            _onScrolls.get(i).setPositions(x,y,oldx,oldy);
            _onScrolls.get(i).Execute();

        }
    }
}
