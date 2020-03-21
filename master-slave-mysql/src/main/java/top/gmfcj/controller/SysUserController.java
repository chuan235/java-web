package top.gmfcj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.gmfcj.bean.Info;
import top.gmfcj.bean.SysUser;
import top.gmfcj.mapper.InfoMapper;
import top.gmfcj.service.ISysUserService;

import java.util.List;

@RestController
public class SysUserController {

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private ISysUserService userService;

    @RequestMapping("/list")
    public List<Info> list(){
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age","email");
        return infoMapper.selectList(null);
    }

    @GetMapping("/user/{id}/{name}")
    public String addUser(@PathVariable Integer id,@PathVariable String name){
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setName(name);
        userService.insert(sysUser);
        return "success";
    }


    @GetMapping("/user/list")
    public List<SysUser> userList(){
        return userService.selectList();
    }
}
