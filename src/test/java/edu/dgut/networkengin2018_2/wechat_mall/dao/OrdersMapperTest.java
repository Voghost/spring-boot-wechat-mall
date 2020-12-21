package edu.dgut.networkengin2018_2.wechat_mall.dao;

import edu.dgut.networkengin2018_2.wechat_mall.entity.Orders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        orders.setOrderTime(date);
        orders.setOrderPrice(124312.124);
        orders.setOrderAddress("asfasfdas");
        orders.setAuthorization("fdasf");
        orders.setOrderUserId(1);
        int num = ordersMapper.insert(orders);
        assertEquals(num,1);
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void findOrdersList() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}