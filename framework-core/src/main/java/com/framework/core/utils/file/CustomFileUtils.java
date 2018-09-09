package com.framework.core.utils.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.framework.core.utils.http.SimpleHttpRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class CustomFileUtils {
    private static Logger LOGGER=LoggerFactory.getLogger(SimpleHttpRequest.class);
    private CustomFileUtils() {
    }
    
    /**
     * 便捷的关闭流。对于可能抛出的异常，方法内部仅把异常信息输出日志，可根据具体需要修改。<br>
     * <br>
     * 通常只需关闭外层流，底层流会隐式的关闭。<br>
     * 你也可以显式的关闭所有流，注意传参顺序：外层流-->底层流。<br>
     * 若是非嵌套的多个流，则可忽略传参顺序。
     * 
     * @param s
     *            需要关闭的流
     */
    public static void closeStreams( AutoCloseable... s ) {
        if (s == null)
            return;
        for (AutoCloseable _s : s) {
            if (_s != null)
                try {
                    _s.close();
                } catch (Exception e) {
                    LOGGER.error( "尝试关闭流时出错{}", e );
                }
        }
    }
    
    /**
     * 从文件名中截取文件格式，返回最后一个点号（包含）之后的字符<br>
     * filename等于null时返回""<br>
     * 
     * @author
     * @param filename
     * @return
     */
    public static String getExt( String filename ) {
        if (filename != null && filename.lastIndexOf( "." ) != -1) {
            return filename.substring( filename.lastIndexOf( "." ) );
        }
        return "";
    }
    
    /**
     * 从文件名中截取文件格式，返回最后一个点号（不包含）之后的字符<br>
     * filename等于null时返回""<br>
     * 
     * @author
     * @param filename
     * @return
     */
    public static String getExtNoDot( String filename ) {
        if (filename != null && !filename.endsWith( "." ) && filename.lastIndexOf( "." ) != -1) {
            return filename.substring( filename.lastIndexOf( "." ) + 1 );
        }
        return "";
    }
    
    public static String getFilename(String path){
        return path.substring( path.lastIndexOf( "/" ) + 1 );
    }
    
    /**
     * 去除路径两边的斜线"/"，不匹配反斜线"\"<br>
     * if path is null return null;<br>
     * if path is "" return "";<br>
     * if path is "/" or "//" return "";<br>
     * other
     * 
     * @author
     * @param path
     * @return
     */
    public static String trimSlash( String path ) {
        if (path == null) {
            return null;
        }
        if (path.length() == 0) {
            return path;
        }
        if (path.startsWith( "/" )) {
            if (path.length() == 1) {
                return "";
            } else {
                path = path.substring( 1 );
            }
        }
        if (path.endsWith( "/" )) {
            if (path.length() == 1) {
                return "";
            } else {
                path = path.substring( 0, path.length() - 1 );
            }
        }
        return path;
    }
    /**
     * {@link org.apache.commons.io.FileUtils#copyFile(File, File) }
     *
     * @param srcDir
     * @param destDir
     * @throws IOException
     */
    public static void copyDirectory( File srcDir, File destDir  ) throws IOException {
        FileUtils.copyDirectory( srcDir, destDir );
    }
    /**
     * 复制文件夹
     *
     * @param srcDir
     * @param destDir
     * @param fileFilter 选择器，定义文件夹或文件是否需要复制
     * @throws IOException
     */
    public static void copyDirectory( File srcDir, File destDir, FileFilter fileFilter ) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException( "Source must not be null" );
        }
        if (destDir == null) {
            throw new NullPointerException( "Destination must not be null" );
        }
        if (srcDir.exists() == false) {
            throw new FileNotFoundException( "Source '" + srcDir + "' does not exist" );
        }
        if (srcDir.isDirectory() == false) {
            throw new IOException( "Source '" + srcDir + "' exists but is not a directory" );
        }
        if (srcDir.getCanonicalPath().equals( destDir.getCanonicalPath() )) {
            throw new IOException( "Source '" + srcDir + "' and destination '" + destDir + "' are the same" );
        }
        doCopyDirectory( srcDir, destDir, true, fileFilter );
    }
    
    private static void doCopyDirectory( File srcDir, File destDir, boolean preserveFileDate, FileFilter fileFilter ) throws IOException {
        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException( "Destination '" + destDir + "' exists but is not a directory" );
            }
        } else {
            if (destDir.mkdirs() == false) {
                throw new IOException( "Destination '" + destDir + "' directory cannot be created" );
            }
            if (preserveFileDate) {
                destDir.setLastModified( srcDir.lastModified() );
            }
        }
        if (destDir.canWrite() == false) {
            throw new IOException( "Destination '" + destDir + "' cannot be written to" );
        }
        // recurse
        File[] files = srcDir.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException( "Failed to list contents of " + srcDir );
        }
        for (int i = 0; i < files.length; i++) {
            File copiedFile = new File( destDir, files[i].getName() );
            if (files[i].isDirectory()) {
                if( fileFilter == null || fileFilter.accept( files[i] ) ){
                    doCopyDirectory( files[i], copiedFile, preserveFileDate, fileFilter );
                }
            } else {
                if( fileFilter == null || fileFilter.accept( files[i] ) ){
                    doCopyFile( files[i], copiedFile, preserveFileDate );
                }
            }
        }
    }
    
    private static void doCopyFile( File srcFile, File destFile, boolean preserveFileDate ) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException( "Destination '" + destFile + "' exists but is a directory" );
        }
        
        FileInputStream input = new FileInputStream( srcFile );
        try {
            FileOutputStream output = new FileOutputStream( destFile );
            try {
                IOUtils.copy( input, output );
            } finally {
                IOUtils.closeQuietly( output );
            }
        } finally {
            IOUtils.closeQuietly( input );
        }
        
        if (srcFile.length() != destFile.length()) {
            throw new IOException( "Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'" );
        }
        if (preserveFileDate) {
            destFile.setLastModified( srcFile.lastModified() );
        }
    }
}
