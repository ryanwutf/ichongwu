package co.ichongwu.vidser.common.service;


import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

public class SilentConversionService implements ConversionService {

    private static SilentConversionService INSTANCE = new SilentConversionService(new DefaultConversionService());

    private ConversionService conversionService;

    public SilentConversionService() {
    }

    public SilentConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public static SilentConversionService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return sourceType == null || targetType == null ? false : conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType == null || targetType == null ? false : conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        if (source != null && targetType != null) {
            try {
                return conversionService.convert(source, targetType);
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source != null && targetType != null) {
            try {
                return conversionService.convert(source, sourceType, targetType);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

}
