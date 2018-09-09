package com.framework.core.db.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;


public class BeanCamelHelper {
    
    /** The class we are mapping to */
    private Class mappedClass;
    private Object obj;

    /** Map of the fields we provide mapping for */
    private Map<String, PropertyDescriptor> mappedFields;

    /** Set of bean properties we provide mapping for */
    private Set<String> mappedProperties;
    
    private List<String> nameByUnderscore=new ArrayList<>();// create_time
    private List<String> nameByCamelCase=new ArrayList<>();// createTime
    private List<Object> nameByValue=new ArrayList<>();// createTime
    private String pkCamelName =null;
    private String pkUnderscoreName =null;
    private Object pkValue =null;
    
    private String tableName =null;

    public BeanCamelHelper(Object obj){
        try {
            initialize( obj );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } 
    }
    public BeanCamelHelper(Class clazz){
        try {
            initialize( clazz );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 所有属性值都是null
     * @return
     */
    public boolean isAllProNull(){
        boolean ret = true;
        for (int i = 0; i < nameByValue.size(); i++) {
            if(nameByValue.get( i )!=null){
                ret= false;
                break;
            }
        }
        return ret;
    }
    /**
     * 把对象中的属性和值，添加begin前缀，放入map中; 
     * 用于在update中区分set和where属性
     * 比如对象：loginName 值为zhangsan, createTime 值2016-02-14 , age 值18
     * begin: max
     * return： { maxloginName = zhangsan , maxcreateTime =2016-02-14, maxage=18 ] 
     */
    public void appendMapCamcelAndValuer(Map<String,Object> map, String begin){
        for (int i = 0; i < nameByValue.size(); i++) {
            if(nameByValue.get( i )!=null){
                map.put( begin+nameByCamelCase.get( i ), nameByValue.get( i ) );
            }
        }
    }
    /**
     * 把非null数据连接成update的set需要的形式
     * 比如对象：loginName 值非空, createTime 值非空 , age 值空的
     * begin: [
     * end: ]
     * join: ;
     * concat: :=
     * return： [ login_name := loginName ; create_time := createTime ] 
     */
    public String getNotNullTogetherStr(String begin, String end, String join, String concat, boolean usePk){
        StringBuilder sb =new StringBuilder();
        for (int i = 0; i < nameByValue.size(); i++) {
            if(nameByValue.get( i )!=null && (usePk || ! nameByCamelCase.get( i ).equals( pkCamelName ) ) ){
                if(sb.length()!=0) sb.append( join );
                
                sb.append("`").append( nameByUnderscore.get( i ) ).append("`")
                .append( concat )
                .append( nameByCamelCase.get( i ) );
            }
        }
        return sb.length()==0?"": sb.insert(0, begin).append(end).toString();
    }
    /**
     * 如果非null，则返回，下划线连接方式的字符串
     * 比如对象：loginName, createTime
     * begin: [
     * end: ]
     * join: ;
     * return： [ login_name ; create_time ] 
     */
    public String getNotNullunderscoreStr(String begin, String end, String join){
        StringBuilder sb =new StringBuilder();
        for (int i = 0; i < nameByValue.size(); i++) {
            if(nameByValue.get( i )!=null){
                if(sb.length()!=0) sb.append( join );
                sb.append( nameByUnderscore.get( i ) );
            }
        }
        return sb.length()==0?"": sb.insert(0, begin).append(end).toString();
    }
    /**
     * 如果非null，则返回，驼峰连接方式的字符串
     * 比如对象：loginName, createTime
     * begin: [
     * end: ]
     * join: ;
     * return： [ loginName ; createTime ] 
     */
    public String getNotNullCamelCaseStr(String begin, String end, String join){
        StringBuilder sb =new StringBuilder();
        for (int i = 0; i < nameByValue.size(); i++) {
            if(nameByValue.get( i )!=null){
                if(sb.length()!=0) sb.append( join );
                sb.append( nameByCamelCase.get( i ) );
            }
        }
        return sb.length()==0?"": sb.insert(0, begin).append(end).toString();
    }
    
    /**
     * Initialize the mapping metadata for the given class.
     * @param mappedClass the mapped class
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    protected void initialize(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
        Class mappedClass = obj.getClass();
        
        this.obj = obj;
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap<String, PropertyDescriptor>();
        this.mappedProperties = new HashSet<String>();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(lowerCaseName(pd.getName()), pd);
                String underscoredName = underscoreName(pd.getName());
                if (!lowerCaseName(pd.getName()).equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }
                this.mappedProperties.add(pd.getName());
                
                nameByUnderscore.add( underscoredName );
                nameByCamelCase.add( pd.getName() );
                
                Object ret = pd.getReadMethod().invoke( obj, new Object[]{} );
                nameByValue.add( ret );
                
                //pk
                if(getPkCamelName()==null){
                    Field field = mappedClass.getDeclaredField( pd.getName() );
                    Id id = field.getAnnotation( Id.class );
                    if(id!=null){
                        pkCamelName =  pd.getName() ;
                        pkUnderscoreName = underscoredName ;
                        pkValue = ret ;
                    }
                }
            }
        }
        
        Table tt = obj.getClass().getAnnotation( Table.class );
        if(tt!=null){
            setTableName( tt.name() );
        }else{
            setTableName( underscoreName(obj.getClass().getSimpleName()) );
        }
    }
    protected void initialize(Class mappedClass) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
        
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap<String, PropertyDescriptor>();
        this.mappedProperties = new HashSet<String>();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(lowerCaseName(pd.getName()), pd);
                String underscoredName = underscoreName(pd.getName());
                if (!lowerCaseName(pd.getName()).equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }
                this.mappedProperties.add(pd.getName());
                
                nameByUnderscore.add( underscoredName );
                nameByCamelCase.add( pd.getName() );
                
                //pk
                if(getPkCamelName()==null){
                    Field field = mappedClass.getDeclaredField( pd.getName() );
                    Id id = field.getAnnotation( Id.class );
                    if(id!=null){
                        pkCamelName =  pd.getName() ;
                        pkUnderscoreName = underscoredName ;
                    }
                }
            }
        }
        
        Object obj2 = mappedClass.newInstance();
        Table tt = obj2.getClass().getAnnotation( Table.class );
        if(tt!=null){
            setTableName( tt.name() );
        }else{
            setTableName( underscoreName(obj2.getClass().getSimpleName()) );
        }
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case.
     * Any upper case letters are converted to lower case with a preceding underscore.
     * @param name the original name
     * @return the converted name
     * @since 4.2
     * @see #lowerCaseName
     */
    protected String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(lowerCaseName(name.substring(0, 1)));
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = lowerCaseName(s);
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            }
            else {
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * Convert the given name to lower case.
     * By default, conversions will happen within the US locale.
     * @param name the original name
     * @return the converted name
     * @since 4.2
     */
    protected String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }
    
    /**
     * 获取table名称，如：sm_user
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }
    /**
     * 获取主键字符串，驼峰类型：如：seqId
     * @return
     */
    public String getPkCamelName() {
        return pkCamelName;
    }
    
    public void setPkCamelName( String pkCamelName ) {
        this.pkCamelName = pkCamelName;
    }
    /**
     * 获取主键字符串，下划线形式，如：seq_id
     * @return
     */
    public String getPkUnderscoreName() {
        return pkUnderscoreName;
    }
    
    public void setPkUnderscoreName( String pkUnderscoreName ) {
        this.pkUnderscoreName = pkUnderscoreName;
    }
    /**
     * 获取主键值
     * @return
     */
    public Object getPkValue() {
        return pkValue;
    }
    
    public void setPkValue( Object pkValue ) {
        this.pkValue = pkValue;
    }
    
    public static void main( String[] args ) {
        
//        StudentPojo obj = new StudentPojo();
//        obj.setAge( 10 );
//        obj.setCreateTime( new Date() );
//        obj.setName( "xxx哈哈:" );
//        BeanCamelHelper b = new BeanCamelHelper( obj );
//        System.out.println( b.getNotNullCamelCaseStr( "", "", " , " ) );
//        System.out.println( b.getNotNullunderscoreStr( ":", "", ", :" ) );
//        System.out.println( b.getNotNullTogetherStr( "", "", ",","=:" , false) );
//        System.out.println( b.getNotNullTogetherStr( "", "", ",","=:" , true) );
//        
//        System.out.println( b.getTableName() );
//        System.out.println( b.getPkCamelName() );
//        System.out.println( b.getPkUnderscoreName() );
//        System.out.println( b.getPkValue() );
    }
}
