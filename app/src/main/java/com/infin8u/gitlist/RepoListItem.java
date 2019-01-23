package com.infin8u.gitlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class RepoListItem extends RelativeLayout {
    private TextView _repoName;
    private TextView _repoDescription;
    private TextView _ownerName;
    private TextView _starts;

    private Handler _handler;

    public void runOnUiThread(Runnable r)
    {
        _handler.post(r);
    }
    public RepoListItem(Context context) {
        super(context);
        Initialize();
    }

    public RepoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize();
    }

    public RepoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Initialize();
    }

    private void Initialize()
    {
        _handler = new Handler(getContext().getMainLooper());

        inflate(getContext(),R.layout.repo_list_item,this);
        _repoName = this.findViewById(R.id.repoName);
        _repoDescription = this.findViewById(R.id.repoDescription);
        _ownerName = this.findViewById(R.id.repoOwner);
        _starts = this.findViewById(R.id.repoStars);

    }

    public void LoadData(JSONObject jitem) {
        try {
            _repoName.setText(jitem.getString("name"));
            _repoDescription.setText(jitem.getString("description"));
            _starts.setText(calculateNumberSuffix(
                    jitem.getString("stargazers_count")
            ));
            JSONObject owner = jitem.getJSONObject("owner");
            _ownerName.setText(owner.getString("login"));
            String avatar_url = owner.getString("avatar_url");
            DownloadAvatar(avatar_url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void DownloadAvatar(final String avatar_url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(avatar_url);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    final Bitmap bmp = BitmapFactory.decodeStream(input);
                    if(bmp != null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImageViewBmp(bmp);
                            }
                        });
                    }
                } catch (IOException e) {
                }
            }
        }).start();

    }

    private void setImageViewBmp(final Bitmap b) {
        final ImageView imgv = findViewById(R.id.repoOwnerAvatar);
        imgv.post(new Runnable() {
            @Override
            public void run() {
                try {


                    int width = imgv.getWidth();
                    int height = (int) (width * ((float) b.getHeight() / (float) b.getWidth()));
                    imgv.setImageBitmap(Bitmap.createScaledBitmap(b, width, height, false));
                }catch(IllegalArgumentException iarg)
                {

                }
                b.recycle();
            }
        });
    }

    private String calculateNumberSuffix(String number)
    {
        long nb = Long.parseLong(number);
        if(nb<1000)
            return nb+"";
        if(nb>=1000 && nb <1000000)
            return BigDecimal.valueOf(((double)nb/(double)1000)).setScale(1,BigDecimal.ROUND_HALF_DOWN).doubleValue()+"K";
        if(nb>=1000000 && nb <1000000000)
            return BigDecimal.valueOf(((double)nb/(double)1000000)).setScale(1,BigDecimal.ROUND_HALF_DOWN).doubleValue()+"M";
        if(nb>=1000000000)
            return BigDecimal.valueOf(((double)nb/(double)1000000)).setScale(1,BigDecimal.ROUND_HALF_DOWN).doubleValue()+"Md";
        return "";
    }
}
