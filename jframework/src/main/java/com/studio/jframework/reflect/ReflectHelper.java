package com.studio.jframework.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectHelper {
    public static Object getField(Object paramObject, Class<?> paramClass, String paramString) throws NoSuchFieldException {
        if ((paramObject == null) || (paramClass == null) || (paramString == null))
            throw new IllegalArgumentException("parameter can not be null!");
        try {
            Field localField = paramClass.getDeclaredField(paramString);
            localField.setAccessible(true);
            Object localObject = localField.get(paramObject);
            return localObject;
        } catch (Exception localException) {
        }
        throw new NoSuchFieldException(paramString);
    }

    public static Object getField(Object paramObject, String paramString) throws NoSuchFieldException {
        if ((paramObject == null) || (paramString == null))
            throw new IllegalArgumentException("parameter can not be null!");
        return getFieldStepwise(paramObject, paramObject.getClass(), paramString);
    }

    private static Object getFieldStepwise(Object paramObject, Class<?> paramClass, String paramString) throws NoSuchFieldException {
        Object localObject1 = paramClass;
        while (localObject1 != null)
            try {
                Object localObject2 = getField(paramObject, (Class) localObject1, paramString);
                return localObject2;
            } catch (NoSuchFieldException localNoSuchFieldException) {
                try {
                    Class localClass = ((Class) localObject1).getSuperclass();
                    localObject1 = localClass;
                } catch (Exception localException) {
                    localObject1 = null;
                }
            }
        throw new NoSuchFieldException(paramString);
    }

    private static Class<?>[] getParamsTypes(Object[] paramArrayOfObject) {
        Class[] arrayOfClass = new Class[paramArrayOfObject.length];
        for (int i = 0; i < arrayOfClass.length; i++)
            arrayOfClass[i] = paramArrayOfObject[i].getClass();
        return arrayOfClass;
    }

    public static Object getStaticField(String paramString1, String paramString2) throws NoSuchFieldException {
        if ((paramString1 == null) || (paramString2 == null)) {
            throw new IllegalArgumentException("parameter can not be null!");
        }
        try {
            Class localClass = Class.forName(paramString1);
            return getFieldStepwise(localClass, localClass, paramString2);
        } catch (ClassNotFoundException localClassNotFoundException) {
            localClassNotFoundException.printStackTrace();
        }
        throw new IllegalArgumentException("className not found");
    }

    private static Object invoke(Class<?> paramClass, Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) throws Exception {
        if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
            Method localMethod1 = paramClass.getMethod(paramString, new Class[0]);
            localMethod1.setAccessible(true);
            return localMethod1.invoke(paramObject, new Object[0]);
        }
        Method localMethod2 = paramClass.getMethod(paramString, paramArrayOfClass);
        localMethod2.setAccessible(true);
        return localMethod2.invoke(paramObject, paramArrayOfObject);
    }

    private static Object invoke(Class<?> paramClass, Object paramObject, String paramString, Object[] paramArrayOfObject) throws Exception {
        if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
            Method localMethod1 = paramClass.getMethod(paramString, new Class[0]);
            localMethod1.setAccessible(true);
            return localMethod1.invoke(paramObject, new Object[0]);
        }
        Method localMethod2 = paramClass.getMethod(paramString, getParamsTypes(paramArrayOfObject));
        localMethod2.setAccessible(true);
        return localMethod2.invoke(paramObject, paramArrayOfObject);
    }

    public static Object invoke(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) throws Exception {
        return invoke(paramObject.getClass(), paramObject, paramString, paramArrayOfClass, paramArrayOfObject);
    }

    public static Object invoke(Object paramObject, String paramString, Object[] paramArrayOfObject) throws Exception {
        return invoke(paramObject.getClass(), paramObject, paramString, paramArrayOfObject);
    }

    public static Object invokeStatic(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) throws Exception {
        Class localClass = Class.forName(paramString1);
        return invoke(localClass, localClass, paramString2, paramArrayOfClass, paramArrayOfObject);
    }

    public static Object invokeStatic(String paramString1, String paramString2, Object[] paramArrayOfObject) throws Exception {
        Class localClass = Class.forName(paramString1);
        return invoke(localClass, localClass, paramString2, paramArrayOfObject);
    }

    public static Object reflectConstructor(Class<?> paramClass, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) throws Exception {
        if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0)) {
            return paramClass.getConstructor(paramArrayOfClass).newInstance(paramArrayOfObject);
        }
        return paramClass.getConstructor(new Class[0]).newInstance(new Object[0]);
    }

    public static Object reflectConstructor(Class<?> paramClass, Object[] paramArrayOfObject) throws Exception {
        if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0)) {
            return paramClass.getConstructor(getParamsTypes(paramArrayOfObject)).newInstance(paramArrayOfObject);
        }
        return paramClass.getConstructor(new Class[0]).newInstance(new Object[0]);
    }

    public static boolean setField(Object paramObject1, Class<?> paramClass, String paramString, Object paramObject2) {
        if ((paramObject1 == null) || (paramClass == null) || (paramString == null))
            throw new IllegalArgumentException("parameter can not be null!");
        try {
            Field localField = paramClass.getDeclaredField(paramString);
            localField.setAccessible(true);
            localField.set(paramObject1, paramObject2);
            return true;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    public static boolean setField(Object paramObject1, String paramString, Object paramObject2) {
        if ((paramObject1 == null) || (paramString == null)) {
            throw new IllegalArgumentException("parameter can not be null!");
        }
        return setFieldStepwise(paramObject1, paramObject1.getClass(), paramString, paramObject2);
    }

    private static boolean setFieldStepwise(Object paramObject1, Class<?> paramClass, String paramString, Object paramObject2) {
        Object localObject = paramClass;
        while (localObject != null) {
            if (setField(paramObject1, (Class) localObject, paramString, paramObject2))
                return true;
            try {
                Class localClass = ((Class) localObject).getSuperclass();
                localObject = localClass;
            } catch (Exception localException) {
                localObject = null;
            }
        }
        return false;
    }

    public static boolean setStaticField(String paramString1, String paramString2, Object paramObject) {
        if ((paramString1 == null) || (paramString2 == null))
            throw new IllegalArgumentException("parameter can not be null!");
        try {
            Class localClass = Class.forName(paramString1);
            return setFieldStepwise(localClass, localClass, paramString2, paramObject);
        } catch (ClassNotFoundException localClassNotFoundException) {
            localClassNotFoundException.printStackTrace();
        }
        throw new IllegalArgumentException("className not found");
    }
}