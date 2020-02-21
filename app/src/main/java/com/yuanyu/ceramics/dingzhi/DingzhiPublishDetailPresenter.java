package com.yuanyu.ceramics.dingzhi;


import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DingzhiPublishDetailPresenter extends BasePresenter<DingzhiPublishDetailConstract.IDingzhiPublishDetailView> implements DingzhiPublishDetailConstract.IDingzhiPublishDetailPresenter{
    private DingzhiPublishDetailConstract.IDingzhiPublishDetailModel model;

    DingzhiPublishDetailPresenter() {model=new DingzhiPublishDetailModel();}

    @Override
    public void initData(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> ticaiList) {
        fenleiList.add(new FenleiTypeBean("原石"));
        fenleiList.add(new FenleiTypeBean("挂件"));
        fenleiList.add(new FenleiTypeBean("吊坠"));
        fenleiList.add(new FenleiTypeBean("把件"));
        fenleiList.add(new FenleiTypeBean("摆件"));
        fenleiList.add(new FenleiTypeBean("器皿"));
        fenleiList.add(new FenleiTypeBean("手镯"));
        fenleiList.add(new FenleiTypeBean("手串（链）"));
        fenleiList.add(new FenleiTypeBean("项链"));
        fenleiList.add(new FenleiTypeBean("饰品"));
        fenleiList.add(new FenleiTypeBean("杂项"));

        ticaiList.add(new FenleiTypeBean("神佛"));
        ticaiList.add(new FenleiTypeBean("瑞兽"));
        ticaiList.add(new FenleiTypeBean("仿古"));
        ticaiList.add(new FenleiTypeBean("山水"));
        ticaiList.add(new FenleiTypeBean("人物"));
        ticaiList.add(new FenleiTypeBean("花鸟"));
        ticaiList.add(new FenleiTypeBean("动物"));
        ticaiList.add(new FenleiTypeBean("其它"));
    }

    @Override
    public void dingzhiPublish(int useraccountid, String master_id, String detail, String useage, String birthday, int priceType, String fenlei, String ticai) {
        try {
            if(Integer.parseInt(master_id)<1){
                if(view!=null){view.showToast("请选择合作大师");}
                return;
            }
        }catch (Exception e){
            if(view!=null){view.showToast("请选择合作大师");}
            return;
        }
        if(detail.trim().length()==0){
            if(view!=null){view.showToast("请填写定制详细需求");}
            return;
        }
        if(fenlei.trim().length()==0){
            if(view!=null){view.showToast("请选择分类");}
            return;
        }
        if(ticai.trim().length()==0){
            if(view!=null){view.showToast("请选择题材");}
            return;
        }
        model.dingzhiPublish(useraccountid,master_id,detail,useage,birthday,priceType,fenlei,ticai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {
                        if(view!=null){view.dingzhiPublishSuccess();}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.dingzhiPublishFail(e);}
                    }
                });
    }
}
