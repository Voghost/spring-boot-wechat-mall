package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.GoodsMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Goods;
import edu.dgut.networkengin2018_2.wechat_mall.service.GoodsService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
}
