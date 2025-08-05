package com.ashokit.cloudinarySignatureUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyUtils {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        BeanWrapper trgWrapper = new BeanWrapperImpl(target);

        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if ("class".equals(propertyName)) continue;

            Object srcValue = srcWrapper.getPropertyValue(propertyName);
            if (srcValue != null) {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null && trgWrapper.isWritableProperty(propertyName)) {
                    trgWrapper.setPropertyValue(propertyName, srcValue);
                }
            }
        }
    }
}

