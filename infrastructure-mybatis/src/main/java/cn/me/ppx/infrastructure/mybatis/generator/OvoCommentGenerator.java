package cn.me.ppx.infrastructure.mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 * mybatis 注解生成器
 */
public class OvoCommentGenerator implements CommentGenerator {
    private Properties properties;

    private static final String DATA_FORMATTER = "yyyy-MM-dd HH:mm:ss.SS";

    public OvoCommentGenerator() {
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // 获取所有的properties
        this.properties.putAll(properties);
    }

    /**
     * 实体类字段注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 获取列注释
        String remarks = introspectedColumn.getRemarks();
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + remarks);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    /**
     * 实体类注释
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * TABLE: " + fullyQualifiedTable);
        topLevelClass.addJavaDocLine(" * @author OVO.MYBATIS.GENERATOR");
        topLevelClass.addJavaDocLine(" * @date " + getData());
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    /**
     * getter 方法注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String formatterStr = "<" + introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName() + ">";
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * This method returns the value of the database column  " + formatterStr);
        method.addJavaDocLine(" * @return get the value of " + formatterStr);
        method.addJavaDocLine(" */");
    }

    /**
     * setter 方法注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        Parameter parm = method.getParameters().get(0);
        String formatterStr = "<" + introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName() + ">";
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * This method sets the value of the database column  " + formatterStr);
        method.addJavaDocLine(" * @param " + parm.getName() + " the value for " + formatterStr);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    protected String getData() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMATTER);
        return sdf.format(new Date());
    }
}
