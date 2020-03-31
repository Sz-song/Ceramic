package com.yuanyu.ceramics.broadcast.push;

public interface LivePushConstract {
    interface ILivePushModel{

    }
    interface ILivePushView{
        void initLivePush();
        void switchFilter(int position);//切换滤镜
        void switchSharpness(int postion);//切换清晰度
    }
    interface ILivePushPresenter{

    }
}
