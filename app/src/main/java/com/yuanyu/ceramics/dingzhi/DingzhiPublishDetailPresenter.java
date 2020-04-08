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
    public void initData(List<FenleiTypeBean> fenleiList, List<FenleiTypeBean> zhonglei ,List<FenleiTypeBean> waiguanList) {
        fenleiList.add(new FenleiTypeBean("花瓶"));
        fenleiList.add(new FenleiTypeBean("雕塑品"));
        fenleiList.add(new FenleiTypeBean("吊坠"));
        fenleiList.add(new FenleiTypeBean("园林陶瓷"));
        fenleiList.add(new FenleiTypeBean("器皿"));
        fenleiList.add(new FenleiTypeBean("相框"));
        fenleiList.add(new FenleiTypeBean("壁画"));
        fenleiList.add(new FenleiTypeBean("陈设品"));
        fenleiList.add(new FenleiTypeBean("其他"));

        zhonglei.add(new FenleiTypeBean("素瓷"));
        zhonglei.add(new FenleiTypeBean("青瓷"));
        zhonglei.add(new FenleiTypeBean("黑瓷"));
        zhonglei.add(new FenleiTypeBean("白瓷"));
        zhonglei.add(new FenleiTypeBean("青白瓷"));
        zhonglei.add(new FenleiTypeBean("其它"));

        waiguanList.add(new FenleiTypeBean("神佛"));
        waiguanList.add(new FenleiTypeBean("瑞兽"));
        waiguanList.add(new FenleiTypeBean("仿古"));
        waiguanList.add(new FenleiTypeBean("山水"));
        waiguanList.add(new FenleiTypeBean("人物"));
        waiguanList.add(new FenleiTypeBean("花鸟"));
        waiguanList.add(new FenleiTypeBean("动物"));
        waiguanList.add(new FenleiTypeBean("其它"));
    }

    @Override
    public void dingzhiPublish(String useraccountid, String master_id, String detail, String useage,  int priceType, String fenlei, String zhonglei,String waiguan) {
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
        if(zhonglei.trim().length()==0){
            if(view!=null){view.showToast("请选择种类");}
            return;
        }
        if(waiguan.trim().length()==0){
            if(view!=null){view.showToast("请选择外观");}
            return;
        }
        model.dingzhiPublish(useraccountid,master_id,detail,useage,priceType,fenlei,zhonglei,waiguan)
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
