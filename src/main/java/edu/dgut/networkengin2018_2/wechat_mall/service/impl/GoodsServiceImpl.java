package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.GoodsMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 得到所有的商品
     *
     * @return
     */
    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllList();
    }

    @Override
    public String insertGoods(Goods goods) {
        if (goodsMapper.insert(goods) > 0) {
            return "添加成功";
        }
        return "添加失败";
    }

    @Override
    public PageResultUtil getGoodsPage(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findGoodsList(pageUtil);
        int total = goodsMapper.getTotalGoods(pageUtil);
        PageResultUtil pageResult = new PageResultUtil(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String updateGoods(Goods goods) {
        Goods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return "更新失败";
        }
        goods.setGoodsUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKey(goods) > 0) {
            return "更新成功";
        }
        return "更新失败";
    }


    @Override
    public Goods getGoodsById(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean batchUpdateSellStatus(Integer[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public Map<String, Object> getGoodsByIdForWechat(Integer goodsId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object>  result= new HashMap<>();
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

        if (goods != null) {
            map.put("goods_id", goods.getGoodsId());
            map.put("cat_id", goods.getGoodsCatThreeId());
            map.put("goods_price", goods.getGoodsPrice());
            map.put("goods_number", goods.getGoodsNumber());
            map.put("goods_weight", goods.getGoodsWeight());
            map.put("goods_introduce", goods.getGoodsIntroduce());
            map.put("goods_state", goods.getGoodsState());
            map.put("cat_one_id", goods.getGoodsCatOneId());
            map.put("cat_two_id", goods.getGoodsCatTwoId());
            map.put("cat_three_id", goods.getGoodsCatThreeId());

            meta.put("msg", "获取成功");
            meta.put("status", 200);

            result.put("message",map);
            result.put("meta",meta);
            return result;
        }

        //否则返回
        meta.put("msg", "获取商品信息失败");
        meta.put("status", 400);
        map.put("message", null);
        map.put("meta", meta);
        return map;
    }

    @Override
    public Map<String, Object> getGoodsPageForWechat(String keyword, Integer cid, Integer pageNum, Integer pageSize) {
        Integer start = null;
        if (pageNum == null || pageSize == null) {
            start = null;
        } else {
            start = (pageNum - 1) * pageSize; //数据库开始查询的地方
        }
        Map<String, Object> query = new HashMap<>(); //查询
        Map<String, Object> result = new HashMap<>(); //结果
        Map<String, Object> message = new HashMap<>(); //message
        Map<String, Object> meta = new HashMap<>(); //message
        query.put("keyword", keyword);
        query.put("cid", cid);
        query.put("start", start);
        query.put("limit", pageSize);


        List<Goods> goodsPageForWechat = goodsMapper.getGoodsPageForWechat(query);

        List<Map<String, Object>> goodsMapList = new ArrayList<>();
        for (int i = 0; i < goodsPageForWechat.size(); i++) {
            Map<String, Object> goodsTmp = new HashMap<>();
            goodsTmp.put("goods_id", goodsPageForWechat.get(i).getGoodsId());
            goodsTmp.put("goods_name", goodsPageForWechat.get(i).getGoodsName());
            goodsTmp.put("goods_price", goodsPageForWechat.get(i).getGoodsPrice());
            goodsTmp.put("goods_number", goodsPageForWechat.get(i).getGoodsNumber());
            goodsTmp.put("goods_weight", goodsPageForWechat.get(i).getGoodsWeight());
            goodsTmp.put("cat_id", goodsPageForWechat.get(i).getGoodsCatThreeId());
            goodsTmp.put("goods_big_logo", goodsPageForWechat.get(i).getGoodsBigLogo());
            goodsTmp.put("goods_small_logo", goodsPageForWechat.get(i).getGoodsSmallLogo());
            goodsTmp.put("add_time", goodsPageForWechat.get(i).getGoodsAddTime());
            goodsTmp.put("upd_time", goodsPageForWechat.get(i).getGoodsUpdateTime());
            goodsTmp.put("goods_cat_one_id", goodsPageForWechat.get(i).getGoodsCatOneId());
            goodsTmp.put("goods_cat_two_id", goodsPageForWechat.get(i).getGoodsCatTwoId());
            goodsTmp.put("goods_cat_three_id", goodsPageForWechat.get(i).getGoodsCatThreeId());
            goodsMapList.add(goodsTmp);
        }

        int total = goodsMapper.getTotalGoods(null);

        message.put("total", total);
        message.put("pagenum", pageNum);
        message.put("goods", goodsMapList);

        meta.put("msg", "获取成功");
        meta.put("status", 200);

        result.put("message", message);
        result.put("meta", meta);

        return result;
    }

    @Override
    public Map<String, Object> getGoodsForWechat(String keyword) {
        Map<String, Object> query = new HashMap<>(); //查询
        Map<String, Object> result = new HashMap<>(); //结果
        Map<String, Object> message = new HashMap<>(); //message
        Map<String, Object> meta = new HashMap<>(); //message
        query.put("keyword", keyword);

        List<Goods> goodsPageForWechat = goodsMapper.getGoodsPageForWechat(query);

        List<Map<String, Object>> goodsMapList = new ArrayList<>();
        for (int i = 0; i < goodsPageForWechat.size(); i++) {
            Map<String, Object> goodsTmp = new HashMap<>();
            goodsTmp.put("goods_id", goodsPageForWechat.get(i).getGoodsId());
            goodsTmp.put("goods_name", goodsPageForWechat.get(i).getGoodsName());
            goodsMapList.add(goodsTmp);
        }
        meta.put("msg", "获取成功");
        meta.put("status", 200);
        result.put("message", goodsMapList);
        result.put("meta", meta);

        return result;

    }

}
