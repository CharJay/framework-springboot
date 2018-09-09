package com.framework.core.utils.crypto;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * 加密算法工具
 * 
 * @author
 */
public final class CipherUtil {

	private CipherUtil() {
	}

	public static final int SALT_BYTE_SIZE = 32;

	public static final Charset charset = Charset.forName("UTF-8");

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * 对字符串使用sha256计算哈希值,结果转为base64字符串
	 * 
	 * @author
	 * @param source
	 * @return
	 */
	public static String sha256Hash2Base64(String source) {
		return new Sha256Hash(source).toBase64();
	}

	/**
	 * 对字符串使用sha256计算哈希值,结果转为十六进制字符串
	 * 
	 * @author
	 * @param source
	 * @return
	 */
	public static String sha256Hash2Hex(String source) {
		return new Sha256Hash(source).toHex();
	}

	/**
	 * 对字符串使用sha256计算哈希值,结果转为十六进制字符串
	 * 
	 * @author
	 * @param source
	 * @return
	 */
	public static String sha256Hash2Hex(String source, String salt) {
		return new Sha256Hash(source, salt).toHex();
	}

	/**
	 * 对字符串使用sha256计算哈希值,结果转为十六进制字符串
	 * 
	 * @author
	 * @param source
	 * @return
	 */
	public static String sha256Hash2Hex(String source, String salt, int hashIterations) {
		return new Sha256Hash(source, salt, hashIterations).toHex();
	}

	/**
	 * 对字符串使用sha256和加密盐计算哈希值,结果转为base64字符串
	 * 
	 * @author
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String sha256Hash2Base64(String source, String salt) {
		return new Sha256Hash(source, salt).toBase64();
	}

	/**
	 * 对字符串使用sha256和加密盐迭代计算哈希值,结果转为base64字符串
	 * 
	 * @author
	 * @param source
	 * @param salt
	 * @param hashIterations
	 * @return
	 */
	public static String sha256Hash2Base64(String source, String salt, int hashIterations) {
		return new Sha256Hash(source, decBase64Str(salt), hashIterations).toBase64();
	}

	/**
	 * 对字符串使用sha1计算哈希值,结果转为十六进制字符串
	 * 
	 * @author
	 * @param source
	 * @return
	 */
	public static String sha1Hash2Hex(String source) {
		return new Sha1Hash(source).toHex();
	}

	/**
	 * 对字符串使用md5计算哈希值,结果转为十六进制字符串
	 * 
	 * @author
	 * @param source
	 * @return
	 */
	public static String md5Hash2Hex(String source) {
		return new Md5Hash(source).toHex();
	}

	/**
	 * 生成随机的加密盐字节数组,结果转为base64字符串
	 * 
	 * @author
	 * @return
	 */
	public static String gnerateSaltBase64() {
		return Base64.encodeBase64String(gnerateSaltByte());
	}

	/**
	 * 生成随机TOKEN，默认byteSize=8。
	 *
	 * @see #generateToken(int byteSize)
	 * @return
	 */
	public static String generateToken() {
		return generateToken(8);
	}

	/**
	 * 生成随机TOKEN。<br>
	 * 随机生成MD5,转换为十六进制字符串返回<br>
	 *
	 * @param byteSize
	 *            随机盐的长度
	 * @return
	 */
	public static String generateToken(int byteSize) {
		byte[] uuidBytes = UUID.randomUUID().toString().getBytes(charset);
		byte[] randomBytes = gnerateSaltByte(byteSize);

		return new Md5Hash(uuidBytes, randomBytes).toHex();
	}

	/**
	 * 生成十六位随机的加密盐字节数组
	 * 
	 * @author
	 * @return
	 */
	public static byte[] gnerateSaltByte() {
		return gnerateSaltByte(SALT_BYTE_SIZE);
	}

	/**
	 * 生成指定长度的随机的加密盐
	 * 
	 * @author
	 * @param byteSize
	 * @return
	 */
	public static byte[] gnerateSaltByte(int byteSize) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[byteSize];
		random.nextBytes(salt);
		return salt;
	}

	/**
	 * "慢"比较,此方法会比对完两个数组所有的内容
	 * 
	 * @author
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	/**
	 * "慢"比较,此方法会比对完两个字符串所有的内容
	 * 
	 * @author
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean slowEquals(String a, String b) {
		return slowEquals(a.getBytes(charset), b.getBytes(charset));
	}

	/**
	 * 字节数组转换为十六进制字符串
	 * 
	 * @author
	 * @param array
	 * @return
	 */
	public static String byte2HexStr(byte[] array) {
		return new String(byte2Hex(array));

	}

	/**
	 * 字节数组转换为十六进制char数组
	 * 
	 * @author
	 * @param data
	 * @return
	 */
	public static char[] byte2Hex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}

	/**
	 * 十六进制字符串转为字节数组
	 * 
	 * @author
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexStr2Bytes(String hexStr) {
		return hex2Byte(hexStr.toCharArray());
	}

	/**
	 * 十六进制char数组转为字节数组
	 * 
	 * @author
	 * @param data
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static byte[] hex2Byte(char[] data) throws IllegalArgumentException {

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new IllegalArgumentException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	private static int toDigit(char ch, int index) throws IllegalArgumentException {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Illegal hexadecimal charcter " + ch + " at index " + index);
		}
		return digit;
	}

	/**
	 * base64字符串解析为字节数组
	 * 
	 * @author
	 * @param str
	 * @return
	 */
	public static byte[] decBase64Str(String str) {
		return Base64.decodeBase64(str);
	}

}