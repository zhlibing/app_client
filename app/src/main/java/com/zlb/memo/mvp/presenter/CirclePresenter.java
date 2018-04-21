package com.zlb.memo.mvp.presenter;

import android.view.View;

import com.zlb.memo.bean.CircleItem;
import com.zlb.memo.bean.CommentConfig;
import com.zlb.memo.bean.CommentItem;
import com.zlb.memo.bean.FavortItem;
import com.zlb.memo.mvp.contract.CircleContract;
import com.zlb.memo.mvp.modle.CircleModel;
import com.zlb.memo.listener.IDataRequestListener;
import com.zlb.memo.utils.DatasUtil;

import java.util.List;

/**
 * @author yiw
 * @ClassName: CirclePresenter
 * @Description: 通知model请求服务器和通知view更新
 * @date 2015-12-28 下午4:06:03
 */
public class CirclePresenter implements CircleContract.Presenter {
    private CircleModel circleModel;
    private CircleContract.View view;

    public CirclePresenter(CircleContract.View view) {
        circleModel = new CircleModel();
        this.view = view;
    }

    public void loadData(int loadType) {

        List<CircleItem> datas = DatasUtil.createCircleDatas();
        if (view != null) {
            view.update2loadData(loadType, datas);
        }
    }


    /**
     * @param circleId
     * @return void    返回类型
     * @throws
     * @Title: deleteCircle
     * @Description: 删除动态
     */
    public void deleteCircle(final String circleId) {
        circleModel.deleteCircle(new IDataRequestListener() {

            @Override
            public void loading(String msg) {

            }

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteCircle(circleId);
                }
            }

            @Override
            public void loadFail(Object object) {

            }
        });
    }

    /**
     * @param circlePosition
     * @return void    返回类型
     * @throws
     * @Title: addFavort
     * @Description: 点赞
     */
    public void addFavort(final int circlePosition) {
        circleModel.addFavort(new IDataRequestListener() {

            @Override
            public void loading(String msg) {

            }

            @Override
            public void loadSuccess(Object object) {
                FavortItem item = DatasUtil.createCurUserFavortItem();
                if (view != null) {
                    view.update2AddFavorite(circlePosition, item);
                }

            }

            @Override
            public void loadFail(Object object) {

            }
        });
    }

    /**
     * @param @param circlePosition
     * @param @param favortId
     * @return void    返回类型
     * @throws
     * @Title: deleteFavort
     * @Description: 取消点赞
     */
    public void deleteFavort(final int circlePosition, final String favortId) {
        circleModel.deleteFavort(new IDataRequestListener() {

            @Override
            public void loading(String msg) {

            }

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteFavort(circlePosition, favortId);
                }
            }

            @Override
            public void loadFail(Object object) {

            }
        });
    }

    /**
     * @param content
     * @param config  CommentConfig
     * @return void    返回类型
     * @throws
     * @Title: addComment
     * @Description: 增加评论
     */
    public void addComment(final String content, final CommentConfig config) {
        if (config == null) {
            return;
        }
        circleModel.addComment(new IDataRequestListener() {

            @Override
            public void loading(String msg) {

            }

            @Override
            public void loadSuccess(Object object) {
                CommentItem newItem = null;
                if (config.commentType == CommentConfig.Type.PUBLIC) {
                    newItem = DatasUtil.createPublicComment(content);
                } else if (config.commentType == CommentConfig.Type.REPLY) {
                    newItem = DatasUtil.createReplyComment(config.replyUser, content);
                }
                if (view != null) {
                    view.update2AddComment(config.circlePosition, newItem);
                }
            }

            @Override
            public void loadFail(Object object) {

            }

        });
    }

    /**
     * @param @param circlePosition
     * @param @param commentId
     * @return void    返回类型
     * @throws
     * @Title: deleteComment
     * @Description: 删除评论
     */
    public void deleteComment(final int circlePosition, final String commentId) {
        circleModel.deleteComment(new IDataRequestListener() {

            @Override
            public void loading(String msg) {

            }

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteComment(circlePosition, commentId);
                }
            }

            @Override
            public void loadFail(Object object) {

            }

        });
    }

    /**
     * @param commentConfig
     */
    public void showEditTextBody(CommentConfig commentConfig) {
        if (view != null) {
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
    }


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle() {
        this.view = null;
    }
}
