import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author tyc
 * @date 2019/6/13
 */
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml","classpath:spring-mvc.xml","classpath:applicationContext-redis.xml"})
public class TestSplit extends AbstractJUnit4SpringContextTests {

    @Test
    public void testdemo(){
        String de = "54647@qq.com";
        String[] a = de.split("@",2);
        System.out.println(a[0].toString());
        System.out.println(a[1].toString());


    }

}
