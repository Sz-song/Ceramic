package com.yuanyu.ceramics.center_circle;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.DynamicBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface SquareDynamicArticleConstract {
    interface IHotFragmentChildModel {
        Observable<BaseResponse<List<DynamicBean>>> getList(String useraccountid, int type, int page);
        Observable<BaseResponse<Boolean>> focus(String useraccountid, String userid);
        Observable<BaseResponse<String[]>> dianZan(String useraccountid, int type, String id, String source_uid);
        Observable<BaseResponse<String[]>> shield(String useraccountid, int type, String id);//屏蔽
        Observable<BaseResponse<Boolean>> blackList(String useraccountid, String userid);//拉黑
        Observable<BaseResponse<String[]>>  deleteDynamic(String useraccountid, String dynamic_id);//删除动态
        Observable<BaseResponse<String[]>>  deleteArticle(String useraccountid, String article_id);//删除动态
    }
    interface IHotFragmentChildView {
        void getListSuccess(List<DynamicBean> beans);
        void getListFail(ExceptionHandler.ResponeThrowable e);

        void focusSuccess(Boolean b, String userid);
        void focusFail(ExceptionHandler.ResponeThrowable e);

        void dianZanSuccess(int position);
        void dianZanFail(ExceptionHandler.ResponeThrowable e);

        void shieldSuccess(int position);
        void shieldFail(ExceptionHandler.ResponeThrowable e);

        void blackListSuccess(Boolean b);
        void blackListFail(ExceptionHandler.ResponeThrowable e);

        void deleteSuccess(int position);
        void deleteFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IHotFragmentChildPresenter {
        void getList(String useraccountid, int type, int page);
        void focus(String useraccountid, String userid);
        void dianZan(String useraccountid, int type, String id, String source_uid, int position);
        void shield(String useraccountid, int type, String id, int position);//屏蔽
        void blackList(String useraccountid, String userid);//拉黑
        void deleteDynamic(String useraccountid, String dynamic_id, int position);//删除动态
        void deleteArticle(String useraccountid, String article_id, int position);//删除动态
    }
}
