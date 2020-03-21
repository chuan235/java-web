package top.gmfcj.service;

import top.gmfcj.bean.SysUser;

import java.util.List;

public interface ISysUserService {


    public int insert(SysUser sysUser);

    public List<SysUser> selectList();


}
