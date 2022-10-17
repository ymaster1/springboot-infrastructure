package cn.me.ppx.infrastructure.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.Iterator;


public class OvoXmlElementGenerator extends AbstractXmlElementGenerator {
    @Override
    public void addElements(XmlElement parentElement) {
        find(parentElement);
        selectAll(parentElement);
        updateSelective(parentElement);
        deleteByPrimaryKey(parentElement);
    }

    private void find(XmlElement parentElement) {
        String findSb = "select " +
                "<include refid=\"Base_Column_List\"/> " +
                "from " +
                introspectedTable.getFullyQualifiedTableNameAtRuntime();
        StringBuilder findWhereSb = new StringBuilder();

        XmlElement whereElement = new XmlElement("where");
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            XmlElement selectNotNullElement = new XmlElement("if");
            findWhereSb.setLength(0);
            findWhereSb.append("null != ");
            findWhereSb.append(introspectedColumn.getJavaProperty());
            selectNotNullElement.addAttribute(new Attribute("test", findWhereSb.toString()));
            findWhereSb.setLength(0);
            // 添加and
            findWhereSb.append(" and ");
            findWhereSb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            // 添加等号
            findWhereSb.append(" = ");
            findWhereSb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            selectNotNullElement.addElement(new TextElement(findWhereSb.toString()));
            whereElement.addElement(selectNotNullElement);
        }
        TextElement findText = new TextElement(findSb);

        XmlElement find = new XmlElement("select");
        find.addAttribute(new Attribute("id", "find"));
        find.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        find.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        find.addElement(findText);
        find.addElement(whereElement);
        parentElement.addElement(find);
    }

    private void selectAll(XmlElement parentElement) {
        String selectAllSb = "select " +
                "<include refid=\"Base_Column_List\"/> " +
                "from " +
                introspectedTable.getFullyQualifiedTableNameAtRuntime();
        TextElement selectAllText = new TextElement(selectAllSb);
        XmlElement list = new XmlElement("select");
        list.addAttribute(new Attribute("id", "selectAll"));
        list.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        list.addElement(selectAllText);
        parentElement.addElement(list);
    }

    private void updateSelective(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");
        answer.addAttribute(new Attribute("id", "updateSelective"));
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        XmlElement setElement = new XmlElement("set");
        answer.addElement(setElement);
        Iterator var5 = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getAllColumns()).iterator();

        while (var5.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) var5.next();
            sb.setLength(0);
            sb.append(" != null");
            XmlElement isNotNullElement = new XmlElement("if");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            setElement.addElement(isNotNullElement);
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append(',');
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        StringBuilder whereSb = new StringBuilder();
        XmlElement whereElement = new XmlElement("where");
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            XmlElement selectNotNullElement = new XmlElement("if");
            whereSb.setLength(0);
            whereSb.append("null != ");
            whereSb.append(introspectedColumn.getJavaProperty());
            selectNotNullElement.addAttribute(new Attribute("test", whereSb.toString()));
            whereSb.setLength(0);
            // 添加and
            whereSb.append(" and ");
            whereSb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            // 添加等号
            whereSb.append(" = ");
            whereSb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            selectNotNullElement.addElement(new TextElement(whereSb.toString()));
            whereElement.addElement(selectNotNullElement);
        }
        answer.addElement(whereElement);
        parentElement.addElement(answer);
    }

    private void deleteByPrimaryKey(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");
        answer.addAttribute(new Attribute("id", this.introspectedTable.getDeleteByPrimaryKeyStatementId()));
        String parameterClass = "java.lang.Long";

        answer.addAttribute(new Attribute("parameterType", parameterClass));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append("set is_delete = 1");
        answer.addElement(new TextElement(sb.toString()));
        boolean and = false;
        Iterator var6 = this.introspectedTable.getPrimaryKeyColumns().iterator();

        while (var6.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) var6.next();
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
        parentElement.addElement(answer);
    }
}
