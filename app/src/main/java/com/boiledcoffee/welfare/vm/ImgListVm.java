package com.boiledcoffee.welfare.vm;

import com.boildcoffee.base.BR;
import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.base.bindingadapter.common.ReplyCommand;
import com.boildcoffee.base.paging.IPagingService;
import com.boildcoffee.base.paging.viewmodel.PagingVM;
import com.boiledcoffee.welfare.repository.ImageRepository;
import com.boiledcoffee.welfare.ui.ImageDetailActivity;
import com.boiledcoffee.welfare.vo.Atlas;

/**
 * @author zjh
 *         2018/4/20
 */

public class ImgListVm extends PagingVM<Atlas> {
    private BaseActivity mActivity;
    private ImageRepository mRepository;
    private String mUrl;

    public ReplyCommand<Integer> mItemClickListener = new ReplyCommand<>((position) -> ImageDetailActivity.startActivity(mActivity,getPagingBean().getData().get(position).getDetailUrl()));

    public ImgListVm(BaseActivity activity,String url){
        mActivity = activity;
        mUrl = url;
        mRepository = ImageRepository.create();
    }

    @Override
    public int getVariableId() {
        return BR.atlas;
    }

    @Override
    public IPagingService<Atlas> getPagingService() {
        return (page, pageSize, onSuccess, onError, onComplete) ->
                mRepository.getAtlas(mUrl,page)
                .subscribe(onSuccess,onError,onComplete);
    }

}
