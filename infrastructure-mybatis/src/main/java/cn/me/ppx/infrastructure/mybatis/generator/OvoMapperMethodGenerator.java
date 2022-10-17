package cn.me.ppx.infrastructure.mybatis.generator;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Set;
import java.util.TreeSet;

public class OvoMapperMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface inter) {
        addInterfaceFind(inter);
        addInterfaceSelectAll(inter);
        addInterfaceUpdateSelective(inter);
        addInterfaceDelete(inter);
    }

    private void addInterfaceFind(Interface inter) {
        // 先创建import对象
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        // 添加Lsit的包
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        // 创建方法对象
        Method method = new Method();
        // 设置该方法为public
        method.setVisibility(JavaVisibility.PUBLIC);
        // 设置返回类型是List
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // 方法对象设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名称为我们在IntrospectedTable类中初始化的 “selectByObject”
        method.setName("find");

        // 设置参数类型是对象
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // import参数类型对象
        importedTypes.add(parameterType);
        // 为方法添加参数，变量名称record
        method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
        //
        addMapperAnnotations(inter, method);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, inter, introspectedTable)) {
            inter.addImportedTypes(importedTypes);
            inter.addMethod(method);
        }
    }

    private void addInterfaceSelectAll(Interface inter) {
        // 先创建import对象
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        // 添加Lsit的包
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        // 创建方法对象
        Method method = new Method();
        // 设置该方法为public
        method.setVisibility(JavaVisibility.PUBLIC);
        // 设置返回类型是List
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        // 设置List的类型是实体类的对象
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        importedTypes.add(listType);
        // 返回类型对象设置为List
        returnType.addTypeArgument(listType);
        // 方法对象设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名称为我们在IntrospectedTable类中初始化的 “selectByObject”
        method.setName("selectAll");

        addMapperAnnotations(inter, method);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, inter, introspectedTable)) {
            inter.addImportedTypes(importedTypes);
            inter.addMethod(method);
        }
    }

    private void addInterfaceUpdateSelective(Interface inter) {
        // 先创建import对象
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        // 添加Lsit的包
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        // 创建方法对象
        Method method = new Method();
        // 设置该方法为public
        method.setVisibility(JavaVisibility.PUBLIC);
        // 设置返回类型是 int
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getIntInstance();
        // 方法对象设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名称
        method.setName("updateSelective");

        // 设置参数类型是对象
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // import参数类型对象
        importedTypes.add(parameterType);
        // 为方法添加参数，变量名称record
        method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
        addMapperAnnotations(inter, method);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, inter, introspectedTable)) {
            inter.addImportedTypes(importedTypes);
            inter.addMethod(method);
        }
    }

    private void addInterfaceDelete(Interface inter) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("deleteByPrimaryKey");
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("java.lang.Long");
        method.addParameter(new Parameter(parameterType, "id"));
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(parameterType);
        importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(inter, method);
        if (this.context.getPlugins().clientUpdateByExampleSelectiveMethodGenerated(method, inter, this.introspectedTable)) {
            inter.addImportedTypes(importedTypes);
            inter.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface inter, Method method) {
    }

}
