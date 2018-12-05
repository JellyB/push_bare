package com.huatu.tiku.push.util;

import com.github.pagehelper.PageInfo;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-07 下午7:02
 **/
public class PageUtil {

    public static Page parsePageInfo(PageInfo<?> pageInfo){
        Page page = new Page(pageInfo.getTotal(), pageInfo.isHasNextPage() ? 1 : 0, pageInfo.getPages());
        page.setList(pageInfo.getList());
        return page;
    }
}
