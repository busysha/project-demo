package com.project.common.util;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.project.common.annotation.ColumnMap;
import com.project.common.common.Constants;
import com.project.common.dto.QueryMapDto;

public class ToolUtil {

    private static final Logger logger = Logger.getLogger(ToolUtil.class);

    /**
     * 判断是否是整数
     */
    public static boolean isNumberic(String str){
        Pattern p = Pattern.compile("[\\d]*");
        return p.matcher(str).matches();
    }

    /**
     * 手机号码正则
     */
    public static boolean isPhoneNum(String phone){
        Pattern p = Pattern.compile("^1[3|5|7|8]\\d{9}");
        return p.matcher(phone).matches();
    }

    /**
     * list去重
     * @param list
     * @return
     */
    public static List<String> uniqueList(List<String> list){
        if(list.size() == 1 || list.size() ==0)return list;
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public static boolean isIdCard(String idCard){
        Pattern p = Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
        return p.matcher(idCard).matches();
    }


    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double lng_anchor, double lat_anchor, double lng_member, double lat_member)
    {
        double radLat1 = rad(lat_anchor);
        double radLat2 = rad(lat_member);
        double a = radLat1 - radLat2;
        double b = rad(lng_anchor) - rad(lng_member);

        double s = 2 * Math.sin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));

        double EARTH_RADIUS = 6378.137;
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    /**
     * 从给定范围内随机N个不重复整数
     */
    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if (n > (max - min + 1) || max < min) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 调用Math.random()方法  
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中  
        }
        int setSize = set.size();
        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
        if (setSize < n) {
            randomSet(min, max, n - setSize, set);// 递归
        }
    }

    /**
     * 返回指定位数的验证码
     */
    public static String randomSet(int length) {
        String code = "";
        for(int i=0;i<length;i++){
            code +=+ (int) (Math.random() * 10);
        }
        return code;
    }

    /**
     * 获取验证码
     * @param code /
     * @param content /
     * @param phone /
     * @return
     */
    public static boolean sendSms(String code,String content,String phone){

        String url = "http://q.hl95.com:8061";
        String userName = "lzzx";
        String password = "lzzx123";
        String epId = "120976";

        String requestUrl = null;
        try {
            requestUrl = url
                    + "/?username=" + userName
                    + "&password=" + XlmEncryption.MD5(password)
                    + "&message=" + URLEncoder.encode(content,"GB2312")
                    + "&phone=" + phone
                    + "&epid=" + epId;
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        return HttpClientUtil.get(requestUrl).equals("00");
    }

    public static String buildToken(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static boolean validateAuthCodeAction(String action){
        return Constants.AUTH_ACTION_LIST.contains(action);
    }

    /**
     * T & V 的字段名字以及set get 必须相同
     * entity list convert to dto
     * @param entityLists /
     * @param targetClazz /
     * @param <T> /
     * @param <V> /
     * @return list
     */
    public static <T,V> List<T> listEntityToDto(List<V> entityLists , Class<T> targetClazz){
        List<T> list = new ArrayList<T>();
        if(entityLists.isEmpty()) return list;
        //获取dto的field
        try {
            Method[] methods = targetClazz.getMethods();
            T obj = null;
            for (V v : entityLists) {
                obj = targetClazz.newInstance();
                runConvert(v,targetClazz,methods,obj);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            logger.error(Constants.LOGGER_HEAD + e);
        }
        return list;
    }


    /**
     * 单个entity to dto
     * @param entity /
     * @param targetClazz /
     * @param <T> /
     * @param <V> /
     * @return
     */
    public static <T,V> T entityToDto(V entity , Class<T> targetClazz){
        if(entity == null) return null;
        //获取dto的field
        try {
            T obj = targetClazz.newInstance();
            Method[] fields = targetClazz.getMethods();
            runConvert(entity,targetClazz,fields,obj);
            return obj;
        } catch (Exception e) {
            logger.error(Constants.LOGGER_HEAD + e);
        }
        return null;
    }

    /**
     * 将搜索dto直接转化为可以搜索的sql语句
     * @return
     */
    public static <T> List<QueryMapDto> buildSearchDto(T bean){
        List<QueryMapDto> list = new ArrayList<QueryMapDto>();
        Class<?> targetClazz = bean.getClass();
        try {
            Method[] methods = targetClazz.getMethods();
            for (Method method : methods) {
                if(method.getName().startsWith("set")){
                    String fieldName = method.getName().substring(3, method.getName().length());
                    Method getMethod = targetClazz.getMethod("get" + fieldName);
                    ColumnMap ann = getMethod.getAnnotation(ColumnMap.class);
                    String mapField = null;
                    //注解忽略则跳过
                    if(ann != null && ann.value().equals("ignore"))continue;
                    //注解有映射就使用映射值
                    mapField = ann != null && !ann.value().equals("") ? ann.value() : ToolUtil.toLowerCase(fieldName);
                    QueryMapDto queryMapDto = new QueryMapDto();
                    Object v = getMethod.invoke(bean);
                    if(v != null){
                        queryMapDto.setKey(mapField);
                        if(fieldName.equals("BeginTime")){
                            queryMapDto.setOperate(">=");
                        }else if(fieldName.equals("EndTime")){
                            if(String.valueOf(v).equals("0"))v = System.currentTimeMillis();
                            queryMapDto.setOperate("<");
                        }else if (fieldName.contains("Like")){
                            queryMapDto.setOperate("like");
                        }else{
                            queryMapDto.setOperate("=");
                        }
                        queryMapDto.setValue(v);
                        list.add(queryMapDto);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(Constants.LOGGER_HEAD + e);
        }
        return list.isEmpty() ? null : list;
    }


    /**
     * 首字母大写
     * @param name
     * @return string
     */
    public static String toUpperCase(String name){
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    /**
     * 首字母小写
     * @param name
     * @return string
     */
    public static String toLowerCase(String name){
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    /**
     * 返回 页码 页码不从0开始时不需要-1
     * @param resCount /
     * @param pageSize /
     * @return
     */
    public static int pageConvert(int resCount,int pageSize){
        int pageNum = (int) Math.ceil(resCount / (double) pageSize);
        return pageNum - 1 < 0 ? 0 : pageNum - 1;
    }


    /**
     * 转换string to integer
     * @param str /
     * @return
     */
    public static int parseStringToInteger(String str){
        if(StringUtils.isEmpty(str)) return -1;
        return Integer.parseInt(str);
    }

    /**
     * 计算等级 经验值为等比数列 100 400 1600 6400 ...
     * @param an ===> exp 等级从0开始 0-1 100 1-2 400
     * @return
     */
    public static int convertLvByExp(int an){
        int a1 = 100;
        int q  = 4;
        if(an < 100)return 0;
        return (int) (Math.log(an / a1) / Math.log(q))  + 1;
    }

    /**
     * 计算经验值 经验值为等比数列 100 400 1600 6400 ...
     * @param lv ===> exp
     * @return
     */
    public static int convertLvByLv(int lv){
        int a1 = 100;
        int q  = 4;
        if(lv <= 0)return 0;
        return (int) (a1 * Math.pow(q,lv - 1));
    }

    /**
     * 返回指定的切割
     * @param length /
     * @param content /
     * @param begin /
     * @param subLength /
     * @return /
     */
    public static String formatStringByLength(int length,String content,int begin,int subLength,String delimiter){
        return content.length() > length ? content.substring(begin,subLength) + delimiter : content;
    }


    //=========private function==========================//
    private static Class<?> classTypeFactory(String type){
        if(type.contains("int")){
            return int.class;
        }else if(type.contains("String")){
            return String.class;
        }else if(type.contains("long")){
            return long.class;
        }
        return null;
    }

    private static <T,V> void runConvert(V entity,Class<T> targetClazz,Method[] s,Object obj){
        try {
            for (Method method : s) {
                if(method.getName().startsWith("set")){
                    String fieldName = method.getName().substring(3, method.getName().length());
                    String targetSetFunc = "set" + fieldName;
                    String entityGetFunc = "get" + fieldName;
                    try {
                        Method getMethod = entity.getClass().getMethod(entityGetFunc);
                        Class<?> funcType= classTypeFactory(getMethod.getReturnType().getName());
                        Method setMethod = targetClazz.getMethod(targetSetFunc,funcType);
                        setMethod.invoke(obj,getMethod.invoke(entity));
                    }catch (Exception e) {
                        logger.error(e);
                    }
                }
            }
        }catch(Exception e){
            logger.error(e);
        }
    }
}
