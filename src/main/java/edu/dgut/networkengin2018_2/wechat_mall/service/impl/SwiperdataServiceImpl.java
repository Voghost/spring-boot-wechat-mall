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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SwiperdataServiceImpl implements SwiperdataService {

    @Autowired
    private SwiperdataMapper swiperdataMapper;

    @Override
    public PageResultUtil getSwiperdataPage(PageQueryUtil pageQueryUtil) {
        List<Swiperdata> swiperdatas = swiperdataMapper.findSwiperdataList(pageQueryUtil);
        int total = swiperdataMapper.getTotalSwiperdata();

        PageResultUtil pageResultUtil = new PageResultUtil(swiperdatas, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResultUtil;
    }

    @Override
    public String insertSwiperdata(Swiperdata swiperdata){
        Integer id = swiperdata.getGoodsId();
        String url = "/pages/goods_detail/index?goods_id=" + id.toString() ;
        swiperdata.setNavigatorUrl(url);
        if(swiperdataMapper.insert(swiperdata)>0){
            return "插入成功";
        }
        return "插入失败";
    }

    @Override
    public String updateSwiperdata(Swiperdata swiperdata) {
        Swiperdata temp = swiperdataMapper.selectByPrimaryKey(swiperdata.getSwiperId());

        if (temp == null) {
            return "没有这个图片";
        }

        temp.setNavigatorUrl(swiperdata.getNavigatorUrl());
        temp.setGoodsId(swiperdata.getGoodsId());
        temp.setOpenType(swiperdata.getOpenType());
        temp.setImageSrc(swiperdata.getImageSrc());
        if (swiperdataMapper.updateByPrimaryKey(swiperdata) > 0) {
            return "修改成功";
        }
        return "修改错误";
    }

    @Override
    public Swiperdata getSwiperdataById(Integer id) {
        return swiperdataMapper.selectByPrimaryKey(id);
    }


    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }

        return swiperdataMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Map<String, Object> getAllSwiperDataForWechat() {
        Map<String, Object> result = new HashMap<>(); //用于存放结果的map
        List<Map<String, Object>> swiperList = new ArrayList<>(); //用于存放轮播图的list
        Map<String, Object> meta = new HashMap<>(); //用于存放结果的状态

        List<Swiperdata> swiperdata = swiperdataMapper.getAllList();
        if (swiperdata == null) {
            meta.put("msg", "获取失败");
            meta.put("status", "400");
            result.put("message", null);
            result.put("meta", meta);
            return  result;
        }

        for (int i = 0; i < swiperdata.size(); i++) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("image_src", swiperdata.get(i).getImageSrc());
            tmp.put("open_type", swiperdata.get(i).getOpenType());
            tmp.put("goods_id", swiperdata.get(i).getGoodsId());
            tmp.put("navigator_url", swiperdata.get(i).getNavigatorUrl());
            swiperList.add(tmp);
        }
        meta.put("msg", "获取成功");
        meta.put("status", "200");

        result.put("message",swiperList);
        result.put("meta",meta);
        return result;
    }


}
