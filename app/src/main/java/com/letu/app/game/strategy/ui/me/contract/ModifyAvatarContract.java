package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;

/**
 * Created by ${user} on 2018/7/27
 */
public class ModifyAvatarContract {
    public interface View extends BaseActivityView {
        void modifyAvatarSuccess(String imagePath);
        void modifyAvatarFails(int code,String msg);

    }


    public interface Present{
        void modifyAvatar(String filePath);
    }
}
