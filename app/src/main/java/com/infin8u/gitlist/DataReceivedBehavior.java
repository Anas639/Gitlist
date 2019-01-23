package com.infin8u.gitlist;

public abstract class DataReceivedBehavior extends Behavior {

    //this data can be anything from data types to wrapper objects
    private Object _data = null;
    public void SetData(Object d)
    {
        _data = d;
    }
    public Object GetData()
    {
        return _data;
    }
}
