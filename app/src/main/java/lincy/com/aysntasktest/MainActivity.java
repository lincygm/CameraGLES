package lincy.com.aysntasktest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    Button button;
    Back back;
    EGLView glSurfaceView;
    SparseArray<Bitmap> sparseArray = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        glSurfaceView = new EGLView(this);
        setContentView(glSurfaceView);


        Bitmap bitmap =  BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background,null);
        sparseArray.put(0,bitmap);
        sparseArray.put(1,bitmap);
        sparseArray.put(2,bitmap);
        sparseArray.put(3,bitmap);
        sparseArray.put(4,bitmap);
        sparseArray.put(5,bitmap);
        sparseArray.put(6,bitmap);
        sparseArray.put(7,bitmap);
        sparseArray.put(8,bitmap);



       // glSurfaceView.setEGLContextClientVersion(2);
       // glSurfaceView.setRenderer(new EGLView(this));
//        button = (Button)this.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(view.getId() == R.id.button){
//                    if(back != null){
//                        back.cancel(true);
//                        back = null;
//                    }
//                    back = new  Back();
//                    back.execute();
//                }
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    class openGLRender implements GLSurfaceView.Renderer{

        @Override
        public void onSurfaceChanged(GL10 gl10, int i, int i1) {
            GLES20.glViewport(0,0,i,i1);
        }

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            //清空屏幕
            GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        }

    }


    SurfaceTexture mSurfaceTexture;
    HandlerThread mCameraThread;
    Handler mCameraHandler;
    Context mActivity;
    CameraDevice mCameraDevice;

    public void startCameraThread(){

        mCameraThread = new HandlerThread("CamearaThread");
        mCameraThread.start();
        mCameraHandler = new Handler(mCameraThread.getLooper());
    }

    public String setupCamera(int width,int height){
        CameraManager cameraManager = (CameraManager)mActivity.getSystemService(Context.CAMERA_SERVICE);
        try{
            for(String id:cameraManager.getCameraIdList()){
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                if(characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                //getOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {

        }
    };

    class Back extends AsyncTask<Void,Integer,Boolean>{

        boolean is = false;
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            is = true;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            is = false;
            Log.d("doInBackground","cancel");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            while(is){
                for(int i=0;i<20000;i++){
                    Log.d("doInBackground",Thread.currentThread()+ "====== "+i);
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){
                    }
                    if(isCancelled()){
                        Log.d("doInBackground","cancel2");
                        break;
                    }
                }
                break;
            }
            return true;
        }
    }

}
