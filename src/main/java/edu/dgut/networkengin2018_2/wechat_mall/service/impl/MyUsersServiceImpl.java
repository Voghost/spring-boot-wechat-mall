package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import edu.dgut.networkengin2018_2.wechat_mall.dao.MyUserMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.MyUser;
import edu.dgut.networkengin2018_2.wechat_mall.service.MyUsersService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyUsersServiceImpl implements MyUsersService {

    @Autowired
    private MyUserMapper myUserMapper;

    @Override
    public PageResultUtil getMyUsersPage(PageQueryUtil pageQueryUtil) {
        List<MyUser> myUsers = myUserMapper.findMyUsersList(pageQueryUtil);
        int total = myUserMapper.getTotalMyUsers();

        PageResultUtil pageResultUtil = new PageResultUtil(myUsers, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResultUtil;
    }

    @Override
    public String insertMyUsers(MyUser myUser) {
        if(myUserMapper.insert(myUser)>0){
            return "插入成功";
        }
        return "插入失败";
    }

    @Override
    public String updateMyUsers(MyUser myUser) {
        MyUser temp = myUserMapper.selectByPrimaryKey(myUser.getUserId());

        if (temp == null) {
            return "没有这个用户";
        }

        temp.setUserId(myUser.getUserId());
        temp.setUserName(myUser.getUserName());
        temp.setPassword(myUser.getPassword());
        temp.setAuthority(myUser.getAuthority());
        if (myUserMapper.updateByPrimaryKey(temp) > 0) {
            return "修改成功";
        }
        return "修改错误";
    }

    @Override
    public MyUser getMyUsersById(Integer id) {
        return myUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }

        return myUserMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Map<String, Object> getAllMyUsersForWechat() {
        Map<String, Object> result = new HashMap<>(); //用于存放结果的map
        List<Map<String, Object>> myUsersList = new ArrayList<>(); //用于存放用户的list
        Map<String, Object> meta = new HashMap<>(); //用于存放结果的状态

        List<MyUser> myUsers = myUserMapper.getAllList();
        if (myUsers == null) {
            meta.put("msg", "获取失败");
            meta.put("status", "400");
            result.put("message", null);
            result.put("meta", meta);
            return  result;
        }

        for (int i = 0; i < myUsers.size(); i++) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("user_name", myUsers.get(i).getUserName());
            tmp.put("user_password", myUsers.get(i).getPassword());
            tmp.put("user_authority", myUsers.get(i).getAuthority());
            myUsersList.add(tmp);
        }
        meta.put("msg", "获取成功");
        meta.put("status", "200");

        result.put("message",myUsersList);
        result.put("meta",meta);
        return result;
    }
}
