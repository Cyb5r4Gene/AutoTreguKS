package rks.youngdevelopers.autotreguks;

import android.os.Environment;

public class FilePaths {

    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR+"/Pictures";
    public String CAMERA = ROOT_DIR+"/DCIM/camera";
}
