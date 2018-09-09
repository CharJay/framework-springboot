package com.framework.core.utils.helper;

import java.util.Collection;
import java.util.Map;

import com.framework.core.exception.WebAssertException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 参考org.springframework.util.Assert.
 * <p>
 * 修改异常类型:此类中断言错误抛出 {@link WebAssertException} &nbsp;<br>
 * 重载方法,格式化输出消息.
 * </p>
 */
@SuppressWarnings( { "rawtypes", "unchecked" } )
public abstract class Assert {
    
    /**
     * Assert a boolean expression, throwing {@code WebAssertException} if the test result is {@code false}.
     * 
     * <pre class="code">
     * Assert.isTrue( i &gt; 0, &quot;The value must be greater than zero&quot; );
     * </pre>
     * 
     * @param expression
     *            a boolean expression
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if expression is {@code false}
     */
    public static void isTrue( boolean expression, int errCode, String message ) {
        if (!expression) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void isTrue( boolean expression, int errCode, String format, Object... obj ) {
        if (!expression) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert a boolean expression, throwing {@code WebAssertException} if the test result is {@code false}.
     * 
     * <pre class="code">
     * Assert.isTrue( i &gt; 0 );
     * </pre>
     * 
     * @param expression
     *            a boolean expression
     * @throws WebAssertException
     *             if expression is {@code false}
     */
    public static void isTrue( boolean expression, CodeEnum code ) {
        isTrue( expression, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that an object is {@code null} .
     * 
     * <pre class="code">
     * Assert.isNull( value, &quot;The value must be null&quot; );
     * </pre>
     * 
     * @param object
     *            the object to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if the object is not {@code null}
     */
    public static void isNull( Object object, int errCode, String message ) {
        if (object != null) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void isNull( Object object, int errCode, String format, Object... obj ) {
        if (object != null) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that an object is {@code null} .
     * 
     * <pre class="code">
     * Assert.isNull( value );
     * </pre>
     * 
     * @param object
     *            the object to check
     * @throws WebAssertException
     *             if the object is not {@code null}
     */
    public static void isNull( Object object, CodeEnum code ) {
        isNull( object, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that an object is not {@code null} .
     * 
     * <pre class="code">
     * Assert.notNull( clazz, &quot;The class must not be null&quot; );
     * </pre>
     * 
     * @param object
     *            the object to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if the object is {@code null}
     */
    public static void notNull( Object object, int errCode, String message ) {
        if (object == null) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void notNull( Object object, int errCode, String format, Object... obj ) {
        if (object == null) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that an object is not {@code null} .
     * 
     * <pre class="code">
     * Assert.notNull( clazz );
     * </pre>
     * 
     * @param object
     *            the object to check
     * @throws WebAssertException
     *             if the object is {@code null}
     */
    public static void notNull( Object object, CodeEnum code ) {
        notNull( object, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the empty String.
     * 
     * <pre class="code">
     * Assert.hasLength( name, &quot;Name must not be empty&quot; );
     * </pre>
     * 
     * @param text
     *            the String to check
     * @param message
     *            the exception message to use if the assertion fails
     * @see StringUtils#hasLength
     */
    public static void hasLength( String text, int errCode, String message ) {
        if (!StringUtils.hasLength( text )) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void hasLength( String text, int errCode, String format, Object... obj ) {
        if (!StringUtils.hasLength( text )) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the empty String.
     * 
     * <pre class="code">
     * Assert.hasLength( name );
     * </pre>
     * 
     * @param text
     *            the String to check
     * @see StringUtils#hasLength
     */
    public static void hasLength( String text, CodeEnum code ) {
        hasLength( text, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that the given String has valid text content; that is, it must not be {@code null} and must contain at least one non-whitespace character.
     * 
     * <pre class="code">
     * Assert.hasText( name, &quot;'name' must not be empty&quot; );
     * </pre>
     * 
     * @param text
     *            the String to check
     * @param message
     *            the exception message to use if the assertion fails
     * @see StringUtils#hasText
     */
    public static void hasText( String text, int errCode, String message ) {
        if (!StringUtils.hasText( text )) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void hasText( String text, int errCode, String format, Object... obj ) {
        if (!StringUtils.hasText( text )) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that the given String has valid text content; that is, it must not be {@code null} and must contain at least one non-whitespace character.
     * 
     * <pre class="code">
     * Assert.hasText( name, &quot;'name' must not be empty&quot; );
     * </pre>
     * 
     * @param text
     *            the String to check
     * @see StringUtils#hasText
     */
    public static void hasText( String text, CodeEnum code ) {
        hasText( text, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that the given text does not contain the given substring.
     * 
     * <pre class="code">
     * Assert.doesNotContain( name, &quot;rod&quot;, &quot;Name must not contain 'rod'&quot; );
     * </pre>
     * 
     * @param textToSearch
     *            the text to search
     * @param substring
     *            the substring to find within the text
     * @param message
     *            the exception message to use if the assertion fails
     */
    public static void doesNotContain( String textToSearch, String substring, int errCode, String message ) {
        if (StringUtils.hasLength( textToSearch ) && StringUtils.hasLength( substring )
                && textToSearch.indexOf( substring ) != -1) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void voiddoesNotContain( String textToSearch, String substring, int errCode, String format, Object... obj ) {
        if (StringUtils.hasLength( textToSearch ) && StringUtils.hasLength( substring )
                && textToSearch.indexOf( substring ) != -1) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that the given text does not contain the given substring.
     * 
     * <pre class="code">
     * Assert.doesNotContain( name, &quot;rod&quot; );
     * </pre>
     * 
     * @param textToSearch
     *            the text to search
     * @param substring
     *            the substring to find within the text
     */
    public static void doesNotContain( String textToSearch, String substring, CodeEnum code ) {
        doesNotContain( textToSearch, substring, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that an array has elements; that is, it must not be {@code null} and must have at least one element.
     * 
     * <pre class="code">
     * Assert.notEmpty( array, &quot;The array must have elements&quot; );
     * </pre>
     * 
     * @param array
     *            the array to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if the object array is {@code null} or has no elements
     */
    public static void notEmpty( Object[] array, int errCode, String message ) {
        if (ObjectUtils.isEmpty( array )) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void notEmpty( Object[] array, int errCode, String format, Object... obj ) {
        if (ObjectUtils.isEmpty( array )) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that an array has elements; that is, it must not be {@code null} and must have at least one element.
     * 
     * <pre class="code">
     * Assert.notEmpty( array );
     * </pre>
     * 
     * @param array
     *            the array to check
     * @throws WebAssertException
     *             if the object array is {@code null} or has no elements
     */
    public static void notEmpty( Object[] array, CodeEnum code ) {
        notEmpty( array, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     * 
     * <pre class="code">
     * Assert.noNullElements( array, &quot;The array must have non-null elements&quot; );
     * </pre>
     * 
     * @param array
     *            the array to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if the object array contains a {@code null} element
     */
    public static void noNullElements( Object[] array, int errCode, String message ) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    throw new WebAssertException( errCode, message );
                }
            }
        }
    }
    
    public static void noNullElements( Object[] array, int errCode, String format, Object... obj ) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    throw new WebAssertException( errCode, String.format( format, obj ) );
                }
            }
        }
    }
    
    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     * 
     * <pre class="code">
     * Assert.noNullElements( array );
     * </pre>
     * 
     * @param array
     *            the array to check
     * @throws WebAssertException
     *             if the object array contains a {@code null} element
     */
    public static void noNullElements( Object[] array, CodeEnum code ) {
        noNullElements( array, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that a collection has elements; that is, it must not be {@code null} and must have at least one element.
     * 
     * <pre class="code">
     * Assert.notEmpty( collection, &quot;Collection must have elements&quot; );
     * </pre>
     * 
     * @param collection
     *            the collection to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if the collection is {@code null} or has no elements
     */
    public static void notEmpty( Collection collection, int errCode, String message ) {
        if (CollectionUtils.isEmpty( collection )) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void notEmpty( Collection collection, int errCode, String format, Object... obj ) {
        if (CollectionUtils.isEmpty( collection )) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that a collection has elements; that is, it must not be {@code null} and must have at least one element.
     * 
     * <pre class="code">
     * Assert.notEmpty( collection, &quot;Collection must have elements&quot; );
     * </pre>
     * 
     * @param collection
     *            the collection to check
     * @throws WebAssertException
     *             if the collection is {@code null} or has no elements
     */
    public static void notEmpty( Collection collection, CodeEnum code ) {
        notEmpty( collection, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and must have at least one entry.
     * 
     * <pre class="code">
     * Assert.notEmpty( map, &quot;Map must have entries&quot; );
     * </pre>
     * 
     * @param map
     *            the map to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws WebAssertException
     *             if the map is {@code null} or has no entries
     */
    public static void notEmpty( Map map, int errCode, String message ) {
        if (CollectionUtils.isEmpty( map )) {
            throw new WebAssertException( errCode, message );
        }
    }
    
    public static void notEmpty( Map map, int errCode, String format, Object... obj ) {
        if (CollectionUtils.isEmpty( map )) {
            throw new WebAssertException( errCode, String.format( format, obj ) );
        }
    }
    
    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and must have at least one entry.
     * 
     * <pre class="code">
     * Assert.notEmpty( map );
     * </pre>
     * 
     * @param map
     *            the map to check
     * @throws WebAssertException
     *             if the map is {@code null} or has no entries
     */
    public static void notEmpty( Map map, CodeEnum code ) {
        notEmpty( map, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that the provided object is an instance of the provided class.
     * 
     * <pre class="code">
     * Assert.instanceOf( Foo.class, foo );
     * </pre>
     * 
     * @param clazz
     *            the required class
     * @param obj
     *            the object to check
     * @throws WebAssertException
     *             if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf( Class clazz, Object obj, CodeEnum code ) {
        isInstanceOf( clazz, obj, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that the provided object is an instance of the provided class.
     * 
     * <pre class="code">
     * Assert.instanceOf( Foo.class, foo );
     * </pre>
     * 
     * @param type
     *            the type to check against
     * @param obj
     *            the object to check
     * @param message
     *            a message which will be prepended to the message produced by the function itself, and which may be used to provide context. It should normally end in a ": " or ". " so that the
     *            function generate message looks ok when prepended to it.
     * @throws WebAssertException
     *             if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf( Class type, Object obj, int errCode, String message ) {
        notNull( type, errCode, message );
        if (!type.isInstance( obj )) {
            throw new WebAssertException( errCode, (StringUtils.hasLength( message ) ? message + " " : "") + "Object of class ["
                    + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type );
        }
    }
    
    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 
     * <pre class="code">
     * Assert.isAssignable( Number.class, myClass );
     * </pre>
     * 
     * @param superType
     *            the super type to check
     * @param subType
     *            the sub type to check
     * @throws WebAssertException
     *             if the classes are not assignable
     */
    public static void isAssignable( Class superType, Class subType, CodeEnum code ) {
        isAssignable( superType, subType, code.getCode(), code.getDescr() );
    }
    
    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 
     * <pre class="code">
     * Assert.isAssignable( Number.class, myClass );
     * </pre>
     * 
     * @param superType
     *            the super type to check against
     * @param subType
     *            the sub type to check
     * @param message
     *            a message which will be prepended to the message produced by the function itself, and which may be used to provide context. It should normally end in a ": " or ". " so that the
     *            function generate message looks ok when prepended to it.
     * @throws WebAssertException
     *             if the classes are not assignable
     */
    public static void isAssignable( Class superType, Class subType, int errCode, String message ) {
        notNull( superType, errCode, message );
        if (subType == null || !superType.isAssignableFrom( subType )) {
            throw new WebAssertException( errCode, message + subType + " is not assignable to " + superType );
        }
    }
    
    public static void hasLength( CodeEnum code, String... array ) {
        hasLength( code.getCode(), code.getDescr(), array );
    }
    
    public static void hasLength( int errCode, String message, String... array ) {
        notEmpty( array, errCode, message );
        for (String string : array) {
            hasLength( string, errCode, message );
        }
    }
    
    public static void hasText( CodeEnum code, String... array ) {
        hasText( code.getCode(), code.getDescr(), array );
    }
    
    public static void hasText( int errCode, String message, String... array ) {
        notEmpty( array, errCode, message );
        for (String string : array) {
            hasText( string, errCode, message );
        }
    }
    
    public static void notNull( CodeEnum code, Object... array ) {
        notNull( code.getCode(), code.getDescr(), array );
    }
    
    public static void notNull( int errCode, String message, Object... array ) {
        notEmpty( array, errCode, message );
        for (Object object : array) {
            notNull( object, errCode, message );
        }
    }
    
}
