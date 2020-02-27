package com.yuanyu.ceramics.fenlei;


import java.util.List;

public interface FenLeiConstract {
    interface IFenleiModel {

    }
    interface IFenleiView {

    }

    interface IFenleiPresenter {
        void initList(List<ClassifyBean> classify_list, List<FenLeiBean> fenleilist, List<FenLeiBean> zhongleilist, List<FenLeiBean> shapelist);
        void setShowList(List<FenLeiBean> showlist,List<FenLeiBean> selectlist);
        void getStringList(List<String> stringList,List<FenLeiBean> fenleilist,List<FenLeiBean> zhongleilist,List<FenLeiBean> shapelist);
        void clearList(List<FenLeiBean> list);
    }
}
