package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Category;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrdersMapperTest {

    @Resource
    OrdersMapper ordersMapper;

    @Test
    void getAllList() {
    }

    @Test
    void insert() throws ParseException {
        Orders orders = new Orders();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2020-12-21");
        orders.setOrderCreateTime(date);
        orders.setOrderPrice(124312.124);
        orders.setOrderAddress("asfasfdas");
        orders.setOrderUserId(1);
        int num = ordersMapper.insert(orders);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
        int num = ordersMapper.deleteByPrimaryKey(1);
        assertEquals(1,num);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findOrdersList() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        List<Orders> goodsList = ordersMapper.findOrdersList(pageQueryUtil);
        assertEquals(goodsList.size(),1);
    }

    @Test
    void updateByPrimaryKey() throws ParseException {
        Orders orders = new Orders();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2020-12-22");
        orders.setOrderId(2);
        orders.setOrderCreateTime(date);
        orders.setOrderPrice(1252.53);
        orders.setOrderAddress("修改");
        int num = ordersMapper.updateByPrimaryKey(orders);
        assertEquals(num,1);
    }

    @Test
    void getTotalOrders() {
        Map<String,Object> list = new HashMap<>();
        list.put("page",2);
        list.put("limit",3);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(list);
        int num= ordersMapper.getTotalOrders();
        assertEquals(num,7);

    }
}