package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.SwiperdataMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Swiperdata;
//import edu.dgut.networkengin2018_2.wechat_mall.service.SwiperdataService;
import edu.dgut.networkengin2018_2.wechat_mall.service.SwiperdataService;
import edu.dgut.networkengin2018_2.wechat_mall.service.impl.SwiperdataServiceImpl;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiperdataServiceImpl implements SwiperdataService {

    @Autowired
    private SwiperdataMapper swiperdataMapper;

    @Override
    public PageResultUtil getSwiperdataPage(PageQueryUtil pageQueryUtil){
        List<Swiperdata> swiperdatas = swiperdataMapper.findSwiperdataList(pageQueryUtil);
        int total = swiperdataMapper.getTotalSwiperdata();

        PageResultUtil pageResultUtil = new PageResultUtil(swiperdatas, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResultUtil;
    }

    @Override
    public String insertSwiperdata(Swiperdata swiperdata){
        if(swiperdataMapper.insert(swiperdata)>0){
            return "插入成功";
        }
        return "插入失败";
    }

    @Override
    public String updateSwiperdata(Swiperdata swiperdata){
        Swiperdata temp = swiperdataMapper.selectByPrimaryKey(swiperdata.getSwiperId());

        if(temp == null){
            return "没有这个图片";
        }

        temp.setNavigatorUrl(swiperdata.getNavigatorUrl());
        temp.setGoodsId(swiperdata.getGoodsId());
        temp.setOpenType(swiperdata.getOpenType());
        temp.setImageSrc(swiperdata.getImageSrc());
        if(swiperdataMapper.updateByPrimaryKey(swiperdata)>0){
            return "修改成功";
        }
        return "修改错误";
    }

    @Override
    public Swiperdata getSwiperdataById(Integer id) { return swiperdataMapper.selectByPrimaryKey(id); }


    @Override
    public Boolean deletePicture(Integer id) {
        return swiperdataMapper.deleteByPrimaryKey(id) > 0;
    }

}
