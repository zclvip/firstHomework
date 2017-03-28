package com.zcl.work.D_FactoryMethodPattern;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public class ClassUtils {

    /**
     * ��һ���ӿڣ���������ӿڵ�����ʵ����
     * @return
     */
    public static List<Class> getAllClassByInterface(Class c){
        List<Class> returnClassList = new ArrayList<Class>();
        if(c.isInterface()){
            String packageName = c.getPackage().getName();//��õ�ǰ����
            try {
                List<Class> allClass = getClasses(packageName);//��õ�ǰ�����Լ��Ӱ��µ�������
                for(int i=0;i<allClass.size();i++){
                    if(c.isAssignableFrom(allClass.get(i))){//�ж��ǲ���һ���ӿ�
                        if(!c.equals(allClass.get(i))){//�����ӽ�ȥ
                            returnClassList.add(allClass.get(i));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return returnClassList;
    }

    /**
     * ��һ�����в��ҳ����е��࣬��jar���в��ܲ���
     * @param packageName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static List<Class> getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader  = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()){
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for(File directory : dirs){
            classes.addAll(findClasses(directory,packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory,String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if(!directory.exists()){
            return classes;
        }
        File[] files  = directory.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file,packageName+"."+file.getName()));
            }else if (file.getName().endsWith(".class")){
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
