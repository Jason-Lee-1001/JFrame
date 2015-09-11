package com.studio.jframework.database;

import com.studio.jframework.reflect.ReflectHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jason
 */
public class TableConstructor {

    private DatabaseHelper mDbHelper;

    public TableConstructor(DatabaseHelper helper) {
        this.mDbHelper = helper;
    }

    public <T> boolean create(Class<T> t) {
        boolean succeed = false;
        Map<String, Class> map = ReflectHelper.getFieldAndType(t.getCanonicalName());

        StringBuilder sql = new StringBuilder();
        if (map != null && map.size() > 0) {
            String packageName = t.getCanonicalName();
            String tableName = packageName.substring(packageName.lastIndexOf(".") + 1);
            sql.append("create table if not exists ").append(tableName).append(" (");
            Set<?> keySet = map.keySet();
            for (Object key : keySet) {
                Class cls = map.get(key);
                String typeName = cls.getCanonicalName();
                switch (typeName) {
                    case "java.lang.String":
                        sql.append(key).append(" text, ");
                        break;
                    case "short":
                        sql.append(key).append(" integer, ");
                        break;
                    case "int":
                        sql.append(key).append(" integer, ");
                        break;
                    case "long":
                        sql.append(key).append(" integer(11), ");
                        break;
                    case "java.util.List":
                        try {
                            Field field = t.getDeclaredField(key.toString());
                            Type type = field.getGenericType();
                            ParameterizedType pType = (ParameterizedType) type;
                            Class innerCls = Class.forName(pType.getActualTypeArguments()[0].toString().replace("class ", ""));
                            create(innerCls);
                            sql.append(key).append(" integer, ");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            sql.replace(sql.length() - 2, sql.length(), "");
            sql.append(");");
            mDbHelper.getWritableDatabase().execSQL(sql.toString());
            System.out.println(sql);
            succeed = true;
        }
        return succeed;
    }
}
