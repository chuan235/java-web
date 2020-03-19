package top.gmfcj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.gmfcj.bean.SysUser;
import top.gmfcj.component.StoreUtil;
import top.gmfcj.util.CookieUtil;
import top.gmfcj.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @description:
 * @author: GMFCJ
 * @create: 2020-03-19 14:02
 */
@Controller
public class LoginController {

    @Autowired
    private StoreUtil storeUtil;

    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser sysUser = new SysUser(username, password);
        ObjectMapper objectMapper = new ObjectMapper();
        if ("123".equals(password)) {
            // 登陆成功，生成的唯一用户标识，会写token到cookie
            String token = CookieUtil.setLoginCookie(request, response);
            String value = JwtUtil.createJWT(UUID.randomUUID().toString(), objectMapper.writeValueAsString(sysUser), 3600*24);
            // 保存标识
            storeUtil.set(token, value);
        }
        return "success";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }
}
