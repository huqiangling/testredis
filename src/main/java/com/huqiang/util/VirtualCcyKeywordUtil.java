package com.huqiang.util;

import com.huqiang.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***************************************************************************************
 *功能介绍：虚拟关键字过滤
 *@date 2021/8/815:36
 ***************************************************************************************/
public class VirtualCcyKeywordUtil {

    private StringRedisTemplate myStringRedisTemplate;

    public StringRedisTemplate getMyStringRedisTemplate() {
        return myStringRedisTemplate;
    }

    public void setMyStringRedisTemplate(StringRedisTemplate myStringRedisTemplate) {
        this.myStringRedisTemplate = myStringRedisTemplate;
    }

    /**
     * 提供给客户端调用判断所给字符串是否包含敏感词
     */
    public boolean containKeywords(String additional) {
        //获取所有键值对
        Map preMap = myStringRedisTemplate.opsForHash().entries("VirtualCcyKeywordUtil");
        String[] add = additional.split("");
        for (int i = 0; i < add.length; i++) {
            if (ifexist(add, i, preMap)) {
                return true;
            }
        }
        return false;
    }

    private boolean ifexist(String[] add, int i, Map<String, Object> preMap) {

        while (i < add.length && preMap.get(add[i]) != null) {

            if ("1".equals(((Map<String, Object>) preMap.get(add[i])).get("isend"))) {
                return true;
            }

            preMap = (Map<String, Object>) preMap.get(add[i]);
            i++;

        }
        return false;
    }

    /**
     * 应用启动后敏感词缓存预加载
     */
    private void reload() {
        System.out.println("=========开始初始化预加载=========");

        Test.getdd();
        List<String> list = new ArrayList<String>();
//        list.add("abc");
        list.add("cd");
        list.add("ad");
        Map<String, Object> preMap = getPreMap(list);
        preMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        myStringRedisTemplate.delete("VirtualCcyKeywordUtil");
        System.out.println(myStringRedisTemplate.getValueSerializer().getClass().getName());
        myStringRedisTemplate.opsForHash().putAll("VirtualCcyKeywordUtil", preMap);
        System.out.println("=========预加载完成=========");
    }

    /**
     * 通过list构造map树，返回根map
     * @param list
     * @return
     */
    private Map<String, Object> getPreMap(List<String> list) {
        Map<String,Object> root = new HashMap<String,Object>();
        if(list == null || list.size() == 0){
            return root;
        }
        root.put("isend","0");
        for(int i=0;i<list.size();i++){
            String[] chars = list.get(i).split("");
            composePreMap(root,chars);
        }
        return root;
    }

    private void composePreMap(Map<String, Object> root, String[] chars) {
        for(int j=0;j<chars.length;j++ )     {
            if("1".equals(root.get("isend"))){
                break;
            }else if(root.get(chars[j])==null ){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("isend",j==chars.length-1?"1":"0");
                root.put(chars[j],map);
                root = map;
            }else{
                root = (Map<String, Object>) root.get(chars[j]);
                if(j==chars.length-1){
                    root.clear();
                    root.put("isend","1");
                }
            }
        }

    }
}
