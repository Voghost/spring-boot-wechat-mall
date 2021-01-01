package edu.dgut.networkengin2018_2.wechat_mall.service;


import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

public interface FloorService {
    /**
     * 页查询floor
     * @param pageQueryUtil
     * @return
     */
    public PageResultUtil getFloorPage(PageQueryUtil pageQueryUtil);

    public Boolean deleteBatch(Integer[] ids);


}
