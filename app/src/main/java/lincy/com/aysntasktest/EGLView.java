package lincy.com.aysntasktest;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EGLView extends GLSurfaceView {

    public EGLView(Context context){
        super(context);
        init();
    }

    private void init(){
        setEGLContextClientVersion(2);
        EGLRender render = new EGLRender();
        setRenderer(render);
        setRenderMode(RENDERMODE_WHEN_DIRTY);//设置渲染模式为主动渲染
    }

    float triangleCoords[] = {
            0.5f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };
    float color[] = {1.0f,1.0f,1.0f,1.0f};

   private class  EGLRender implements Renderer{

       Triangle triangle;
        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            GLES30.glClearColor(0.5f,0.5f,0.5f,0.5f);
            //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
            //byteBuffer.order(ByteOrder.nativeOrder());
            triangle = new Triangle();
        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int i, int i1) {
            GLES20.glViewport(0,0,i,i1);
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            triangle.draw();
        }

    }

}
