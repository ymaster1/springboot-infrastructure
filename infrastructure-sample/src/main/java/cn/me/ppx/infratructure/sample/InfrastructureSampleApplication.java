package cn.me.ppx.infratructure.sample;

import cn.me.ppx.infrastructure.mybatis.generator.GeneratorTools;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class InfrastructureSampleApplication {

    public static void main(String[] args) throws Exception {
        GeneratorTools.generator(new File(InfrastructureSampleApplication.class.getClassLoader().getResource("generator/generatorConfig.xml").getFile()));
//        SpringApplication.run(InfrastructureSampleApplication.class, args);
    }

}
