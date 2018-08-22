package com.letu.app.game.strategy.dagger;

import com.letu.app.baselib.dagger.AppComponent;
import com.letu.app.baselib.dagger.PerActivity;
import com.letu.app.game.strategy.ui.game.widget.GameDetailActivity;
import com.letu.app.game.strategy.ui.login.widget.ForgetPasswordActivity;
import com.letu.app.game.strategy.ui.login.widget.LoginActivity;
import com.letu.app.game.strategy.ui.main.widget.AboutFragment;
import com.letu.app.game.strategy.ui.main.widget.GameFragment;
import com.letu.app.game.strategy.ui.main.widget.MainActivity;
import com.letu.app.game.strategy.ui.main.widget.MeFragment;
import com.letu.app.game.strategy.ui.main.widget.StrategyFragment;
import com.letu.app.game.strategy.ui.me.widget.CollectListActivity;
import com.letu.app.game.strategy.ui.me.widget.GameListActivity;
import com.letu.app.game.strategy.ui.me.widget.ModifyAvatarActivity;
import com.letu.app.game.strategy.ui.me.widget.ModifyNickNameActivity;
import com.letu.app.game.strategy.ui.me.widget.ModifyPasswordActivity;
import com.letu.app.game.strategy.ui.me.widget.PromoterActivity;
import com.letu.app.game.strategy.ui.me.widget.PromoterDetailActivity;
import com.letu.app.game.strategy.ui.me.widget.WebViewActivity;
import com.letu.app.game.strategy.ui.protocol.widget.ProtocolActivity;
import com.letu.app.game.strategy.ui.register.widget.RegisterActivity;
import com.letu.app.game.strategy.ui.strategy.widget.CommentEditActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailNewActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyEditActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyListActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,modules = {AppBaseModule.class})
public interface AppBaseComponent {
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(ProtocolActivity ProtocolActivity);
    void inject(StrategyDetailActivity strategyDetailActivity);
    void inject(CommentEditActivity commentEditActivity);
    void inject(GameDetailActivity gameDetailActivity);
    void inject(StrategyListActivity strategyListActivity);
    void inject(StrategyEditActivity strategyEditActivity);
    void inject(GameListActivity gameListActivity);
    void inject(CollectListActivity collectListActivity);
    void inject(WebViewActivity webViewActivity);
    void inject(ForgetPasswordActivity forgetPasswordActivity);
    void inject(ModifyPasswordActivity modifyPasswordActivity);
    void inject(ModifyAvatarActivity modifyAvatarActivity);
    void inject(ModifyNickNameActivity modifyNickNameActivity);
    void inject(PromoterActivity promoterActivity);
    void inject(PromoterDetailActivity promoterDetailActivity);
    void inject(StrategyDetailNewActivity strategyDetailNewActivity);


    void inject(StrategyFragment strategyFragment);
    void inject(MeFragment meFragment);
    void inject(AboutFragment aboutFragment);
    void inject(GameFragment gameFragment);

}
