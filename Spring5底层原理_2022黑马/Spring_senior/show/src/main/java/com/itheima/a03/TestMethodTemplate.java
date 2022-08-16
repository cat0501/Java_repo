package com.itheima.a03;

import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class TestMethodTemplate {

    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析 @Autowired"));
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析 @Resource"));
        beanFactory.getBean();
    }

    // 模板方法  Template Method Pattern
    static class MyBeanFactory {
        public Object getBean() {
            Object bean = new Object();
            System.out.println("构造 " + bean);
            System.out.println("依赖注入 " + bean); // 后续扩展：添加@Autowired, @Resource的解析功能
            for (BeanPostProcessor processor : processors) {// 遍历所有后处理器
                processor.inject(bean);
            }
            System.out.println("初始化 " + bean);
            return bean;
        }

        // 集合存储后处理器
        private List<BeanPostProcessor> processors = new ArrayList<>();

        public void addBeanPostProcessor(BeanPostProcessor processor) {
            processors.add(processor);
        }
    }

    static interface BeanPostProcessor {
        // 变化的部分抽象成接口
        public void inject(Object bean); // 对依赖注入阶段的扩展
    }
}
