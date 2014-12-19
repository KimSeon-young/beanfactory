package com.coupang.c4.step14.beanfactory;

/**
 * Created by coupang on 14. 12. 11..
 */
public interface BeanFacotryInterface {
    <T> Object getInstance(Class<T> type);
    Object getInstance(String beanName);
}
