package com.humorousz.commonutils.file;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangzhiquan on 2017/8/14.
 */

public class FileUtils {

    private static final String PATH_SD_CARD = Environment.getExternalStorageDirectory().toString();

    private static final String MainDir = "humorous";

    private static final String Separator = File.separator;

    private static FileUtils mInstance;

    private FileUtils(){}

    public static FileUtils getInstance(){
        if(mInstance == null){
            synchronized (FileUtils.class){
                if(mInstance == null){
                    mInstance = new FileUtils();
                }
            }
        }

        return mInstance;
    }


    public synchronized String getPath(String fileName){
        File file = new File(fileName);
        if (file.exists() && file.isDirectory()) {
            return file.getPath();
        } else {
            file.mkdirs();
            return file.getPath();
        }
    }

    public synchronized String getMainDirPath() {
        return getPath(PATH_SD_CARD + Separator + MainDir);
    }



    public synchronized String getZipPath(){
        return getPath(getMainDirPath() + Separator + "ZipFile");
    }


    public synchronized void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(file.exists())
            return;
        file.createNewFile();
    }

    public synchronized void deleteFolder(String path){
        delete(new File(path),true);
    }

    public synchronized void deleteFile(String path){
        delete(new File(path),false);
    }

    public synchronized void delete(File file,boolean deleteIfFolder){
        if(!file.exists())
            return;
        if(!deleteIfFolder && file.isDirectory())
            return;
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFiles = file.listFiles();
            if(childFiles == null || childFiles.length == 0){
                file.delete();
                return;
            }

            for(File child : childFiles){
                delete(child,deleteIfFolder);
            }
            file.delete();
        }
    }
}
