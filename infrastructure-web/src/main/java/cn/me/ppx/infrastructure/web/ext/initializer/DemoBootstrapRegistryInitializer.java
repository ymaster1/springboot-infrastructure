package cn.me.ppx.infrastructure.web.ext.initializer;

import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ym
 * @date 2022/12/6 10:15
 * 可以在context准备完成之前做一些扩展，比如添加监听器等。。
 */
public class DemoBootstrapRegistryInitializer implements BootstrapRegistryInitializer {
    /**
     * @param registry the registry to initialize
     */
    @Override
    public void initialize(BootstrapRegistry registry) {
        System.out.println("DemoBootstrapRegistryInitializer");
        registry.addCloseListener((e)->{
            // 这里已经准备好context了，下一步是load context
            ConfigurableApplicationContext applicationContext = e.getApplicationContext();

        });
    }
}
