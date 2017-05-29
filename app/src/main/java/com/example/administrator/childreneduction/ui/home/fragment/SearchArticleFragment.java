package com.example.administrator.childreneduction.ui.home.fragment;

import android.animation.ObjectAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.home.adapter.SearchArticleAdapter;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.google.gson.Gson;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/5/29.
 * 搜索文章
 */

public class SearchArticleFragment extends BaseFagment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyFramArtsearItem;

    private int state=0;
    private LoginInfo login;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;

    private SearchArticleAdapter mArticleAdapter;

    public static SearchArticleFragment newInstance(){
        return new SearchArticleFragment();
    }

    @Override
    public int getLayOutID() {
        return R.layout.fragment_search_article;
    }

    @Override
    public void initView(View mRootView) {
        mRefresh = (BGARefreshLayout) mRootView.findViewById(R.id.refresh);
        mRecyFramArtsearItem = (RecyclerView) mRootView.findViewById(R.id.recy_fram_artsear_item);

        initToData();
    }

    private void initToData(){
        mPrefernceUtils=new SharePrefernceUtils(getContext(), Content.SP_NAME);
        mGson=new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        login = mGson.fromJson(string, LoginInfo.class);

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder=new BGANormalRefreshViewHolder(getContext(),true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        initRecyclerView();
//        mRefresh.beginRefreshing();
        state=0;
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                new Spruce.SpruceBuilder(mRecyFramArtsearItem)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyFramArtsearItem, 800),
                                ObjectAnimator.ofFloat(mRecyFramArtsearItem, "translationX", -mRecyFramArtsearItem.getWidth(), 0f).setDuration(800))
                        .start();
            }
        };
        mRecyFramArtsearItem.setLayoutManager(manager);
        mRecyFramArtsearItem.addItemDecoration(new RecycleViewDivider(getContext(), DividerItemDecoration.VERTICAL));
        mArticleAdapter=new SearchArticleAdapter(getContext());
        mRecyFramArtsearItem.setAdapter(mArticleAdapter);

//        mArticleAdapter.setOnClickListener(new OnClickListener() {
//            @Override
//            public void setOnClickListener(View view, int position) {
//                UA_Table ua_table = mArticleAdapter.getList().get(position);
//                Intent intent = LookArticleActivity.createIntent(getContext());
//                intent.putExtra(Content.ARTICLE_INFO,ua_table);
//            }
//        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
