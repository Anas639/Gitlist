package com.infin8u.gitlist;

public abstract class ScrollBehavior extends Behavior {

    //the scroll behavior holds the new x and y in addition to the old x and y
    private int _x = 0,_y = 0,_oldX = 0,_oldY = 0;

    public int X()
    {
        return _x;
    }
    public int OldX()
    {
        return _oldX;
    }
    public int Y()
    {
        return  _y;
    }
    public int OldY()
    {
        return _oldY;
    }


    public void setPositions(int x, int y, int oldx, int oldy) {
        _x = x;
        _y = y;
        _oldX = oldx;
        _oldY = oldy;
    }
}
