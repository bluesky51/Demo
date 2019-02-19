package com.sky.wang.view.home;
import com.sky.wang.R;
import com.sky.wang.base.mvpbase.view.AbstractMvpAppCompatActivity;

public class HomeActivity extends AbstractMvpAppCompatActivity<HomeView,HomePresenter> implements HomeView {

    @Override
    protected void initData() {

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void noNetworkStatus() {

    }

    @Override
    public void noContentStatus() {

    }
}
