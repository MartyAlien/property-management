package com.program.check;

public class StaticPeram {
	/**
	 * 手机验证部分配置
	 */
	// 设置超时时间-可自行调整
	final static String defaultConnectTimeout  = "sun.net.client.defaultConnectTimeout";
	final static String defaultReadTimeout = "sun.net.client.defaultReadTimeout";
	final static String Timeout = "10000";
	// 初始化ascClient需要的几个参数
	final static String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	final static String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	// 替换成你的AK (产品密)
	final static String accessKeyId = "LTAI4G7ioUh7GdP5yf5Apmh5";// 你的accessKeyId,填你自己的 上文配置所得  自行配置
	final static String accessKeySecret = "9IOOdebw0ug9qq8arbM4CazZWE1D4u";// 你的accessKeySecret,填你自己的 上文配置所得 自行配置
	// 必填:短信签名-可在短信控制台中找到
	final static String SignName = "物业收费管理系统";// 阿里云配置你自己的短信签名填入
	// 必填:短信模板-可在短信控制台中找到
	// 0 注册模板  1 修改密码模板 2 缴费验证模板 3 资料修改模板
	final static String TemplateCode[] = {"SMS_196618708","SMS_196653767","SMS_198671891","SMS_198691783"}; // 阿里云配置你自己的短信模板填入
}
