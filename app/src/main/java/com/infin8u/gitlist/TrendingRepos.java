package com.infin8u.gitlist;

import android.animation.LayoutTransition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrendingRepos extends AppCompatActivity {

    private boolean _busy = false;
    private Scroll _scroll = null;
    private LinearLayout _scrollContainer = null;
    private GitPager _pager = new GitPager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_repos);

        _scroll = findViewById(R.id.Scroll);
        _scrollContainer = findViewById(R.id.ScrollContainer);
        _scroll.setLayoutTransition(new LayoutTransition());
        _scroll.addOnScrollBehaviour(new ScrollBehavior() {
            @Override
            void Execute() {
                if(((_scroll.getChildAt(_scroll.getChildCount()-1).getBottom())-(_scroll.getHeight()+_scroll.getScrollY()))<=0)
                {
                    if(!_busy) {
                        //we reached the bottom
                        //increment the pager
                        int p = _pager.next();
                        LoadRepos();
                    }
                }
            }
        });
        LoadRepos();
    }

    private void LoadRepos() {
        _busy = true;
        try
        {
            //try to load repos from github api
            final String url = generateurl();

            JSONReader.fromUrl(url, new DataReceivedBehavior() {
                @Override
                void Execute() {
                    Object data = GetData();
                    if(data instanceof JSONObject)
                    {
                        //that's good
                        JSONObject jobj = (JSONObject)data;
                        //lets get the items array
                        try {
                            JSONArray jarray = jobj.getJSONArray("items");
                            for(int i =0;i<jarray.length();i++)
                            {
                                final JSONObject jitem = jarray.getJSONObject(i);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RepoListItem rli = new RepoListItem(TrendingRepos.this);
                                        _scrollContainer.addView(rli);
                                        rli.LoadData(jitem);
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            Log.e(getClass().toString(),"An error has occurred while parsing the JSON data");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TrendingRepos.this,getString(R.string.data_read_error),Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }else if(data instanceof Integer)
                    {
                        int error_code = (int)data;
                        switch(error_code)
                        {
                            case(ErrorCodes.UNKNOWN_ERROR):
                            {
                                Log.e(getClass().toString(),"Unknown Error while fetching JSON data");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TrendingRepos.this,R.string.unknown_error,Toast.LENGTH_LONG).show();
                                    }
                                });
                            }break;
                            case ErrorCodes.MALFORMED_ERROR:
                            {
                                Log.e(getClass().toString(),"The url "+url+" is malformed");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TrendingRepos.this,getString(R.string.malformed_url_error),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }break;
                            case ErrorCodes.JSON_PARSE_ERROR:
                            {
                                Log.e(getClass().toString(),"An error has occurred while parsing the JSON data");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TrendingRepos.this,getString(R.string.data_read_error),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }break;
                        }
                    }
                    _busy = false;
                }
            });

        }catch(Exception x)
        {
            //ops!
            Toast.makeText(this,getString(R.string.unknown_error),Toast.LENGTH_LONG).show();
        }
    }
    public String generateurl()
    {
        return  new GitApiURLFactory().Search().Repos().Created(">"
                ,new DateUtil().Now().SubstractDays(30).Date())
                .Sort(GitAPISortParam.STARS).Order(GitAPIOrderParam.DESC)
                .Page(_pager.current()).URL();
    }
}
