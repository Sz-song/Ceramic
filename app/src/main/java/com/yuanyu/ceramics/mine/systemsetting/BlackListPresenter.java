package com.yuanyu.ceramics.mine.systemsetting;

import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BlackListPresenter extends BasePresenter<BlackListConstract.IBlackListView> implements BlackListConstract.IBlackListPresenter {
    private BlackListConstract.IBlackListModel model;
    BlackListPresenter() { model=new BlackListModel();}
    @Override
    public void getBlacklist(String useraccountid) {
        TIMFriendshipManager.getInstance().getBlackList(new TIMValueCallBack<List<TIMFriend>>() {
            @Override
            public void onError(int i, String s) {
                if(view!=null){
                    view.showToast("获取失败");
                }
            }
            @Override
            public void onSuccess(List<TIMFriend> timFriends) {
                List<BlackListBean> blackListBeanList =new ArrayList<>();
                for(int i=0;i<timFriends.size();i++){
                    BlackListBean bean=new BlackListBean(timFriends.get(i).getTimUserProfile().getFaceUrl(),timFriends.get(i).getIdentifier(),timFriends.get(i).getTimUserProfile().getNickName());
                    blackListBeanList.add(bean);
                }
                model.getBlacklist(useraccountid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(new HttpServiceInstance.ErrorTransformer<>())
                        .subscribe(new BaseObserver<List<BlackListBean>>() {
                            @Override
                            public void onNext(List<BlackListBean> list) {
                                if(view!=null){view.getBlacklistSuccess(list);}
                                for(int i=0;i<blackListBeanList.size();i++){
                                    L.e("black user"+blackListBeanList.get(i).getBlackuserid());
                                    if(!list.contains(blackListBeanList.get(i))){
                                        addBlacklist(useraccountid,blackListBeanList.get(i).getBlackuserid());
                                    }
                                }
                                for(int i=0;i<list.size();i++){
                                    L.e("black  uid"+list.get(i).getBlackuserid());
                                    if(!blackListBeanList.contains(list.get(i))){
                                        addBlacklist(useraccountid,list.get(i).getBlackuserid());
                                    }
                                }
                            }
                            @Override
                            public void onError(ExceptionHandler.ResponeThrowable e) {
                                L.e("response" + e.message + e.status);
                                if(view!=null){view.showToast(e.message);}
                            }
                        });
            }
        });
    }

    @Override
    public void removeBlacklist(String useraccountid, String blackUser,int position) {
        List<String> users=new ArrayList<>();
        users.add(blackUser);
        TIMFriendshipManager.getInstance().deleteBlackList(users, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                L.e("error code:"+i+"  msg:"+ s);
                if(view!=null){ view.showToast(s);}
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                model.removeBlacklist(useraccountid,blackUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                        .subscribe(new BaseObserver<String[]>() {
                            @Override
                            public void onNext(String[] strings) {
                                if(view!=null){view.removeBlacklistSuccess(position);}
                            }
                            @Override
                            public void onError(ExceptionHandler.ResponeThrowable e) {
                                if(view!=null){ view.showToast(e.message);}
                            }
                        });
            }
        });
    }

    @Override
    public void addBlacklist(String useraccountid, String blackUser) {
        List<String> users=new ArrayList<>();
        users.add(blackUser);
        TIMFriendshipManager.getInstance().addBlackList(users, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                L.e("error code:"+i+"  msg:"+ s);
            }
            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                model.addBlacklist(useraccountid,blackUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                        .subscribe(new BaseObserver<Boolean>() {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                L.e(aBoolean+"拉黑成功");
                            }
                            @Override
                            public void onError(ExceptionHandler.ResponeThrowable e) {
                                L.e(e.message);
                            }
                        });
            }
        });
    }
}
