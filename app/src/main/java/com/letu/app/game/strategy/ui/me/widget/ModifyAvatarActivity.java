package com.letu.app.game.strategy.ui.me.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.bottompopfragmentmenu.BottomMenuFragment;
import com.letu.app.game.strategy.ui.common.bottompopfragmentmenu.MenuItem;
import com.letu.app.game.strategy.ui.common.bottompopfragmentmenu.MenuItemOnClickListener;
import com.letu.app.game.strategy.ui.common.imagepicker.ImagePicker;
import com.letu.app.game.strategy.ui.common.imagepicker.cropper.CropImage;
import com.letu.app.game.strategy.ui.common.imagepicker.cropper.CropImageView;
import com.letu.app.game.strategy.ui.common.permission.PermissionPageManager;
import com.letu.app.game.strategy.ui.me.contract.ModifyAvatarContract;
import com.letu.app.game.strategy.ui.me.presenter.ModifyAvatarPresenter;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyAvatarActivity extends BaseActivity<ModifyAvatarPresenter> implements ModifyAvatarContract.View {

    @BindView(R.id.modify_avatar_select)
    TextView modifyAvatarSelect;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.modify_avatar)
    SimpleDraweeView modifyAvatar;

    private ImagePicker imagePicker = new ImagePicker();

    private AlertDialog permissionAlertDialog=null;

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_avatar);
        mPresenter.attachView(this);
        setSupportActionBar(toolbar);   //必须使用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUserInfoPage();
            }
        });

        // 设置标题
        imagePicker.setTitle("设置头像");
        // 设置是否裁剪图片
        imagePicker.setCropImage(true);


        modifyAvatar.getHierarchy().setRoundingParams(RoundingParams.asCircle());

        setAvatar(SPManager.getInstance().getUserInfo().getAvatarUrl());


    }

    @OnClick(R.id.modify_avatar_select)
    public void onViewClicked() {
        //        showBottomMenu();
        startChooser();
    }

    @OnClick(R.id.modify_avatar)
    public void onModifyAvatarClicked() {
        //        showBottomMenu();
        startChooser();
    }

    @Override
    public void onBackPressed() {
        gotoUserInfoPage();
        super.onBackPressed();
    }

    private void gotoUserInfoPage() {
        setResult(Constant.CODE_RESPONSE_MODIFY_AVATAR);
        finish();
    }

    private void showBottomMenu() {
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();

        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("拍照");
        //        menuItem1.setStyle(MenuItem.MenuItemStyle.COMMON);
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setText("从手机相册选择");
        //        menuItem2.setStyle(MenuItem.MenuItemStyle.COMMON);
        menuItem2.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem2) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {
                Log.i("", "onClickMenuItem: ");
                startChooser();
            }
        });
        menuItemList.add(menuItem1);
        menuItemList.add(menuItem2);

        bottomMenuFragment.setMenuItems(menuItemList);

        bottomMenuFragment.show(getFragmentManager(), "BottomMenuFragment");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    private void startChooser() {
        // 启动图片选择器
        imagePicker.startChooser(this, new ImagePicker.Callback() {
            // 选择图片回调
            @Override
            public void onPickImage(Uri imageUri) {

            }

            // 裁剪图片回调
            @Override
            public void onCropImage(Uri imageUri) {
                mPresenter.modifyAvatar(imageUri.getPath());
            }

            // 自定义裁剪配置
            @Override
            public void cropConfig(CropImage.ActivityBuilder builder) {
                builder
                        // 是否启动多点触摸
                        .setMultiTouchEnabled(true)
                        .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                        // 设置网格显示模式
                        .setGuidelines(CropImageView.Guidelines.ON)
                        // 圆形/矩形
                        .setCropShape(CropImageView.CropShape.OVAL)
                        // 调整裁剪后的图片最终大小
                        .setRequestedSize(300, 300)
                        // 宽高比
                        .setAspectRatio(1, 1);

            }

            // 用户拒绝授权回调
            @Override
            public void onPermissionDenied(int requestCode, String[] permissions,
                                           int[] grantResults) {
                for(String permission:permissions){

                    Log.d(TAG, requestCode+",permission:"+permission+ ", is denied.  "+grantResults[0]);
                }
                if(null!=permissionAlertDialog&&permissionAlertDialog.isShowing()){
                    return;
                }

                AlertDialog.Builder permissionAlertDialogBuilder=new AlertDialog.Builder(mContext);
                permissionAlertDialogBuilder.setTitle("授权提示：");
                permissionAlertDialogBuilder.setMessage("该功能需要使用相机权限。\n请到\"设置\">\"应用\">\"权限\"中配置权限。");
                permissionAlertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        PermissionPageManager.getInstance().jumpPermissionPage(mContext);
                    }
                });
                permissionAlertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                permissionAlertDialog=permissionAlertDialogBuilder.create();
                permissionAlertDialog.show();

            }
        });
    }

    @Override
    public void modifyAvatarSuccess(String imagePath) {
        ToastUtil.toastShort("头像已上传");
        setAvatar(imagePath);
    }

    @Override
    public void modifyAvatarFails(int code, String msg) {
        ToastUtil.toastShort("头像上传失败");
    }

    private void setAvatar(String avatarUrl){
        if(LeTuUtils.isNull(avatarUrl)){
            return;
        }
        modifyAvatar.setImageURI(avatarUrl);
    }
}
