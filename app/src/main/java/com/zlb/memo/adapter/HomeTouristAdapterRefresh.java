package com.zlb.memo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhang.lib.SlantedTextView;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxTextTool;
import com.vondear.rxtools.model.ModelSpider;
import com.vondear.rxtools.view.likeview.RxShineButton;
import com.zhuguangmama.imagepicker.ui.ImagePagerActivity;
import com.zlb.memo.R;
import com.zlb.memo.activity.CircleActivity;
import com.zlb.memo.activity.DetailsDarenActivity;
import com.zlb.memo.activity.DetailsPingyouActivity;
import com.zlb.memo.activity.PublishedBoZhuDetailsActivity;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.bean.Note;
import com.zlb.memo.bean.PhotoInfo;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.ImageUtil;
import com.zlb.memo.widgets.MultiImageView;
import com.zlb.memo.widgets.RxCobwebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeTouristAdapterRefresh extends BaseRefreshRecyclerAdapter<PublishBase> {
    private Context context;

    public HomeTouristAdapterRefresh(Context context) {
        super(R.layout.item_home_tourist);
        this.context = context;
    }

    @Override
    public BaseRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_self_help, parent, false);
                return new BaseRefreshViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_travels, parent, false);
                return new BaseRefreshViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news, parent, false);
                return new BaseRefreshViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_teamtour, parent, false);
                return new BaseRefreshViewHolder(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_tourist, parent, false);
                return new BaseRefreshViewHolder(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_guide, parent, false);
                return new BaseRefreshViewHolder(view);
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_coupon, parent, false);
                return new BaseRefreshViewHolder(view);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    protected void onBindViewHolder(BaseRefreshViewHolder holder, final PublishBase model, int position) {
        if (holder.getItemViewType() == 0) {
            CardView cardView = holder.getView(R.id.cardView);
            ImageView img_cover = holder.getView(R.id.img_cover);
            CircleImageView img_avatar = holder.getView(R.id.img_avatar);
            TextView tv_nickname = holder.getView(R.id.tv_nickname);
            TextView tv_time_and_distance = holder.getView(R.id.tv_time_and_distance);
            RxShineButton img_collection = holder.getView(R.id.img_collection);
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_detail = holder.getView(R.id.tv_detail);
            SlantedTextView tv_tag = holder.getView(R.id.tv_tag);
            if (model.getPoiList()!=null&&model.getPoiList().get(0)!=null&&
                    Float.parseFloat(model.getPoiList().get(0).getDistance()) > 50) {
                tv_tag.setVisibility(View.VISIBLE);
                tv_tag.setText("跨市区");
            } else {
                tv_tag.setVisibility(View.GONE);
            }
            if (model.getCoverImage() != null && model.getCoverImage().length() > 0) {
                ImageUtil.loadImage(context, img_cover, C.BaseImgUrl + model.getCoverImage());
            } else {
                ImageUtil.loadImage(context, img_cover, C.BaseImgUrl + model.getPoiList().get(0).getGdCutImage());
            }
            tv_name.setText(model.getName());
            tv_time_and_distance.setText(model.getTime() + "  " + model.getPoiList().get(0).getDistance() + "公里");

            // 响应点击事件的话必须设置以下属性
            tv_detail.setMovementMethod(LinkMovementMethod.getInstance());
            RxTextTool.Builder builder = RxTextTool.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER);
            for (int i = 0; i < model.getPoiList().size() - 1; i++) {
                final int finalI = i;
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.parseColor("#DAA520"));
                        ds.setUnderlineText(false);
                    }
                };
                if (i == 0) {
                    builder.append("图片").setResourceId(R.drawable.normal_qidian_blue)
                            .append(model.getPoiList().get(i).getKeyword()).setClickSpan(clickableSpan)
                            .append("图片").setResourceId(R.drawable.normal_youjiantou_green);
                } else {
                    builder.append(model.getPoiList().get(i).getKeyword()).setClickSpan(clickableSpan)
                            .append("图片").setResourceId(R.drawable.normal_youjiantou_green);
                }
            }
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(Color.parseColor("#DAA520"));
                    ds.setUnderlineText(false);
                }
            };
            builder.append(model.getPoiList().get(model.getPoiList().size() - 1).getKeyword()).setClickSpan(clickableSpan)
                    .append("图片").setResourceId(R.drawable.normal_zhongdian_blue);
            builder.into(tv_detail);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable("MODEL", model);
                    RxActivityTool.skipActivity(context, DetailsPingyouActivity.class, b);
                }
            });
        } else if (holder.getItemViewType() == 1) {
            RecyclerView rc_members = holder.getView(R.id.rc_photo);
            rc_members.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            HomeTravelsPhotoItemAdapter homeTravelsPhotoItemAdapter = new HomeTravelsPhotoItemAdapter(context);
            rc_members.setAdapter(homeTravelsPhotoItemAdapter);
            homeTravelsPhotoItemAdapter.replaceData(model.getBozhuList());
            homeTravelsPhotoItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(context, PublishedBoZhuDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("URL", model.getBozhuList().get(position).getFirstImageUrl());
                    bundle.putSerializable("note", new Note(
                            100,
                            model.getBozhuList().get(position).getTitle(),
                            model.getBozhuList().get(position).getContentDetails(),
                            "888",
                            model.getBozhuList().get(position).getCreationDate()));
                    intent.putExtra("data", bundle);
                    context.startActivity(intent);
                }
            });
        } else if (holder.getItemViewType() == 2) {
            TextView tv_content = holder.getView(R.id.tv_content);
            CardView cardView = holder.getView(R.id.cardView);
            tv_content.setText(model.getContentDetails());
            MultiImageView multiImagView = holder.getView(R.id.multiImagView);
            final List<PhotoInfo> photoInfos = new ArrayList<>();
            for (int i = 0; i < model.getImages().size(); i++) {
                photoInfos.add(new PhotoInfo(C.BaseImgUrl + model.getImages().get(i).getImageUrl()));
            }
            multiImagView.setList(photoInfos);
            multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                    List<String> photoUrls = new ArrayList<String>();
                    for (PhotoInfo photoInfo : photoInfos) {
                        photoUrls.add(photoInfo.url);
                    }
                    ImagePagerActivity.startImagePagerActivity(context, photoUrls, position, imageSize);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable("MODEL", model);
                    RxActivityTool.skipActivity(context, DetailsDarenActivity.class, b);
                }
            });
        } else if (holder.getItemViewType() == 3) {
            RxCobwebView rxCobwebView = holder.getView(R.id.cobweb_view);
            CardView cardView = holder.getView(R.id.cardView);
            List<ModelSpider> modelSpiders = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                modelSpiders.add(new ModelSpider(C.GuideTags.get(i), 1 + new Random().nextInt(rxCobwebView.getSpiderMaxLevel())));
            }
            rxCobwebView.setSpiderList(modelSpiders);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable("MODEL", model);
                    RxActivityTool.skipActivity(context, CircleActivity.class, b);
                }
            });
        } else if (holder.getItemViewType() == 4) {

        } else if (holder.getItemViewType() == 5) {

        } else if (holder.getItemViewType() == 6) {

        } else {

        }

    }

    @Override
    public int getItemViewType(int position) {
        int type = 1000;
        switch (getmList().get(position).getType()) {
            case "PINGYOU":
                type = 0;
                break;
            case "BOZHU":
                type = 1;
                break;
            case "DAREN":
                type = 2;
                break;
            case "DAOYOU":
                type = 3;
                break;
            case "LVXINGZHE":
                type = 4;
                break;
            case "SHANGHU":
                type = 5;
                break;
            case "YOUHUIQUAN":
                type = 6;
                break;
            default:
                break;
        }
        return type;
    }
}
