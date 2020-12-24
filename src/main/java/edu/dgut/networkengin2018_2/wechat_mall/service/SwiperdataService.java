package edu.dgut.networkengin2018_2.wechat_mall.service;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;

import java.util.Map;

public interface SwiperdataService {

    PageResultUtil getSwiperdataPage(PageQueryUtil pageQueryUtil);

    String insertSwiperdata(Swiperdata swiperdata);

    String updateSwiperdata(Swiperdata swiperdata);

    Swiperdata getSwiperdataById(Integer id);

    Boolean deletePicture(Integer id);

    Map<String,Object> getAllSwiperDataForWechat();

}
