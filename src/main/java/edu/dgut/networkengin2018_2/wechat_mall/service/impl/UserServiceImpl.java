package edu.dgut.networkengin2018_2.wechat_mall.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.dgut.networkengin2018_2.wechat_mall.dao.UsersMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Users;
import edu.dgut.networkengin2018_2.wechat_mall.entity.WechatLogin;
import edu.dgut.networkengin2018_2.wechat_mall.service.UsersService;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageResultUtil;
import edu.dgut.networkengin2018_2.wechat_mall.util.WechatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public PageResultUtil getUsersPage(PageQueryUtil pageQueryUtil) {
        List<Users> users = usersMapper.findUsersList(pageQueryUtil);
        int total = usersMapper.getTotalUser();

        PageResultUtil pageResultUtil = new PageResultUtil(users, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResultUtil;
    }

    /**
     * 微信登录
     *
     * @param wechatLogin
     * @return
     */
    @Override
    public Map<String, Object> wechatLogin(WechatLogin wechatLogin) {
        WechatInfo wechatInfo = new WechatInfo();
        String urlFormat = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String url = String.format(urlFormat, wechatInfo.getAppID(), wechatInfo.getAppSecret(), wechatLogin.getCode());

        RestTemplate rest = new RestTemplate();
        RequestEntity<Void> requestEntity = RequestEntity.post(url).header("User-Agent", "Dgut Demo").build();
        ResponseEntity<String> exchange = rest.exchange(requestEntity, String.class);
        String result = exchange.getBody();
        Map<String, Object> resultMap = new JacksonJsonParser().parseMap(result);

        Users user = usersMapper.getUserByOpenId((String) resultMap.get("openid")); //查看数据库中是否用该用户

        if (user == null) {
            user = new Users();
            user.setUserOpenId((String) resultMap.get("openid"));
            user.setUserCreateTime(new Date());
            user.setUserUpdateTime(new Date());
            user.setUserLastLoginTime(new Date());

            usersMapper.insert(user);


            String token = JWT.create()
                    .withKeyId(user.getUserId().toString())
                    .withIssuer("www.ikertimes.com")
                    .withIssuedAt(new Date())
                    .withJWTId("jwt.ikertimes.com")
                    .withAudience(user.getUserId().toString())
                    .sign(Algorithm.HMAC256(user.getUserOpenId()));

            user.setUserToken(token);


            usersMapper.updateByPrimaryKey(user);
        } else {
            user.setUserLastLoginTime(new Date());
            usersMapper.updateByPrimaryKey(user); //更新用户登录时间
        }


        Map<String, Object> userMap = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();

        userMap.put("user_id", user.getUserId());
        userMap.put("token", user.getUserToken());

        meta.put("msg", "登录成功");
        meta.put("status", 200);
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("message", userMap);
        pageResult.put("meta", meta);

        return pageResult;
    }

    @Override
    public Users getUserByToken(String token) {
        return usersMapper.getUserByToken(token);
    }


    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return usersMapper.lockUserBatch(ids, lockStatus) > 0;
    }

}
