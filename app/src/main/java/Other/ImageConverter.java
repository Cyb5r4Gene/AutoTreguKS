package Other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageConverter {

    private static final String TAG = "ImageConverter";
    public static Bitmap imgToBitmap(String imgUrl){
        File file = new File(imgUrl);
        FileInputStream fis = null;
        Bitmap bitmap = null;

        try{
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "imgToBitmap: FajlliNukUGjet "+e.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException e){
                //Log.e(TAG, "imgToBitmap: FajlliNukUGjet "+ e.getMessage());
            }
        }
        return bitmap;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, int quality){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }
}
