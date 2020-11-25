package top.gmfcj.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SpringUtils implements BeanFactoryPostProcessor {

    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");


    private static ConfigurableListableBeanFactory beanFactory;

    public SpringUtils() {
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = beanFactory.getBean(clz);
        return result;
    }

    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    public static void copyBeanProp(Object dest, Object src) {
        List<Method> destSetters = getSetterMethods(dest);
        List srcGetters = getGetterMethods(src);

        try {
            Iterator var4 = destSetters.iterator();

            while (var4.hasNext()) {
                Method setter = (Method) var4.next();
                Iterator var6 = srcGetters.iterator();

                while (var6.hasNext()) {
                    Method getter = (Method) var6.next();
                    if (isMethodPropEquals(setter.getName(), getter.getName()) && setter.getParameterTypes()[0].equals(getter.getReturnType())) {
                        setter.invoke(dest, getter.invoke(src));
                    }
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    public static List<Method> getSetterMethods(Object obj) {
        List<Method> setterMethods = new ArrayList();
        Method[] methods = obj.getClass().getMethods();
        Method[] var3 = methods;
        int var4 = methods.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && method.getParameterTypes().length == 1) {
                setterMethods.add(method);
            }
        }

        return setterMethods;
    }

    public static List<Method> getGetterMethods(Object obj) {
        List<Method> getterMethods = new ArrayList();
        Method[] methods = obj.getClass().getMethods();
        Method[] var3 = methods;
        int var4 = methods.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && method.getParameterTypes().length == 0) {
                getterMethods.add(method);
            }
        }

        return getterMethods;
    }

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(3).equals(m2.substring(3));
    }

}
