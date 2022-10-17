package cn.me.ppx.infrastructure.mybatis.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RepositoryPlugin extends PluginAdapter {

    private String baseRepository;
    private String baseRepositoryImpl;
    private String targetProject;
    private String targetPackage;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String baseRepository = this.properties.getProperty("baseRepository");
        if (StringUtility.stringHasValue(baseRepository)) {
            this.baseRepository = baseRepository;
        } else {
            throw new RuntimeException("baseRepository 属性不能为空！");
        }
        String baseRepositoryImpl = this.properties.getProperty("baseRepositoryImpl");
        if (StringUtility.stringHasValue(baseRepositoryImpl)) {
            this.baseRepositoryImpl = baseRepositoryImpl;
        } else {
            throw new RuntimeException("baseRepositoryImpl 属性不能为空！");
        }
        String targetProject = this.properties.getProperty("targetProject");
        if (StringUtility.stringHasValue(targetProject)) {
            this.targetProject = targetProject;
        } else {
            throw new RuntimeException("targetProject 属性不能为空！");
        }
        String targetPackage = this.properties.getProperty("targetPackage");
        if (StringUtility.stringHasValue(targetPackage)) {
            this.targetPackage = targetPackage;
        } else {
            throw new RuntimeException("targetPackage 属性不能为空！");
        }
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable table) {
        return Arrays.asList(generateService(table), generateRepositoryImpl(table));
    }

    private GeneratedJavaFile generateService(IntrospectedTable table) {
        // 获取实体类型
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 生成 Service 名称
        String repository = targetPackage + "." + table.getFullyQualifiedTable().getDomainObjectName() + "Repository";
        // 构造 Service 文件
        Interface interfaze = new Interface(new FullyQualifiedJavaType(repository));
        // 设置作用域
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        // import
        interfaze.addImportedType(entityType);
        interfaze.addImportedType(new FullyQualifiedJavaType(baseRepository));
        interfaze.addSuperInterface(new FullyQualifiedJavaType(
                getClassName(baseRepository) + "<" + entityType.getShortName() + ">"));
        return new GeneratedJavaFile(interfaze, targetProject, new DefaultJavaFormatter());
    }

    private GeneratedJavaFile generateRepositoryImpl(IntrospectedTable table) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        String domainObjectName = table.getFullyQualifiedTable().getDomainObjectName();
        String repository = targetPackage + "." + domainObjectName + "Repository";
        String repositoryImpl = targetPackage + ".impl." + domainObjectName + "RepositoryImpl";
        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(repositoryImpl));
        clazz.addImportedType(entityType);
        clazz.addImportedType(new FullyQualifiedJavaType(repository));
        clazz.addImportedType(new FullyQualifiedJavaType(baseRepositoryImpl));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        clazz.addAnnotation("@Repository(\"" + firstLetterLowerCase(domainObjectName + "Repository") + "\")");
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.setSuperClass(new FullyQualifiedJavaType(
                getClassName(baseRepositoryImpl) + "<" + entityType.getShortName() + ">"));
        clazz.addSuperInterface(new FullyQualifiedJavaType(repository));
        return new GeneratedJavaFile(clazz, targetProject, new DefaultJavaFormatter());
    }

    private String firstLetterLowerCase(String name) {
        char c = name.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            String temp = String.valueOf(c);
            return name.replaceFirst(temp, temp.toLowerCase());
        }
        return name;
    }

    private String getClassName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

}
