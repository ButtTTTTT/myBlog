package top.lhit.myBlog.common.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: xfg
 * @description: 通用工具包
 * @create: 2019-12-13 15:55
 */
public class CommonUtils {
    public static Pattern patternPhone = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
    public static Pattern patternInt = Pattern.compile("^[-\\+]?[\\d]*$");
    public static String uploadFilePath = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/upload/img/";

    public static String getUploadFilePath() {
        return uploadFilePath;
    }


    //获取sqlServeDate
    public static String getSqlServerDate() {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
    }


    // 验证是否为手机号
    public static boolean isPhone(String mobiles) {
        Matcher matcher = patternPhone.matcher(mobiles);
        return matcher.matches();
    }

    /**
     * 判断是否为常见图片格式
     *
     * @param suffix
     * @return
     */
    public static boolean isImageSuffix(String suffix) {
        if (StringUtils.isEmpty(suffix)) {
            return false;
        }
        String[] imgSuffix = {".BMP", ".GIF", ".JPEG", ".PNG", ".ICO", ".JPG"};
        if (!ArrayUtil.contains(imgSuffix, suffix.toUpperCase())) {
            return false;
        }
        return true;
    }

    /**
     * 判断User-Agent 是不是来自于手机
     *
     * @return
     */
    public static boolean checkAgentIsMobile(HttpServletRequest servletRequest) {
        String ua = servletRequest.getHeader("User-Agent");
        final String[] agent = {"Android", "iPhone", "iPod", "NokiaN9", "Windows Phone", "MQQBrowser"};
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
                for (String item : agent) {
                    if (ua.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 判断图片是否为指定像素
     */
    public static boolean isImgPixel(int width, int height, File f) {
        boolean isImgOk = false;
        if (!isImage(f)) {
            return isImgOk;
        }
        try {
            BufferedImage img = ImageIO.read(f);
            Raster ra = img.getData();
            Rectangle rect = ra.getBounds();
            if (rect.height != height) {
                return isImgOk;
            }
            if (rect.width != width) {
                return isImgOk;
            }
            isImgOk = true;
        } catch (Exception e) {
            isImgOk = false;
        }
        return isImgOk;
    }

    // 判断是否为图
    public static boolean isImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null || StringUtils.isEmpty(file.getName())) {
                return false;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


    //判断是否为int类型，如果是，就返回int
    public static Integer getInteger(String str) {
        if (patternInt.matcher(str).matches()) {
            return Integer.parseInt(str);
        }
        return 1;

    }


    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }


    /**
     * 获取图形验证码
     *
     * @return
     */
    public static CircleCaptcha getCaptcha(HttpServletRequest request) {
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(80, 40, 4, 0);
        String circleCaptchaCode = captcha.getCode();
        request.getSession().setAttribute("circleCaptchaCode", circleCaptchaCode);
        return captcha;
    }

    /**
     * 判断字符串是否为URL
     *
     * @param urls 用户头像key
     * @return true:是URL、false:不是URL
     */
    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    /**
     * 文件名后缀前添加一个时间戳
     */
    public static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index) + "_" + IdUtil.simpleUUID() + fileName.substring(index);
        return fileName;
    }

    /**
     * 获取当前项目路径
     */
    public static String getClasspath(){
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) {
            path = new File("");
        }

        return path.getAbsolutePath();
    }

    /**
     * 获取当前上传文件路径
     */
    public static String getUploadPath() {
        File upload = new File(getClasspath(), "static" + uploadFilePath);
        if (!upload.exists()) {
            upload.mkdirs();
        }
        return upload.getAbsolutePath();
    }

    /**
     * 隐藏字符串中间字符，只显示首位字符
     * @param str
     * @return
     */
    public static String getHideMiddleStr(String str){
        if(StrUtil.isBlank(str)){
            return null;
        }
        char[] chars = str.toCharArray();
        if(chars.length<=1){
            return chars[0]+"**";
        }
        return chars[0]+"**"+chars[chars.length-1];
    }

}
