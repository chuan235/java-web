package top.gmfcj.task;

import ch.qos.logback.core.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component("codeTask")
public class CodeTask {

    public void printJob(){
        LocalTime now = LocalTime.now();
        System.out.println(now+" : ------------job---------------");
    }
}
