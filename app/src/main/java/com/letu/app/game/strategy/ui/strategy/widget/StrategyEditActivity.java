package com.letu.app.game.strategy.ui.strategy.widget;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.glide.MyGlideEngine;
import com.letu.app.game.strategy.ui.common.permission.PermissionPageManager;
import com.letu.app.game.strategy.ui.common.permission.RxPermissions;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyResponse;
import com.letu.app.game.strategy.ui.strategy.contract.StrategyEditContract;
import com.letu.app.game.strategy.ui.strategy.presenter.StrategyEditPresenter;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SDCardUtil;
import com.sendtion.xrichtext.RichTextEditor;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class StrategyEditActivity extends BaseActivity<StrategyEditPresenter> implements StrategyEditContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.strategy_edit_title_tv)
    TextInputEditText strategyEditTitleTv;
    @BindView(R.id.strategy_edit_content_rt)
    RichTextEditor strategyEditContentRt;
    @BindView(R.id.strategy_edit_content_tv)
    TextInputEditText strategyEditContentTv;
    @BindView(R.id.strategy_edit_title_TextInputLayout)
    TextInputLayout strategyEditTitleTextInputLayout;
    @BindView(R.id.strategy_edit_content_TextInputLayout)
    TextInputLayout strategyEditContentTextInputLayout;

    private String gameId;
    private ProgressDialog insertDialog;

    private RxPermissions rxPermissions = null;
//    private PermissionPageManager permissionPageManager;
    private AlertDialog permissionAlertDialog=null;
    private boolean isSelectting=false;

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
        setContentView(R.layout.activity_strategy_edit);
        mPresenter.attachView(this);

        Intent intent = getIntent();

        gameId = intent.getStringExtra(Constant.KEY_INTENT_GAME_ID);

        setSupportActionBar(toolbar);

        rxPermissions = new RxPermissions(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);

//        permissionPageManager=new PermissionPageManager(this);
        strategyEditContentRt.setOnRtImageClickListener(new RichTextEditor.OnRtImageClickListener() {
            @Override
            public void onRtImageClick(String imagePath) {

            }
        });

        strategyEditContentRt.setOnRtImageDeleteListener(new RichTextEditor.OnRtImageDeleteListener() {
            @Override
            public void onRtImageDelete(String imagePath) {
                if (!LeTuUtils.isNull(imagePath)) {
                   ToastUtil.toastShort("已删除");
                }
            }
        });

        openSoftKeyInput();//打开软键盘显示


    }

    @Override
    public void addStrategySuccess(StrategyResponse strategyResponse) {
        ToastUtil.toastShort("已提交");
        finish();
    }

    @Override
    public void addStrategyFails(int code, String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void addStrategyComplete() {

    }

    @Override
    public void setNullErrorTitle(String msg) {
        strategyEditTitleTextInputLayout.setError(msg);
    }

    @Override
    public void setNullErrorContent(String msg) {
        strategyEditContentTextInputLayout.setError(msg);
    }

    @Override
    public void clearAllError() {
        strategyEditTitleTextInputLayout.setError("");
        strategyEditContentTextInputLayout.setError("");
    }

    @Override
    public void setFileSizeError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void uploadImageSuccess(String imagePath) {
        if (insertDialog != null && insertDialog.isShowing()) {
            insertDialog.dismiss();
        }
        strategyEditContentRt.insertImage(imagePath, strategyEditContentRt.getMeasuredWidth());
        ToastUtil.toastShort("图片插入成功");
    }

    @Override
    public void uploadImageFails(int code, String msg) {
        closeInsertDialog("图片插入失败："+msg);
    }

    @Override
    public void uploadImageError(Throwable e) {
        closeInsertDialog(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.strategy_editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_draft:
                onSaveDraftClick();
                break;
            case R.id.action_insert_image:
                onInsertImageClick();
                break;
            case R.id.action_post:
                onSubmitClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isSelectting=false;
        if(resultCode==RESULT_OK&&requestCode==Constant.CODE_REQUEST_PHOTO_CHOOSE){
            insertImagesSync(data);
        }
    }

    private void insertImagesSync(final Intent data) {
        insertDialog.show();

        strategyEditContentRt.measure(0, 0);
        List<Uri> selectedImageUriList = Matisse.obtainResult(data);
        for (Uri imageUri:selectedImageUriList){
            String imagePath = SDCardUtil.getFilePathFromUri(this,  imageUri);
            Luban.with(this)
                    .load(imagePath)
                    .ignoreBy(100)
                    //.setTargetDir(imagePath)
//                    .filter(new CompressionPredicate() {
//                        @Override
//                        public boolean apply(String path) {
//                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                        }
//                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            mPresenter.uploadImage(file.getAbsolutePath());
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            if (insertDialog != null && insertDialog.isShowing()) {
                                insertDialog.dismiss();
                            }
                            closeInsertDialog(null);
                        }
                    }).launch();
        }
    }

    private void onInsertImageClick() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        closeSoftKeyInput();//关闭软键盘

        rxPermissions
