/*
 * 文件�?? TimeThread.java
 * �??   权：  Copyright Huawei Tech. Co. Ltd. All Rights Reserved.
 * �??   �?? [该类的简要描述]
 * 创建�?? zhoumi
 * 创建时间:2012-2-14
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package unicom.hand.redeagle.zhfy.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * File通用操作工具
 *
 * @author
 * @version [RCS Client_Handset V100R001C04SPC002, Feb 14, 2012]
 */
public final class FileUtil {
	/**
	 * TAG
	 */
	private static final String TAG = FileUtil.class.getSimpleName();

	/**
	 * 缓冲区大
	 */
	private static final int BUFFER_SIZE = 100 * 1024;

	/**
	 * 下载文件到指定目
	 * 
	 * @param context
	 * @param file
	 * @param id
	 * @throws IOException
	 */
	public static void loadFile(Context context, File file, int id) throws IOException {
		InputStream is = context.getResources().openRawResource(id);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = is.read(buffer)) > 0) {
			fos.write(buffer, 0, count);
		}
		fos.close();
		is.close();
	}

	public static void loadFile(File file, File oldfile) throws IOException {
		FileInputStream is = new FileInputStream(oldfile);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = is.read(buffer)) > 0) {
			fos.write(buffer, 0, count);
		}
		fos.close();
		is.close();
	}
	public static byte[] fileToByte(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] barr = new byte[1024];
			while (true) {
				int r = in.read(barr);
				if (r <= 0) {
					break;
				}
				buffer.write(barr, 0, r);
			}
			return buffer.toByteArray();
		} finally {
			closeStream(in);
		}
	}

	/**
	 *
	 * 将文件的byte[]格式转化成一个文�??
	 *
	 * @param b
	 *            文件的byte[]格式
	 * @param fileName
	 *            文件名称
	 * @return 转化后的文件
	 */
	public static File byteToFile(byte[] b, String fileName) {
		BufferedOutputStream bos = null;
		File file = null;
		try {
			file = new File(fileName);
			if (!file.exists()) {
				File parent = file.getParentFile();
				if (!parent.mkdirs()) {
					// 创建不成功的话，直接返回null
					return null;
				}
			}

			FileOutputStream fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
		} catch (IOException e) {
			Log.e(TAG, "byteToFile error", e);
		} finally {
			closeStream(bos);
		}
		return file;
	}

	/**
	 * 判断文件是否是图片格
	 *
	 * @param fileName
	 *            文件名称
	 * @return true 表示是图片格
	 */
	public static boolean isPictureType(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			String type = fileName.substring(index).toLowerCase();
			if (".png".equals(type) || ".gif".equals(type) || ".jpg".equals(type) || ".bmp".equals(type)
					|| ".jpeg".equals(type)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文�?
				deleteFile(path + "/" + tempList[i]);// 再删除空文件�?
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除文件
	 *
	 * @param path
	 *            路径
	 * @return 是否即时删除成功
	 */
	public static boolean deleteFiles(String path) {
		if (path == null || path.trim().length() < 1) {
			return false;
		}
		try {
			File file = new File(path);
			if (file != null && file.exists() && file.isFile()) {
				try {
					return file.delete();
				} catch (Exception e) {
					Log.e(TAG, "delete file error", e);
					file.deleteOnExit();
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除文件
	 *
	 * @param file
	 * @return 是否即时删除成功
	 */
	public static boolean deleteFile(File file) {
		try {
			if (file != null && file.exists() && file.isFile()) {
				try {
					return file.delete();
				} catch (Exception e) {
					Log.e(TAG, "delete file error", e);
					file.deleteOnExit();
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean closeStream(java.io.Closeable beCloseStream) {
		if (beCloseStream != null) {
			try {
				beCloseStream.close();
				return true;
			} catch (IOException e) {
				Log.e(TAG, "close stream error", e);
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取文件大小
	 *
	 * @param filePath
	 *            文件路径
	 * @return 文件大小
	 */
	public static long getFileLength(String filePath) {
		if (filePath == null || filePath.trim().length() < 1) {
			return 0;
		}
		try {
			File file = new File(filePath);
			return file.length();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 复制文件
	 *
	 * @param origin
	 *            原始文件
	 * @param dest
	 *            目标文件
	 * @return 是否复制成功
	 */
	public static boolean copyFile(File origin, File dest) {
		if (origin == null || dest == null) {
			return false;
		}
		if (!dest.exists()) {
			File parentFile = dest.getParentFile();
			if (!parentFile.exists()) {
				boolean succeed = parentFile.mkdirs();
				if (!succeed) {
					Log.i(TAG, "copyFile failed, cause mkdirs return false");
					return false;
				}
			}
			try {
				dest.createNewFile();
			} catch (Exception e) {
				Log.i(TAG, "copyFile failed, cause createNewFile failed");
				return false;
			}
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(origin);
			out = new FileOutputStream(dest);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			int length = BUFFER_SIZE;
			while (true) {
				if (inC.position() == inC.size()) {
					return true;
				}
				if ((inC.size() - inC.position()) < BUFFER_SIZE) {
					length = (int) (inC.size() - inC.position());
				} else {
					length = BUFFER_SIZE;
				}
				inC.transferTo(inC.position(), length, outC);
				inC.position(inC.position() + length);
			}
		} catch (Exception e) {
			return false;
		} finally {
			closeStream(in);
			closeStream(out);
		}
	}

	/**
	 * 设置没有媒体文件标志
	 * 
	 * @param directory
	 *            文件
	 * @return
	 */
	public static boolean setNoMediaFlag(File directory) {
		File noMediaFile = new File(directory, ".nomedia");
		if (!noMediaFile.exists()) {
			try {
				return noMediaFile.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 通过提供的文件名在默认路径下生成文件
	 * 
	 * @param filePath
	 * @throws IOException
	 *             IOException
	 */
	public static File createFile(String filePath) throws IOException {
		String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
		File folder = getFileByPath(folderPath);
		folder.mkdirs();
		File file = getFileByPath(filePath);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			return createFile(getNextPath(filePath));
		}
		return file;
	}

	/**
	 * 已经有文件名相同文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 新生成的文件路径
	 */
	private static String getNextPath(String path) {
		Pattern pattern = Pattern.compile("\\(\\d{1,}\\)\\.");
		// 除中文不用外，其他的都要
		Matcher matcher = pattern.matcher(path);
		String str = null;
		while (matcher.find()) {
			str = matcher.group(matcher.groupCount());
			System.out.println("[" + str + "]");
		}
		if (str == null) {
			int index = path.lastIndexOf(".");
			if (index != -1) {
				path = path.substring(0, index) + "(1)" + path.substring(index);
			} else {
				path += "(1)";
			}
		} else {
			int index = Integer.parseInt(str.replaceAll("[^\\d]*(\\d)[^\\d]*", "$1")) + 1;
			path = path.replace(str, "(" + index + ").");
		}
		return path;
	}

	/**
	 * 删除路径指向
	 * 
	 * @param filePath
	 * @return true删除成功，false删除失败
	 */
	public static boolean deleteFile(String filePath) {
		File file = getFileByPath(filePath);
		if (file.isFile()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 获取文件路径
	 * 
	 * @param filePath
	 *            String
	 * @return 文件路径
	 */
	public static File getFileByPath(String filePath) {
		filePath = filePath.replaceAll("\\\\", "/");
		boolean isSdcard = false;
		int subIndex = 0;
		if (filePath.indexOf("/sdcard") == 0) {
			isSdcard = true;
			subIndex = 7;
		} else if (filePath.indexOf("/mnt/sdcard") == 0) {
			isSdcard = true;
			subIndex = 11;
		}

		if (isSdcard) {
			if (isExistSdcard()) {
				// 获取SDCard目录,2.2的时候为:/mnt/sdcard
				// 2.1的时候为�??sdcard，所以使用静态方法得到路径会好一点�?
				File sdCardDir = Environment.getExternalStorageDirectory();
				String fileName = filePath.substring(subIndex);
				return new File(sdCardDir, fileName);
			} else if (isEmulator()) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				String fileName = filePath.substring(subIndex);
				return new File(sdCardDir, fileName);
			}
			return null;
		} else {
			return new File(filePath);
		}
	}

	/**
	 * 是否有sdcard
	 * 
	 * @return boolean
	 */
	private static boolean isExistSdcard() {
		if (!isEmulator()) {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		}
		return true;
	}

	/**
	 * 是否是模拟器
	 * 
	 * @return boolean
	 */
	private static boolean isEmulator() {
		return android.os.Build.MODEL.equals("sdk");
	}

	// 读取文件
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = readTextInputStream(is);
			;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}

	public static String readTextInputStream(InputStream is) throws IOException {
		StringBuffer strbuffer = new StringBuffer();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				strbuffer.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return strbuffer.toString();
	}

	public static void writeTextFile(File file, String str) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			out.write(str.getBytes());
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * fore delete a file,thread safe.
	 * 
	 * @param file
	 *            file
	 * @return del result
	 */
	public static boolean forceDeleteFile(File file) {
		boolean result = false;
		int tryCount = 0;
		while (!result && tryCount++ < 10) {
			result = file.delete();
			if (!result) {
				try {
					synchronized (file) {
						file.wait(200);
					}
				} catch (InterruptedException e) {
				}
			}
		}
		return result;
	}

}