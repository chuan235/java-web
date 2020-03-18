package top.gmfcj.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreUtil {

    @Autowired
    private StoreFactory storeFactory;


    public Object get(String key){
        return  storeFactory.getStore().get(key);
    }


    public void set(String key,Object object){
        storeFactory.getStore().set(key,object);
    }

}
