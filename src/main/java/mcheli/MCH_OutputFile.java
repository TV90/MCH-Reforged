package mcheli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MCH_OutputFile {

   public File file = null;
   public PrintWriter pw = null;


   public boolean open(String path) {
      this.close();
      this.file = new File(path);

      try {
         this.pw = new PrintWriter(this.file);
         return true;
      } catch (FileNotFoundException var3) {
         return false;
      }
   }

   public boolean openUTF8(String path) {
      this.close();
      this.file = new File(path);

      try {
         this.pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file), "UTF-8"));
         return true;
      } catch (Exception var3) {
         var3.printStackTrace();
         return false;
      }
   }

   public void writeLine(String s) {
      if(this.pw != null && s != null) {
         this.pw.println(s);
      }

   }

   public void close() {
      if(this.pw != null) {
         this.pw.close();
      }

      this.pw = null;
   }
}
