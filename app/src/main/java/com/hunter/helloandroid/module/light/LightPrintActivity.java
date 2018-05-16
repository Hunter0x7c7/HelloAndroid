package com.hunter.helloandroid.module.light;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作    者： 黄自航
 * 创建日期： 2018/4/19 20:47
 */
public class LightPrintActivity extends AppCompatActivity {

    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_result)
    TextView mResult;
    @BindView(R.id.et_input_name)
    EditText mInputName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_print);

        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_transition)
    void onClickTransition() {
        mResult.setText("");
        mProgressBar.setVisibility(View.VISIBLE);

        String fileName = mInputName.getText().toString();
        if (!TextUtils.isEmpty(fileName)) {
            test1(fileName);
        } else {
            ToastUtil.showPrompt("请输入文件名！");
        }


    }

    private void test1(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    InputStream is = getResources().getAssets().open("1.jpg");
//                    InputStream is = getResources().getAssets().open("2.jpg");
//                    InputStream is = getResources().getAssets().open("hello_world.jpg");
                    InputStream is = getResources().getAssets().open("hei_ha.png");
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    List<int[]> target = getPixelListFromBitmap(bitmap);
                    System.out.println("..........getPixelListFromBitmap:" + target);

                    List<List<int[]>> datas = new ArrayList<>();
                    for (int[] c : target) {
                        List<int[]> datass = new ArrayList<>();
                        for (int cc : c) {
                            int[] ints = {Color.red(cc), Color.green(cc), Color.blue(cc)};
                            datass.add(ints);
                        }
                        datas.add(datass);
                    }

                    final String toJson = new Gson().toJson(datas);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mResult.setText(toJson);
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                    //把中括号替换成需要的格式
                    String replace = toJson.replace("[", "{").replace("]", "}");

//                    String fileName = "ColorPixel.txt";
                    String fileType = ".txt";
                    File file = getFileFromBytes(replace.getBytes(), getSDPath() + "/" + fileName + fileType);
                    System.out.println("............file:" + file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void test2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getResources().getAssets().open("hello_world.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    List<int[]> convertTo = getPixelListFromBitmap(bitmap);
                    System.out.println("..........getPixelListFromBitmap:" + convertTo);

                    PixelBean result = new PixelBean();
                    List<RowBean> datas = new ArrayList<>();
                    result.setName("Hello World!");
                    result.setType("color-array");
                    result.setWidth(bitmap.getWidth());
                    result.setHeight(bitmap.getHeight());
                    result.setDatas(datas);

                    for (int[] c : convertTo) {
                        RowBean rowBean = new RowBean();
                        rowBean.setType("row");
//                        List<ColorBean> colorBeen = new ArrayList<>();
                        for (int cc : c) {
//                            ColorBean colorBean = new ColorBean();
//                            colorBean.setRgb(cc);
//                            colorBean.setR(Color.red(cc));
//                            colorBean.setG(Color.green(cc));
//                            colorBean.setB(Color.blue(cc));
//                            colorBeen.add(colorBean);
//                            rowBean.setDatas(colorBeen);
                            rowBean.setDatas(new int[]{Color.red(cc), Color.green(cc), Color.blue(cc)});
                        }
                        datas.add(rowBean);
                    }

                    final String toJson = new Gson().toJson(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mResult.setText(toJson);
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });


                    String fileName = "ColorPixel.txt";
                    File file = getFileFromBytes(toJson.getBytes(), getSDPath() + "/" + fileName);
                    System.out.println("............file:" + file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public int red(int color) {
        return (color >> 16) & 0xFF;
    }

    public int green(int color) {
        return (color >> 8) & 0xFF;
    }

    public int blue(int color) {
        return color & 0xFF;
    }


    public class PixelBean {
        private String name, type;
        private int width, height;
        private List<RowBean> datas;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public List<RowBean> getDatas() {
            return datas;
        }

        public void setDatas(List<RowBean> datas) {
            this.datas = datas;
        }
    }

    public class RowBean {
        private String type;
        private int[] datas;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int[] getDatas() {
            return datas;
        }

        public void setDatas(int[] datas) {
            this.datas = datas;
        }
    }

    public class RowBean2 {
        private String type;
        private List<ColorBean> datas;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ColorBean> getDatas() {
            return datas;
        }

        public void setDatas(List<ColorBean> datas) {
            this.datas = datas;
        }
    }

    public class ColorBean {
        private int rgb, r, g, b;

        public int getRgb() {
            return rgb;
        }

        public void setRgb(int rgb) {
            this.rgb = rgb;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }

    public String getSDPath() {
        String result = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            result = sdDir.toString();
        }
        return result;
    }

    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputName) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputName);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将Bitmap转换为像素集合
     */
    public static List<int[]> getPixelListFromBitmap(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
//        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
//        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        int[] heightPixels;
        List<int[]> widthPixels = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            heightPixels = new int[height];
            bmp.getPixels(heightPixels, 0, 1, i, 0, 1, height);
            widthPixels.add(heightPixels);
        }
        return widthPixels;
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @return 返回转换好的位图
     */
    public static int[] convertTo2(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        return pixels;
    }

}
