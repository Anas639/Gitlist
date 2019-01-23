package com.infin8u.gitlist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private Date _date = null;
    public DateUtil Now()
    {
        return new DateUtil(Calendar.getInstance().getTime());
    }
    public DateUtil SubstractDays(int days)
    {
        if(_date == null)
            return this;
        Calendar cal = Calendar.getInstance();
        cal.setTime(_date);
        cal.add(Calendar.DAY_OF_MONTH,-days);
        return new DateUtil(cal.getTime());
    }
    public DateUtil()
    {

    }
    public DateUtil(Date d)
    {
        _date = d;
    }
    //this will print date in yyyy-MM-dd format
    public String Date()
    {
        if(_date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(_date);

        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");

        return dateF.format(cal.getTime());
    }
}
