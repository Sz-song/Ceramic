package com.yuanyu.ceramics.fenlei;

import com.yuanyu.ceramics.base.BasePresenter;

import java.util.List;

public class FenLeiPresenter extends BasePresenter<FenLeiConstract.IFenleiView> implements FenLeiConstract.IFenleiPresenter {

    @Override
    public void initList(List<ClassifyBean> classify_list, List<FenLeiBean> fenleilist, List<FenLeiBean> zhongleilist, List<FenLeiBean> shapelist) {
        classify_list.add(new ClassifyBean("分类",true));
        classify_list.add(new ClassifyBean("种类",false));
        classify_list.add(new ClassifyBean("外观",false));

        fenleilist.add(new FenLeiBean("花瓶",false));
        fenleilist.add(new FenLeiBean("雕塑品",false));
        fenleilist.add(new FenLeiBean("园林陶瓷",false));
        fenleilist.add(new FenLeiBean("器皿",false));
        fenleilist.add(new FenLeiBean("相框",false));
        fenleilist.add(new FenLeiBean("壁画",false));
        fenleilist.add(new FenLeiBean("陈设品",false));
        fenleilist.add(new FenLeiBean("其它",false));

        zhongleilist.add(new FenLeiBean("素瓷",false));
        zhongleilist.add(new FenLeiBean("青瓷",false));
        zhongleilist.add(new FenLeiBean("黑瓷",false));
        zhongleilist.add(new FenLeiBean("白瓷",false));
        zhongleilist.add(new FenLeiBean("青白瓷",false));
        zhongleilist.add(new FenLeiBean("其它",false));

        shapelist.add(new FenLeiBean("神佛",false));
        shapelist.add(new FenLeiBean("瑞兽",false));
        shapelist.add(new FenLeiBean("仿古",false));
        shapelist.add(new FenLeiBean("山水",false));
        shapelist.add(new FenLeiBean("人物",false));
        shapelist.add(new FenLeiBean("花鸟",false));
        shapelist.add(new FenLeiBean("动物",false));
        shapelist.add(new FenLeiBean("其它",false));
    }

    @Override
    public void setShowList(List<FenLeiBean> showlist, List<FenLeiBean> selectlist) {
        showlist.clear();
        showlist.addAll(selectlist);
    }

    @Override
    public void getStringList(List<String> stringList, List<FenLeiBean> fenleilist, List<FenLeiBean> zhongleilist, List<FenLeiBean> shapelist) {

        stringList.clear();
        StringBuilder fenlei=new StringBuilder();
        for(int i=0;i<fenleilist.size();i++){
            if(fenleilist.get(i).isSelect()){
                if(fenlei.toString().length()>0){
                    fenlei.append("、").append(fenleilist.get(i).getName());
                }else{
                    fenlei.append(fenleilist.get(i).getName());
                }
            }
        }
        stringList.add(fenlei.toString());

        StringBuilder zhonglei=new StringBuilder();
        for(int i=0;i<zhongleilist.size();i++){
            if(zhongleilist.get(i).isSelect()){
                if(zhonglei.toString().length()>0){
                    zhonglei.append("、").append(zhongleilist.get(i).getName());
                }else{
                    zhonglei.append(zhongleilist.get(i).getName());
                }
            }
        }
        stringList.add(zhonglei.toString());

        StringBuilder pise=new StringBuilder();
        for(int i=0;i<shapelist.size();i++){
            if(shapelist.get(i).isSelect()){
                if(pise.toString().length()>0){
                    pise.append("、").append(shapelist.get(i).getName());
                }else{
                    pise.append(shapelist.get(i).getName());
                }
            }
        }
        stringList.add(pise.toString());
    }


    @Override
    public void clearList(List<FenLeiBean> list) {
        for(int i=0;i<list.size();i++){
            list.get(i).setSelect(false);
        }
    }
}
