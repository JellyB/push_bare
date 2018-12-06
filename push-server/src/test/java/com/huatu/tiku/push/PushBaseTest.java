package com.huatu.tiku.push;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author biguodong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PushApplication.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@WebAppConfiguration
public class PushBaseTest {
}
