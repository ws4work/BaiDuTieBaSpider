package util;

public class CharTransUtil {

	/**
	 * 转义HTML链接中特殊字符
	 * 
	 * @param src
	 * @return src
	 */
	public static String charTransMethod(String src) {
		src = src.replaceAll("&nbsp;", " ");
		src = src.replaceAll("&amp;", "&");
		src = src.replaceAll("&quot;", "\"");
		src = src.replaceAll("&gt;", ">");
		//		src = src.replaceAll("&larr;", "←");
		//		src = src.replaceAll("&uarr;", "↑");
		//		src = src.replaceAll("&rarr;", "→");
		//		src = src.replaceAll("&darr;", "↓");
		//		src = src.replaceAll("&laquo;", "«");
		//		src = src.replaceAll("&raquo;", "»");
		//		src = src.replaceAll("&lsaquo;", "‹");
		//		src = src.replaceAll("&rsaquo;", "›");
		//		src = src.replaceAll("&hellip;", "…");
		//		src = src.replaceAll("&times;", "×");
		return src;
	}
}