//                .requestEach(Manifest.permission_group.STORAGE)
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission->{
                    if(permission.granted){
                        // 用户已经同意该权限
                        Log.d(TAG, permission.name + " is granted.");
                        if(isSelectting){
                            return;
                        }
                        isSelectting=true;
                        callGallery();

                    }else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Log.d(TAG, permission.name + " is denied. More info should be provided.");
                    }else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Log.d(TAG, permission.name + " is denied.");

                        if(null!=permissionAlertDialog&&permissionAlertDialog.isShowing()){
                            return;
                        }
                        AlertDialog.Builder permissionAlertDialogBuilder=new AlertDialog.Builder(this);
                        permissionAlertDialogBuilder.setTitle("授权提示：");
                        permissionAlertDialogBuilder.setMessage("该功能需要使用存储权限。\n请到\"设置\">\"应用\">\"权限\"中配置权限。");
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

    private void onSaveDraftClick() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

    }

    private void closeInsertDialog(String msg){
        if (insertDialog != null && insertDialog.isShowing()) {
            insertDialog.dismiss();
        }
        if(LeTuUtils.isNull(msg)){
            msg="图片插入失败";
        }
        ToastUtil.toastShort(msg);
    }

    private void onSubmitClick() {
        if (LeTuUtils.isFastClick()) {
            return;
        }

        mPresenter.addStrategy(gameId, strategyEditTitleTv.getText().toString(), getEditData());
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        List<RichTextEditor.EditData> editList = strategyEditContentRt.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                String inputStr=itemData.inputStr.replaceAll(" ","&nbsp;");
                inputStr=inputStr.replaceAll("\r\n","<br>");
                 inputStr=inputStr.replaceAll("\n","<br>");
                content.append(inputStr);
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
            }
        }

        //&nbsp;
//        Log.i(TAG,"=========strategy content:"+content.toString());
        return content.toString();
    }


    /**
     * 关闭软键盘
     */
    private void closeSoftKeyInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        //boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (imm.isActive()){
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.hideSoftInputFromInputMethod();//据说无效
            //imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0); //强制隐藏键盘
            //如果输入法在窗口上已经显示，则隐藏，反之则显示
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开软键盘
     */
    private void openSoftKeyInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        //boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (!imm.isActive()){
            strategyEditContentRt.requestFocus();
            //第二个参数可设置为0
            //imm.showSoftInput(et_content, InputMethodManager.SHOW_FORCED);//强制显示
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),
                    InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 调用图库选择
     */
    private void callGallery(){
        //        //调用系统图库
        //        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");// 相片类型
        //        startActivityForResult(intent, 1);

        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示MimeType.allOf()
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(1)//最大选择数量为9
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片显示表格的大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new MyGlideEngine())//图片加载方式，Glide4需要自定义实现
                .capture(false) //是否提供拍照功能，兼容7.0系统需要下面的配置
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                //.captureStrategy(new CaptureStrategy(true,"com.letu.app.game.strategy..imagePicker.provider"))//存储到哪里
                .forResult(Constant.CODE_REQUEST_PHOTO_CHOOSE);//请求码
    }
}
