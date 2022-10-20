package cn.me.ppx.infratructure.sample.common;

import cn.me.ppx.infrastructure.common.utils.BeanConverter;

/**
 * @author ym
 * @date 2022/10/20 16:07
 */
public class Main {
    public static void main(String[] args) {
        Animal animal =  Animal.builder().name("ppx").build();
//        Animal animal = new Animal();
        animal.setName("ppx");
        Dog dog = BeanConverter.convert(Dog.class, animal);
        System.out.println(dog.toString());
    }
}
