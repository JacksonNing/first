package com.example.songzeceng.studyoflivedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songzeceng on 2018/3/16.
 */

public class MainFragment extends Fragment {
    private MyModel nameModel;
    private Observer<String> nameObserver;
    private Random r = new Random();
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.btn_change)
    Button btn_change;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main_fragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameModel = ViewModelProviders.of(this).get(MyModel.class);
        //获取liveData对象.注意of()函数里面要传入的是Fragment对象
        nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //liveData对象发生变化时的回调方法
                if(!TextUtils.isEmpty(s)){
                    tv_name.setText("");
                    tv_name.setText(s);
                }
            }
        };
        //创建liveData观察者对象

        nameModel.getName().observe(getActivity(),nameObserver);
    }

    @OnClick(R.id.btn_change)
    public void changeName(){
        String name = "name-" + Math.abs(r.nextInt() % 5);
        nameModel.getName().setValue(name);
        //更新liveData的方法.主线程:setValue()，子线程:postValue()
    }
}