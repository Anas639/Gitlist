package com.infin8u.gitlist;

/**
 * so this class will keep track of the page number
 * */
public class GitPager {
    private int _page;
    public GitPager()
    {
        _page = 1;
    }
    public int current()
    {
        return _page;
    }
    public int next()
    {
        _page++;
        return _page;
    }
    public int previous()
    {
        _page--;
        if(_page<1)
            _page = 1;
        return _page;
    }
}
