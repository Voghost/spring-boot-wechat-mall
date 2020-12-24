package edu.dgut.networkengin2018_2.wechat_mall.controller.wechatapi;


import com.sun.activation.registries.MailcapParseException;
import edu.dgut.networkengin2018_2.wechat_mall.entity.WechatLogin;
import edu.dgut.networkengin2018_2.wechat_mall.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/wechatapi")
public class UserApiController {

    @Resource
    private UsersService usersService;

    @ResponseBody
    @PostMapping("/users/wxlogin")
    public Map<String,Object> wxLogin(@RequestBody WechatLogin wechatLogin){
        return usersService.wechatLogin(wechatLogin);
    }

}
