package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.FloorMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Floor;
import edu.dgut.networkengin2018_2.wechat_mall.service.FloorService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorMapper floorMapper;


    @Override
    public PageResultUtil getFloorPage(PageQueryUtil pageQueryUtil) {
        List<Floor> floorList = floorMapper.findFloorList(pageQueryUtil);
        int total = floorMapper.getTotalFloors();

        PageResultUtil pageResultUtil = new PageResultUtil(floorList,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
        return pageResultUtil;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
         if(ids.length<1){
             return false;
         }
         return floorMapper.deleteBatch(ids) > 0 ;
    }
}
