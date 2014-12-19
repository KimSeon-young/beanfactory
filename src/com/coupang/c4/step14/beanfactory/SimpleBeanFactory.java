package com.coupang.c4.step14.beanfactory;

import com.coupang.c4.step14.ResourceUtil;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Dead Line : 12 / 19 (Fri)
 * Step 14만 따로 해서 git에 올린 후, 메일로 주소를 보냄.
 *
 * 전제조건:
 *  - 기본 생성자가 있는 bean만 취급한다. declaredConstro를 사용.
 * 1. Singleton instance 관리 - 생성된 bean 캐싱 : Map 사용(멤버 변수)
 * 1-1. 고려 내용 추후 다른 scope 생성이 용이한 구조가 되도록. : 다른 scope을 구현하라는 얘기는 아님. OOP로 개발한다는 것은 어떻게 하는 것인가?
 * 2. thread safe 하게 구성할 것. : beanfactory는 객체를 막 호출하기 때문에 그 부분을 고려해서 동기화 : 제외
 * 3. 계층 구조가 가능한 bean factory : 1-1과 관련성이 있음. 상속의 의미는 아니고 전역으로 : 제외
 *
 * 유연성과 확장성을 잘 고려해서 심플 빈팩토리를 잘 나누고 추상화를 해줘야 함.
 * 점수기준 - 감동을 받으면 100점, 스펙 완성 : 90점, 일부 완성 : 70점, 미구현 : 30점
 * 16번에서 scope로 풀고 있음.
 * 새 method와 return으로 해결하시오. 불필요한 else문을 만들지 마시오.
 * 다중 루프문은 각 스탭별로 메소드를 분리.
 * 인터페이스를 만들고 이것을 기준으로 움직이는 것이 유리
 *
 * 목적: 인스턴스의 생성을 위임하고 싶어서 만든것.
 */

public class SimpleBeanFactory implements BeanFacotryInterface {
	private String propertyPath;
	private Properties prop = new Properties();
	private HashMap<Class<?>, Object> map = new HashMap<Class<?>, Object>();

	public SimpleBeanFactory(String propertyPath) {
		this.propertyPath = propertyPath;
	}

	public <T> Object getInstance(Class<T> type) {

		Constructor<T> declaredConstructor = null;
		try {
			declaredConstructor = type.getDeclaredConstructor();

			if (map.containsKey(type)) {
				return map.get(type);
			}
			declaredConstructor.setAccessible(true);

			Object instance = declaredConstructor.newInstance();
			map.put(type, instance);

			return instance;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Object getInstance(String beanName) {

		try {
			prop.load(ResourceUtil.resourceAsInputStream(propertyPath)); //properties 파일을 로드
			String clazzName = prop.getProperty(beanName); //beanName과 일치하는 값을 가져옴
			Class<?> forName = Class.forName(clazzName); //값에서 클래스이름을 추출함

			return getInstance(forName); //Overloading함수로 Instance 값을 넘겨줌
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
