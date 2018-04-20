package com.boiledcoffee.welfare.vm;

import com.boildcoffee.base.BR;
import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.base.paging.IPagingService;
import com.boildcoffee.base.paging.viewmodel.PagingVM;
import com.boiledcoffee.welfare.repository.ImageRepository;
import com.boiledcoffee.welfare.vo.ImageWelfare;

/**
 * @author zjh
 *         2018/4/20
 */

public class ImgListVm extends PagingVM<ImageWelfare> {
    private BaseActivity mActivity;
    private ImageRepository mRepository;
    private String mTypeValue;

    public ImgListVm(BaseActivity activity,String typeValue){
        mActivity = activity;
        mTypeValue = typeValue;
        mRepository = ImageRepository.create();
    }

    @Override
    public int getVariableId() {
        return BR.imageWelfare;
    }

    @Override
    public IPagingService<ImageWelfare> getPagingService() {
        return (page, pageSize, onSuccess, onError, onComplete) ->
                mRepository.getImageWelfare(mTypeValue,page)
                .subscribe(onSuccess,onError,onComplete);
    }
}
