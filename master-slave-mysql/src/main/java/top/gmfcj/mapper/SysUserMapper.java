package top.gmfcj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import top.gmfcj.bean.Info;
import top.gmfcj.bean.SysUser;

import java.util.Date;
import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select name from [sys].[databases]")
    List<String> queryServerName();

}
