package com.infin8u.gitlist;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * this class will only generate a string for git api and take care
 * of the parameters
 * when you call URL() the factory will
 * join directories and parameters and return them as a single string
 * P.S:I made it chained so it will be easy to add more options
 * and support more API functionalities
 * feel free to ask why*/
public class GitApiURLFactory {
    //https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc
    private final String API_URL = "https://api.github.com";
    private final String SEARCH = "search";
    private final String REPOS = "repositories";

    private List<String> _path = new ArrayList<>();
    private List<String> _params = new ArrayList<>();

    public GitApiURLFactory Search()
    {
        _path.add(SEARCH);
        return this;
    }
    public GitApiURLFactory Repos()
    {
        _path.add(REPOS);
        return this;
    }
    public GitApiURLFactory Created(String operator,String date)
    {
        _params.add("q=created:"+operator+date);
        return this;
    }
    public GitApiURLFactory Sort(GitAPISortParam sort)
    {
        String str = "";
        switch (sort)
        {
            case STARS:
                str="stars";
                break;
            //we can add more cases later

        }
        _params.add("sort="+str);
        return this;
    }
    public GitApiURLFactory Page(int page)
    {
        _params.add("page="+page);
        return this;
    }
    public GitApiURLFactory Order(GitAPIOrderParam order)
    {
        //well, default is asc in almost every system :)
        String str = "asc";
        switch (order)
        {
            case DESC:
                str = "desc";
        }
        _params.add("order="+str);
        return this;
    }
    /**these methods shouldn't be chained methods cause it's illogical to chain a flush*/
    public void FlushPath()
    {
        _path.clear();
        _path.add(API_URL);
    }
    public void FlushParams()
    {
        _params.clear();
    }
    /**********/

    public GitApiURLFactory()
    {
        _path.add(API_URL);
    }


    //returns final URL
    public String URL()
    {

        StringBuilder sb = new StringBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sb.append(String.join("/",_path));
            sb.append("?");
            sb.append(String.join("&",_params));
        }else
        {
            for(int i = 0;i<_path.size();i++)
            {
                sb.append(_path.get(i));
                if(i<_path.size()-1)
                    sb.append("/");
            }
            sb.append("?");
            for(int i = 0;i<_params.size();i++)
            {
                sb.append(_params.get(i));
                if(i<_params.size()-1)
                    sb.append("&");
            }
        }
        return sb.toString();
    }
}
//using enums will make this code reusable
//and this class will survive any changes in the api easily
enum GitAPISortParam
{
    STARS,
}
enum GitAPIOrderParam
{
    ASC,
    DESC
}
