package cn.nj.www.my_module.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtils {
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 6. * 提供精确的加法运算 7. * @param v1 被加数 8. * @param v2 加数 9. * @return 两个参数的和
	 * 10.
	 */

	public static int compareWeight(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		b1 = b1.setScale(4, BigDecimal.ROUND_HALF_UP);
		b2 = b2.setScale(4, BigDecimal.ROUND_HALF_UP);
		return b1.compareTo(b2);

	}

	public static int compareMoney(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		b1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP);
		b2 = b2.setScale(2, BigDecimal.ROUND_HALF_UP);
		return b1.compareTo(b2);

	}

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 18. * 提供精确的减法运算 19. * @param v1 被减数 20. * @param v2 减数 21. * @return
	 * 两个参数的差 22.
	 */
	public static double substract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 30. * 提供精确的乘法运算 31. * @param v1 被乘数 32. * @param v2 乘数 33. * @return
	 * 两个参数的积 34.
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 42. * 提供（相对）精确的除法运算,当发生除不尽的情况时, 43. * 精确到小数点以后10位,以后的数字四舍五入. 44. * @param
	 * v1 被除数 45. * @param v2 除数 46. * @return 两个参数的商 47.
	 */
	public static double divide(double v1, double v2) {
		return divide(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 53. * 提供（相对）精确的除法运算. 54. * 当发生除不尽的情况时,由scale参数指 定精度,以后的数字四舍五入. 55. * 56.
	 * * @param v1 被除数 57. * @param v2 除数 58. * @param scale 表示需要精确到小数点以后几位 59.
	 * * @return 两个参数的商 60.
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 72. * 提供精确的小数位四舍五入处理 73. * @param v 需要四舍五入的数字 74. * @param scale 小数点后保留几位
	 * 75. * @return 四舍五入后的结果 76.
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double roundMoney(double v) {

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double roundWeight(double v) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String toMoney(double amount) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(amount);
	}

	/**
	 * 将以元为单位的金额转换成以分为单位的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String toMinuteMoney(double amount) {
		if (compareMoney(amount, 0) == 0)
			return "0";
		DecimalFormat df = new DecimalFormat("0");
		return df.format(multiply(roundMoney(amount), 100));
	}

	/**
	 * 将以元为单位的金额转换成以分为单位的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static double toYuanMoney(double amount) {
		if (compareMoney(amount, 0) == 0)
			return amount;
		return roundMoney(divide(amount, 100));
	}



	/**
	 * 将放大了10000倍后的利率
	 * 
	 * @param rate
	 * @return
	 */
	public static String toMaxRate(double rate) {
		if (compareMoney(rate, 0) == 0)
			return "0";
		DecimalFormat df = new DecimalFormat("0");
		return df.format(multiply(roundMoney(rate), 10000));
	}

	/***
	 * 是否为正整数
	 */
	public static boolean isPositiveInteger(double value) {
		long a = (long) value;
		if (a != value || a < 0) {
			return false;
		}
		return true;
	}

	/***
	 * 是否为正整数
	 */
	public static boolean isPositiveInteger(String value) {
		double d = Double.parseDouble(value);
		long a = (long) d;
		if (a != d || a < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 如果小数点后的数字为0，直接去掉0，列入1.0返回1,1.1返回1.1
	 * @param d
	 * @return
	 */
	public static String doubleTrans(double d) {
		if (Math.round(d) - d == 0) {
			return String.valueOf((long) d);
		}else
			return String.valueOf(d);
	}

}
