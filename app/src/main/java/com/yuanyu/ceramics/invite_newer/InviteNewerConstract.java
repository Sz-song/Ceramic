package com.yuanyu.ceramics.invite_newer;


import android.graphics.Bitmap;

public interface InviteNewerConstract {
    interface IInviteNewerModel {

    }
    interface IInviteNewerView {

    }

    interface IInviteNewerPresenter {
        void ShareWechat(Bitmap bmp, String miniProgrampath, String url, String title);//分享微信
    }
}
