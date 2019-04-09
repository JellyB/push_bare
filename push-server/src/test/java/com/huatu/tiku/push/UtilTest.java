package com.huatu.tiku.push;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-15 上午9:38
 **/
@Slf4j
public class UtilTest extends PushBaseTest{

    private static final SimpleDateFormat simpledateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/YYYY");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);



    public void test(){
        long time = 1542290400000l;

        log.info("time:{}", simpledateFormat.format(new Date(time)));

    }

    @Test
    public static void  dateTest() {
        long time = 1573832381326l;
        long time2 = 1542622800000l;
        System.err.println(simpledateFormat.format(new Date(time)));
        System.err.println(simpledateFormat.format(new Date(time2)));

        time += 2 * 60 * 1000L;
        System.err.println(time);

        Long target = null;
        Integer source = 10;
        target = Long.valueOf(source.intValue());
        System.err.println(target);
        try{
            Date startTime = dateFormat.parse("2019-04-09 14:18:00");
            Date endTime = dateFormat.parse("2028-11-26 22:00:00");
            System.err.println("startTime:" + startTime.getTime() );
            System.err.println("endTime:" + endTime.getTime() );
        }catch (Exception e){
            System.err.println("parse date error!" );
        }

    }

    public static void main(String[] args) {
        dateTest();
    }


    @Test
    public void timesTriggered(){
        try{
            Date startTime = dateFormat.parse("2019-02-18 18:25:00");
            long times = (System.currentTimeMillis() - startTime.getTime())/300000;
            log.info("times triggered!{}", times);
        }catch (Exception e){
            log.error("error", e);
        }
    }

}
