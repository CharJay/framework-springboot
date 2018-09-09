package com.framework.core.utils.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**

 * JSON工具类。

 * 转换失败会抛出运行时异常。

 * 

 * @author

 */
@SuppressWarnings( "rawtypes" )
public final class CustomJsonUtils {
    
    private CustomJsonUtils() {
    }
    
    private static Logger          LOGGER      = LoggerFactory.getLogger( CustomJsonUtils.class );
    
    private static CustomJsonUtils jm;
    private ObjectMapper           mapper      = null;
    private TypeFactory            typeFactory = null;
    
    /**

     * 获取单例

     * 

     * @author

     * @return

     */
    public static CustomJsonUtils getInstance() {
        if (jm == null) {
            jm = new CustomJsonUtils();
            jm.mapper = new ObjectMapper();
            jm.mapper.setDateFormat( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) );
            jm.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jm.typeFactory = jm.mapper.getTypeFactory();
        }
        return jm;
    }
    
    public static CustomJsonUtils createInstance(DateFormat dateFormat ) {
        CustomJsonUtils ins = new CustomJsonUtils();
        ins.mapper = new ObjectMapper();
        ins.typeFactory = ins.mapper.getTypeFactory();
        ins.mapper.setDateFormat( dateFormat );
        
        return ins;
    }
    
    public void writeValue( File f, Object object ) {
        try {
            mapper.writeValue( f, object );
        } catch (IOException e) {
            LOGGER.error( "对象输出到writer出错.Class:{}", object.getClass(), e );
            throw new RuntimeException( e );
        }
    }
    
    public void writeValue( Writer w, Object object ) {
        try {
            mapper.writeValue( w, object );
        } catch (IOException e) {
            LOGGER.error( "对象输出到writer出错.Class:{}", object.getClass(), e );
            throw new RuntimeException( e );
        }
    }
    
    public void writeValue( OutputStream os, Object object ) {
        try {
            mapper.writeValue( os, object );
        } catch (IOException e) {
            LOGGER.error( "对象输出到OutputStream出错.Class:{}", object.getClass(), e );
            throw new RuntimeException( e );
        }
    }
    
    public byte[] writeValueAsBytes( Object object ) {
        try {
            return mapper.writeValueAsBytes( object );
        } catch (IOException e) {
            LOGGER.error( "对象转换为byte[]出错.Class:{}", object.getClass(), e );
            throw new RuntimeException( e );
        }
    }
    
    public String writeValueAsString( Object object ) {
        try {
            return mapper.writeValueAsString( object );
        } catch (IOException e) {
            LOGGER.error( "对象转换为json字符串出错.Class:{}", object.getClass(), e );
            throw new RuntimeException( e );
        }
    }
    
    public <T> T readValue( byte[] content, Class<T> valueType ) {
        if (content == null)
            return null;
        try {
            return mapper.readValue( content, valueType );
        } catch (IOException e) {
            LOGGER.error( "byte[]转换为对象出错.Class:{}.", valueType, e );
            throw new RuntimeException( e );
        }
    }
    
    public <T> T readValue( String content, Class<T> valueType ) {
        if (content == null)
            return null;
        try {
            return mapper.readValue( content, valueType );
        } catch (IOException e) {
            LOGGER.error( "json字符串转换为对象出错.json:{},Class:{}.", content, valueType, e );
            throw new RuntimeException( e );
        }
    }
    
    public <T> T readValue( String content, Class<T> valueType, Class... elementType ) {
        if (content == null)
            return null;
        if (elementType == null || elementType.length == 0)
            return readValue( content, valueType );
        
        try {
            return mapper.readValue( content, contstruct( valueType, elementType ) );
        } catch (IOException e) {
            LOGGER.error( "json字符串转换为对象出错.json:{},Class:{}.", content, valueType, e );
            throw new RuntimeException( e );
        }
    }
    
    public <T> List<T> readValueToList( String content, Class<? extends List> listType, Class valueType ) {
        if (content == null)
            return null;
        try {
            return mapper.readValue( content, typeFactory.constructCollectionType( listType, valueType ) );
        } catch (IOException e) {
            LOGGER.error( "json字符串转换为对象出错.json:{},listType:{}, valueType:{}.", content, listType, valueType, e );
            throw new RuntimeException( e );
        }
    }
    
    public <T> List<T> readValueToList( String content, Class<? extends List> listType, Class valueType, Class... elementType ) {
        if (content == null)
            return null;
        if (elementType == null || elementType.length == 0)
            return readValueToList( content, listType, valueType );
        
        try {
            return mapper.readValue( content,
                    typeFactory.constructCollectionType( listType, contstruct( valueType, elementType ) ) );
        } catch (IOException e) {
            LOGGER.error( "json字符串转换为对象出错.json:{},listType:{}, valueType:{}, elementType:{}", content, listType, valueType,
                    elementType, e );
            throw new RuntimeException( e );
        }
    }
    
    public String readValueAsString( String content, String fieldName ) {
        if (content == null || fieldName == null)
            return null;
        
        try {
            return mapper.readTree( content ).get( fieldName ).asText();
        } catch (IOException e) {
            LOGGER.error( "json字符串解析节点出错.json:{},fieldName:{}.", content, fieldName, e );
            throw new RuntimeException( e );
        }
        
    }
    
    public String readJsonStrValue( String content, String fieldName ) {
        if (content == null || fieldName == null)
            return null;
        
        try {
            Object obj = mapper.readTree( content ).get( fieldName );
            return obj == null ? null : obj.toString();
        } catch (IOException e) {
            LOGGER.error( "json字符串解析节点出错.json:{},fieldName:{}.", content, fieldName, e );
            throw new RuntimeException( e );
        }
        
    }
    
    /**

     * 对象解析为JSONArray字符串

     * 

     * @author

     * @param object

     *            null时返回[]

     * @return

     * @throws IOException

     */
    public String obj2JSONArrayStr( Object object ) {
        if (object == null) {
            return "[]";
        }
        if (object instanceof Collection || object.getClass().isArray()) {
            return writeValueAsString( object );
        }
        List<Object> list = new ArrayList<Object>();
        list.add( object );
        return writeValueAsString( list );
    }
    
    private JavaType contstruct( Class valueType, Class[] elementType ) {
        JavaType jtype = null;
        if (elementType.length == 1) {
            jtype = typeFactory.constructParametricType( valueType, elementType[0] );
        } else {
            jtype = typeFactory
                    .constructParametricType( elementType[elementType.length - 2], elementType[elementType.length - 1] );
            for (int i = elementType.length - 3; i > 1; i--) {
                jtype = typeFactory.constructParametricType( elementType[i], jtype );
            }
            jtype = typeFactory.constructParametricType( valueType, jtype );
        }
        return jtype;
    }
    
}