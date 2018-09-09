package com.framework.core.utils.helper;

/**
 * 元角分厘毫
 * 10毫==1厘
 * 10厘==1分
 * 10分==1角
 * 
 * 1元=10*10*10*10（毫）
 *     角   分  厘  毫
 * 0.01分=1毫
 * @author Administrator
 *
 */
public class MoneyUtil {
	
	public static Integer HY_TIMES=10000;//毫元转换的倍数
	public static Integer HJ_TIMES=1000;//毫角转换的倍数
	public static Integer HF_TIMES=100;//毫分转换的倍数
	
	/**
	 * 字符串数字转Long
	 * @param str 待转数字类字符串
	 * @param times 转换倍数
	 * @return
	 */
	public static Long str2Long(String str,Integer times){
		Double du = Double.parseDouble(str);
		du=du*times;
		return du.longValue();
	}
	/**
	 * 0.01分（毫）转元
	 * @param str
	 * @param times
	 * @return
	 */
	public static Double hao2yuan(Long hao){
		Double du = hao.doubleValue();
		Double yuan = du/MoneyUtil.HY_TIMES;
		return yuan;
	}
	 /**
	  * 毫转元 字符串
	  * @param hao
	  * @return
	  */
    public static String hao2yuanStr(Long hao){
        if(hao==0){
            return "0";
        }
        //String.format("%.4f", MoneyUtil.hao2yuan(balance.getMoneyAll()));
        java.text.DecimalFormat df = new java.text.DecimalFormat( "#.##" );
        Double du = hao.doubleValue();
        Double yuan = du/MoneyUtil.HY_TIMES;
        String gold = df.format( yuan );
        return gold;
    }
    /**
     * 毫转角
     * @param hao
     * @return
     */
    public static Double hao2Jiao(Long hao){
        Double du = hao.doubleValue();
        Double fen = du/MoneyUtil.HJ_TIMES;
        return fen;
    }
    public static String hao2jiaoStr(Long hao){
        return changeMoney(hao, MoneyUtil.HJ_TIMES);
    }
	/**
	 * 毫转分
	 * @param hao
	 * @return
	 */
	public static Double hao2fen(Long hao){
		Double du = hao.doubleValue();
		Double fen = du/MoneyUtil.HF_TIMES;
		return fen;
	}
	public static String hao2fenStr(Long hao){
	    return changeMoney(hao, MoneyUtil.HF_TIMES);
	}
	/**
	 * 分转毫
	 * @param hao
	 * @return
	 */
	public static Long fen2hao(Long fen){
		return fen*MoneyUtil.HF_TIMES;
	}
	public static Long fen2hao(String fenStr){
		Long fen = Long.parseLong( fenStr ) ;
		return fen*MoneyUtil.HF_TIMES;
	}
	/**
	 * 分转元
	 */
   public static String fen2yuanStr(Long fen){
       if(fen==0){
           return "0";
       }
       java.text.DecimalFormat df = new java.text.DecimalFormat( "#.##" );
       Double du = fen.doubleValue();
       Double yuan = du/MoneyUtil.HF_TIMES;
       String gold = df.format( yuan );
       return gold;
   }
	/**
	 * 元转0.01分（毫）
	 * @param str
	 * @param times
	 * @return
	 */
	public static Long yuan2hao(Double yuan){
		Double du = yuan*MoneyUtil.HY_TIMES;
		return du.longValue();
	}
	public static Long yuan2hao(String yuan){
		Long ret = str2Long(yuan, MoneyUtil.HY_TIMES);
		return ret;
	}
	/**
	 * 金额倍数转换=金额/倍数
	 * @author jay
	 * 
	 * @param money 金额
	 * @param times 倍数
	 * @return
	 */
	public static String changeMoney(Long money,int times){
	    if(money==0){
	        return "0";
	    }
	    java.text.DecimalFormat df = new java.text.DecimalFormat( "#.##" );
	    Double du = money.doubleValue();
	    Double yuan = du/times;
	    String gold = df.format( yuan );
	    return gold;
	}
	
	public static void main(String[] args) {
	    Long ret=0L;
//		Long ret = ZwUtil.str2Long("a", 1000);
//		Double ret = MoneyUtil.hao2yuan(10000L);
	    
//	    -13370    -12154
//		Long danHao = 13370L;
//        long danFen =  MoneyUtil.hao2fen(danHao).longValue();//分
//        Long realHao=ArithUtil.div(danHao, 1.1).longValue();
//        Long realFen =  MoneyUtil.hao2fen(realHao.longValue()).longValue();//真实分 会少几毫
//        
//		String ret = MoneyUtil.hao2yuanStr(realHao);
//		String ret2 = MoneyUtil.fen2yuanStr(realFen);//实际发的奖励
//		System.out.println(String.format("danHao=%s danFen=%s,realHao=%s,realFen=%s,hy=%s,fy=%s",
//		        danHao,danFen,realHao,realFen,ret,ret2));
		
		//
	    String ret1=changeMoney(1L, MoneyUtil.HF_TIMES);
	    System.out.println(ret1);
	}
}
