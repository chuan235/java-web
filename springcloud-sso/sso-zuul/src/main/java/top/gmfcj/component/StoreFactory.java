package top.gmfcj.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StoreFactory {

    @Autowired
    private RedisStore redisStore;

    @Autowired
    private MysqlStore mysqlStore;


    @Value("${token.store.type}")
    private String storeType;

    public StoreInterface getStore(){
        switch (storeType){
            case "redis":
                return redisStore;
            case "mysql":
                return mysqlStore;
        }
        return redisStore;
    }

}
