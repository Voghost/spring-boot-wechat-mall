package edu.dgut.networkengin2018_2.wechat_mall.service;


import edu.dgut.networkengin2018_2.wechat_mall.entity.Floor;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

import java.util.List;

public interface FloorService {
    /**
     * 页查询floor
     * @param pageQueryUtil
     * @return
     */
    PageResultUtil getFloorPage(PageQueryUtil pageQueryUtil);

    Boolean deleteBatch(Integer[] ids);

    String insertFloor(Floor floor);

    String updateFloor(Floor floor);

    Floor getFloorById(Integer id);

    List<Floor> getAllFloorForWechat();

}
