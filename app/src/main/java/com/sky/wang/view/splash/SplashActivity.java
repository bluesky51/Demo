package com.sky.wang.view.splash;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.blankj.utilcode.util.ActivityUtils;
import com.classic.common.MultipleStatusView;
import com.orhanobut.logger.Logger;
import com.sky.wang.ApkExtract;
import com.sky.wang.BsPatch;
import com.sky.wang.R;
import com.sky.wang.adapter.PullToRefreshAdapter;
import com.sky.wang.base.mvpbase.factory.CreatePresenter;
import com.sky.wang.base.mvpbase.view.AbstractMvpAppCompatActivity;
import com.sky.wang.helper.DialogHelper;
import com.sky.wang.model.bean.DataInfo;
import com.sky.wang.view.web.TestWebJsActivity;
import com.sky.wang.widget.CustomNavigatorBar;
import java.io.File;
import java.util.List;
import butterknife.BindView;
import me.leefeng.promptlibrary.PromptDialog;

@CreatePresenter(SplashPresenter.class)
public class SplashActivity extends AbstractMvpAppCompatActivity<SplashView, SplashPresenter> implements SplashView<DataInfo>, CustomNavigatorBar.OnCustomClickListener {

    @BindView(R.id.customView)
    CustomNavigatorBar customNavigatorBar;

    @BindView(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    PromptDialog loadingDialog;

    @Override
    protected void initData() {
        com.blankj.utilcode.util.PermissionUtils.permission(Manifest.permission.READ_CONTACTS)
                .rationale(new com.blankj.utilcode.util.PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        Log.e("BlueSky", "拒绝后提示用户需要授权才可政策使用功能");
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new com.blankj.utilcode.util.PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        for (String s : permissionsGranted) {
                            Log.e("BlueSky", "授权成功===" + s);
                        }
                        //doBspatch();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Log.e("BlueSky", "onDenied");
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                    }
                }).request();
        customNavigatorBar.getRightText().setVisibility(View.GONE);
        customNavigatorBar.addViewClickListener(this);
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshUI();
            }
        });

        refreshUI();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_splash;
    }

    private void refreshUI() {
        getMvpPresenter().test(this);
    }

    @Override
    public void showProgressDialog() {
        loadingDialog= new PromptDialog(this);
        loadingDialog.showLoading("加载中");
      //  multiplestatusview.showLoading();
    }

    @Override
    public void hideProgressDialog() {
        if (loadingDialog!=null){
            loadingDialog.dismissImmediately();
        }
      //  multiplestatusview.showContent();
    }

    @Override
    public void noNetworkStatus() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void noContentStatus() {
        multiplestatusview.showEmpty();
    }


    @Override
    public void onSuccess(DataInfo dataInfo) {
        DataInfo.NormalBean normalBean = dataInfo.getNormal();
        if (normalBean != null) {
            List<DataInfo.NormalBean.ListBeanX> listBeanXES = normalBean.getList();
            PullToRefreshAdapter adapter = new PullToRefreshAdapter(R.layout.layout_item, listBeanXES);
            DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                    LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line));
            recyclerView.addItemDecoration(itemDecoration);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onError(String message) {
        multiplestatusview.showError();
    }

    @Override
    public void onClickListener(View rootView) {
        switch (rootView.getId()) {
            case R.id.left_image:
                ActivityUtils.startActivity(this, TestWebJsActivity.class);
                Toast.makeText(this, "点击了左边", Toast.LENGTH_LONG).show();
                break;
            case R.id.right_image:
                com.blankj.utilcode.util.PermissionUtils.permission(Manifest.permission.READ_CONTACTS)
                        .rationale(new com.blankj.utilcode.util.PermissionUtils.OnRationaleListener() {
                            @Override
                            public void rationale(ShouldRequest shouldRequest) {
                                Log.e("BlueSky", "rationale");
                                DialogHelper.showRationaleDialog(shouldRequest);
                            }
                        })
                        .callback(new com.blankj.utilcode.util.PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                for (String s : permissionsGranted) {
                                    Log.e("BlueSky", "授权成功===" + s);
                                }
                                //doBspatch();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                Log.e("BlueSky", "onDenied");
                                if (!permissionsDeniedForever.isEmpty()) {
                                    DialogHelper.showOpenAppSettingDialog();
                                }
                            }
                        }).request();
//
                Toast.makeText(this, "点击了右边", Toast.LENGTH_LONG).show();
                break;
        }
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doBspatch();
            }
        }
    }

}
