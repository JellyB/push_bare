package com.huatu.tiku.push.service.command;

import com.huatu.tiku.push.quartz.command.CourseJobCommand;
import com.huatu.tiku.push.quartz.command.MockJobCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：
 *
 * @author biguodong
 * Create time 2018-11-09 上午10:34
 **/
@Slf4j
@Service
public class ConcreteReceiver implements Receiver{

    @Autowired
    private CourseJobCommand courseJobCommand;

    @Autowired
    private MockJobCommand mockJobCommand;


    /**
     * 接收命令
     */
    @Override
    public void acceptCommand() {

        courseJobCommand.work();
        mockJobCommand.work();

    }
}
