package com.whunf.netexam;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    ListView listview;
    JokeAdapter mJokeAdapter;
    private ImageLoader imageLoader;

    String url = "https://route.showapi.com/341-2?showapi_appid=20697&showapi_sign=6305ff6ef59c46ebba4566ade35745c9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = ((MyApplication) getApplication()).requestQueue;
        //初始化图片加载器，第一个参数是请求队列，第二个参数是缓存策略对象
        imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        listview = (ListView) findViewById(R.id.listview);
        mJokeAdapter = new JokeAdapter(this);
        listview.setAdapter(mJokeAdapter);
        initData();

    }

    StringRequest sr;

    private void initData() {
        //创建request对象
        sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//请求成功时回调此方法
                //使用fastjson将json String转换成对象
                ShowapiRes showapiRes = JSONObject.parseObject(response, ShowapiRes.class);
                if (showapiRes != null) {
                    ShowapiResBody showapi_res_body = showapiRes.getShowapi_res_body();
                    if (showapi_res_body != null) {
                        Joke[] contentlist = showapi_res_body.getContentlist();
                        //将数据添加到适配器中，最终作用到listview上
                        mJokeAdapter.addAll(contentlist);
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {//无网络或者请求失败时回调
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_LONG).show();
            }
        });
        //将请求添加到异步任务队列中
        requestQueue.add(sr);
    }


    @Override
    protected void onDestroy() {
        //取消单个请求
        if (sr != null) {
            sr.cancel();
        }
//        //取消队列所有的请求
//        requestQueue.cancelAll(null);
        super.onDestroy();
    }

    class JokeAdapter extends ArrayAdapter<Joke> {

        public JokeAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.joke_list_item, null);
                holder = new ViewHolder(convertView);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Joke item = getItem(position);
            holder.contentTv.setText(item.getTitle());
            holder.iconImv.setImageUrl(item.getImg(), imageLoader);
            return convertView;
        }


        class ViewHolder {
            //NetworkImageView是一个加载网路图片的控件
            NetworkImageView iconImv;
            TextView contentTv;

            ViewHolder(View v) {
                iconImv = (NetworkImageView) v.findViewById(R.id.icon_imv);
                contentTv = (TextView) v.findViewById(R.id.content_tv);
                //将当前对象设置到视图v中
                v.setTag(this);
            }

        }

    }
}
