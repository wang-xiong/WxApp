package com.example.wangxiong.wxmoduletest.aroutertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wangxiong.wxmoduletest.R;

@Route(path = "/wxmoduletest/URLActivity2")
public class URLActivity2 extends AppCompatActivity {

    @Autowired
    String name;

    @Autowired
    int age;

    @Autowired
    boolean boy;

    @Autowired
    int high;

    @Autowired
    String obj ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url2);

        //如果需要接收 URL 中的参数，需要在 Activity 调用自动注入初始化方法；
        ARouter.getInstance().inject(this);
        //如果不使用自动注入，那么可以不写 ARouter.getInstance().inject(this)，
        //但是需要取值的字段仍然需要标上 @Autowired 注解，因为 只有标上注解之后，
        //ARouter才能知道以哪一种数据类型提取URL中的参数并放入Intent中，这样您才能在intent中获取到对应的参数


        TextView textView = findViewById(R.id.tv);

        //解析参数
        Bundle bundle = getIntent().getExtras();
        String name1 = bundle.getString("name");

        textView.setText("参数是： " + "name: " + name + "  age: " + age + " high:" + high
                + " boy: " + boy + " name1: " + name1 + " obj: " + obj.toString() );


    }
}
