package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;

/**
 * Created by ${user} on 2018/7/27
 */
public class ModifyPasswordContract {
    public interface View extends BaseActivityView {
        void modifyPasswordSuccess(OtherResponse otherResponse);
        void modifyPasswordFails(int code,String msg);

        void clearError();
        void setOldPasswordNullError(String msg);
        void setNewPasswordNullError(String msg);

        void setOldPasswordValidError(String msg);
        void setNewPasswordValidError(String msg);

        void setOldPasswordLengthValidError(String msg);
        void setNewPasswordLengthValidError(String msg);
    }


    public interface Present{
        void modifyPassword(String oldPassword,String newPassword );
    }
}
