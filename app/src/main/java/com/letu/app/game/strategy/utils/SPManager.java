package com.letu.app.game.strategy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.letu.app.game.strategy.bean.UserInfo;
import com.letu.app.game.strategy.constant.SPConstant;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;

import net.nightwhistler.htmlspanner.TextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ${user} on 2018/7/15
 */
public class SPManager {
    public static final String SP_DEFAULT_NAME = "letu_default_name";

    public SPManager() {

    }

    private SPManager spm;
    private SharedPreferences sp;
    /*
     * 保存手机里面的名字
     */
    private SharedPreferences.Editor editor;


    public enum Singleton {
        INSTANCE;
        private SPManager instance;

        private Singleton() {
            instance = new SPManager();
        }

        public SPManager getInstance() {
            return instance;
        }
    }

    public static SPManager getInstance() {
        return Singleton.INSTANCE.getInstance();
    }


    /**
     * 初始化SharedPreferencesUtil,只需要初始化一次，建议在Application中初始化
     *
     * @param context 上下文对象
     * @param name    SharedPreferences Name
     */
    public void init(Context context, String name) {
        if (null == name || TextUtils.isEmpty(name)) {
            name = SP_DEFAULT_NAME;
        }
        sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存数据到SharedPreferences
     *
     * @param key   键
     * @param value 需要保存的数据
     * @return 保存结果
     */
    public boolean put(String key, Object value) {
        boolean result;
        //        SharedPreferences.Editor editor = sp.edit();
        String type = value.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    editor.putBoolean(key, (Boolean) value);
                    break;
                case "Long":
                    editor.putLong(key, (Long) value);
                    break;
                case "Float":
                    editor.putFloat(key, (Float) value);
                    break;
                case "String":
                    editor.putString(key, (String) value);
                    break;
                case "Integer":
                    editor.putInt(key, (Integer) value);
                    break;
                default:
                    Gson gson = new Gson();
                    String json = gson.toJson(value);
                    editor.putString(key, json);
                    break;
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取SharedPreferences中保存的数据
     *
     * @param key          键
     * @param defaultValue 获取失败默认值
     * @return 从SharedPreferences读取的数据
     */
    public Object get(String key, Object defaultValue) {
        Object result;
        String type = defaultValue.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    result = sp.getBoolean(key, (Boolean) defaultValue);
                    break;
                case "Long":
                    result = sp.getLong(key, (Long) defaultValue);
                    break;
                case "Float":
                    result = sp.getFloat(key, (Float) defaultValue);
                    break;
                case "String":
                    result = sp.getString(key, (String) defaultValue);
                    break;
                case "Integer":
                    result = sp.getInt(key, (Integer) defaultValue);
                    break;
                default:
                    Gson gson = new Gson();
                    String json = sp.getString(key, "");
                    if (!json.equals("") && json.length() > 0) {
                        result = gson.fromJson(json, defaultValue.getClass());
                    } else {
                        result = defaultValue;
                    }
                    break;
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public <T> boolean putListData(String key, List<T> list) {
        boolean result;
        String type = list.get(0).getClass().getSimpleName();
        //        SharedPreferences.Editor editor = sp.edit();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = sp.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public <K, V> boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        //        SharedPreferences.Editor editor = sp.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @return HashMap
     */
    public <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = sp.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        Log.e("SharedPreferencesUtil", obj.toString());
        return map;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }


    /**
     * 设置用户信息
     * @param userInfo
     * @return
     */
    public boolean putUserInfo(UserInfo userInfo) {
        if (null == userInfo) {
            userInfo = new UserInfo();
        }
        return put(SPConstant.SP_KEY_USER_INFO, userInfo);
    }

    /**
     * 获取用户信息
     * @return
     */
    public UserInfo getUserInfo() {
        Object obj =  get(SPConstant.SP_KEY_USER_INFO, new UserInfo());
        return null == obj ? null : (UserInfo)obj;
    }


    /**
     * 设置用户登录状态
     * @param isLogin
     * @return
     */
    public boolean putLoginState(boolean isLogin){
        return put(SPConstant.SP_KEY_LOGIN_STATES,isLogin);
    }

    /**
     * 获取用户登录状态
     * @return
     */
    public boolean isLogin(){
        return (boolean)get(SPConstant.SP_KEY_LOGIN_STATES,false);
    }

    /**
     * 设置用户登录token信息
     * @param token
     * @return
     */
    public boolean putLoginToken(String token){
        return put(SPConstant.SP_KEY_LOGIN_TOKEN,token);
    }

    /**
     * 获取用户登录token信息
     * @return
     */
    public String getLoginToken(){
        return (String)get(SPConstant.SP_KEY_LOGIN_TOKEN,"");
    }


    /**
     * 保存用户登录信息
     * @param loginResponse
     */
    public void saveUserLoginInfos(LoginResponse loginResponse){
        SPManager.getInstance().putLoginState(true);
        SPManager.getInstance().putLoginToken(loginResponse.getToken());

        UserInfo userInfo=new UserInfo();
        userInfo.setAvatarUrl(loginResponse.getAvatarUrl());
        userInfo.setNickName(loginResponse.getNickName());
        userInfo.setPhone(loginResponse.getPhone());
        userInfo.setToken(loginResponse.getToken());
        userInfo.setUserId(loginResponse.getUserId());
        userInfo.setIsPromoter(loginResponse.getIsSpreader());
        putUserInfo(userInfo);
    }

    public void modifyNickName(String nickName){
        UserInfo userInfo=getUserInfo();
        userInfo.setNickName(nickName);
        putUserInfo(userInfo);
    }

    public void modifyAvatar(String avatar){
        UserInfo userInfo=getUserInfo();
        userInfo.setAvatarUrl(avatar);
        putUserInfo(userInfo);
    }

    /**
     * 清除用户登录信息
     */
    public void clearUserInfo(){
        putLoginState(false);
        putLoginToken("");
        putUserInfo(new UserInfo());
    }

}
