package z.pub.util;
import  java.io.File;       
import  java.io.FileFilter;       
      
/**
 * 文件夹文件过滤器
 * @author EVECOM-PC
 *
 */
public class  FileExtensionFileFilter  implements  FileFilter {       
      
      private  String extension;       
      
      public  FileExtensionFileFilter(String extension) {       
           this .extension = extension;       
     }       
      
      /*     
      * 是否是文件且是否包含文件名  extension
      */       
      public   boolean  accept(File file) {       
           // Lowercase the filename for easier comparison       
          String lCaseFilename = file.getName().toLowerCase();       
      
           return  (file.isFile() &&       
                       (lCaseFilename.indexOf(extension) >  0 )) ?  true : false ;       
     }       
}  