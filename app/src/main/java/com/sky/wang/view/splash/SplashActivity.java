package com.sky.wang.view.splash;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sky.wang.ApkExtract;
import com.sky.wang.BsPatch;
import com.sky.wang.R;
import com.sky.wang.adapter.PullToRefreshAdapter;
import com.sky.wang.app.AppStatusConstant;
import com.sky.wang.app.AppStatusManager;
import com.sky.wang.base.mvpbase.factory.CreatePresenter;
import com.sky.wang.base.mvpbase.view.AbstractMvpAppCompatActivity;
import com.sky.wang.databinding.ActivitySplashBinding;
import com.sky.wang.model.bean.DataInfo;
import com.sky.wang.utils.PermissionUtils;
import com.sky.wang.view.home.HomeActivity;
import com.sky.wang.widget.CustomNavigatorBar;
import java.io.File;
import java.util.List;
@CreatePresenter(SplashPresenter.class)
public class SplashActivity extends AbstractMvpAppCompatActivity<SplashView,SplashPresenter> implements  SplashView<DataInfo>,CustomNavigatorBar.OnCustomClickListener {

    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusConstant.STATUS_NORMAL);//进入应用初始化设置成未登录状态
        super.onCreate(savedInstanceState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        PermissionUtils.getInstance().getGpsPerssion(this);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);

        binding.customView.getRightText().setVisibility(View.GONE);
        binding.customView.addViewClickListener(this);
        binding.mainMultiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshUI();
            }
        });

        refreshUI();
    }

    private void refreshUI(){
        getMvpPresenter().test(this);
    }

    @Override
    public void showProgressDialog() {
        binding.mainMultiplestatusview.showLoading();
    }

    @Override
    public void hideProgressDialog() {
        binding.mainMultiplestatusview.showContent();
    }

    @Override
    public void noNetworkStatus() {
        binding.mainMultiplestatusview.showNoNetwork();
    }

    @Override
    public void noContentStatus() {
          binding.mainMultiplestatusview.showEmpty();
    }


    @Override
    public void onSuccess(DataInfo dataInfo) {
        DataInfo.NormalBean normalBean= dataInfo.getNormal();
        if (normalBean!=null)
        {
            List<DataInfo.NormalBean.ListBeanX>  listBeanXES= normalBean.getList();
            PullToRefreshAdapter adapter =new PullToRefreshAdapter(R.layout.layout_item,listBeanXES);
            DividerItemDecoration  itemDecoration= new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
            //itemDecoration.setDrawable(getResources().getDrawable(R.drawable.split_line));
            //itemDecoration.setDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.navigate_statusbar_bgcolor)));
            binding.recyclerView.addItemDecoration(itemDecoration);
            LinearLayoutManager manager=new LinearLayoutManager(this);
            binding.recyclerView.setLayoutManager(manager);
            binding.recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onError(String message) {
       binding.mainMultiplestatusview.showError();
    }

    @Override
    public void onClickListener(View rootView) {
        switch (rootView.getId()){
            case R.id.left_image:
                startActivity(new Intent(this, HomeActivity.class));
                Toast.makeText(this,"点击了左边",Toast.LENGTH_LONG).show();
                break;
            case R.id.right_image:
                if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    doBspatch();
                }
                Toast.makeText(this,"点击了右边",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void doBspatch() {
        final File destApk = new File(Environment.getExternalStorageDirectory(), "dest.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "PATCH.patch");
        Logger.e("patch = " + patch.exists() + " , " + patch.getAbsolutePath());

        //一定要检查文件都存在
        BsPatch.bspatch(ApkExtract.extract(this),
                destApk.getAbsolutePath(),
                patch.getAbsolutePath());

        if (destApk.exists())
            ApkExtract.install(this, destApk.getAbsolutePath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doBspatch();
            }
        }
    }
}
