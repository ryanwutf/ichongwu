package co.ichongwu.vidser.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Constructor;

import org.springframework.core.convert.ConversionService;

import co.ichongwu.vidser.common.service.SilentConversionService;


public class ConversionUtil {

    private static ConversionService INSTANCE = SilentConversionService.getInstance();

    public static boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return INSTANCE.canConvert(sourceType, targetType);
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        return INSTANCE.convert(source, targetType);
    }
    
    public static <T> T convert(Object source, T defaultValue) {
        @SuppressWarnings("unchecked")
        Class<T> targetType = (Class<T>) defaultValue.getClass();
        T t = convert(source, targetType);
        return t == null ? defaultValue : t;
    }
    
    public static <T> T construct(Constructor<T> constructor, Object... objects){
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length && i < objects.length; i ++) {
            args[i] = convert(objects[i], paramTypes[i]);
        }
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
        }
        return null;
    }

}
