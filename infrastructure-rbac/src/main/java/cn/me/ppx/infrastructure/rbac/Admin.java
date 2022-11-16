package cn.me.ppx.infrastructure.rbac;

import lombok.Data;

import java.util.Set;

/**
 * @author ym
 * @date 2022/11/16 10:20
 */
@Data
public class Admin {
    private String name;
    private Set<String> permissions;
}
