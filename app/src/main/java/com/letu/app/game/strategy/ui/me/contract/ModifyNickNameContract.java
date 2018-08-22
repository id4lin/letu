package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;

/**
 * Created by ${user} on 2018/7/27
 */
public class ModifyNickNameContract {
    public interface View extends BaseActivityView {
        void modifyNickNameSuccess(OtherResponse otherResponse);
        void modifyNickNameFails(int code,String msg);
        void setNickNameLengthValidError(String msg);
        void setErrorNull(String msg);

    }


    public interface Present{
        void modifyNickName(String nickName);
    }
}
