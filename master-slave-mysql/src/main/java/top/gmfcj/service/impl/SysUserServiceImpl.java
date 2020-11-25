package top.gmfcj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.gmfcj.anno.Read;
import top.gmfcj.anno.Write;
import top.gmfcj.bean.SysUser;
import top.gmfcj.mapper.SysUserMapper;
import top.gmfcj.service.ISysUserService;

import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    // 使用写数据库
    @Override
    @Write
    public int insert(SysUser sysUser) {
        // 查询一下数据库的主机名
        sysUserMapper.queryServerName().forEach(s -> System.out.println(s));
        return sysUserMapper.insert(sysUser);
    }

    // 使用读数据库
    @Override
    @Read
    public List<SysUser> selectList() {
//        System.out.println(sysUserMapper.queryServerName());
        List<String> list = sysUserMapper.queryServerName();
        for (String s : list) {
            System.out.println(s);
        }
        return sysUserMapper.selectList(null);
    }
}
