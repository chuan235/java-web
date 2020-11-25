package top.gmfcj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.gmfcj.bean.SysJob;

@Repository
public interface SysJobRepository extends JpaRepository<SysJob, Long> {

}
