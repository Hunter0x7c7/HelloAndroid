package com.hunter.helloandroid.module;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hunter.dialog.SimpleDialog;
import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.adb.RootCmd;
import com.hunter.helloandroid.module.add_view_anim.AddViewAnimActivity;
import com.hunter.helloandroid.module.beam_mvp.ui.BeamMvpLoginActivity;
import com.hunter.helloandroid.module.ble.BleActivity;
import com.hunter.helloandroid.module.coordinator.main.CoordinatorActivity;
import com.hunter.helloandroid.module.custom.CustomViewActivity;
import com.hunter.helloandroid.module.four.FourViewActivity;
import com.hunter.helloandroid.module.gradient.GradientActivity;
import com.hunter.helloandroid.module.light.LightPrintActivity;
import com.hunter.helloandroid.module.matrix.MatrixActivity;
import com.hunter.helloandroid.module.md5.EncryptActivity;
import com.hunter.helloandroid.module.multi.list.MultiListActivity;
import com.hunter.helloandroid.module.nest_item.NestItemActivity;
import com.hunter.helloandroid.module.operate_log.OperateLogActivity;
import com.hunter.helloandroid.module.phont_number.PhontActivity;
import com.hunter.helloandroid.module.rocket.RocketActivity;
import com.hunter.helloandroid.module.rotation.RotationActivity;
import com.hunter.helloandroid.module.rx_android.CheeseActivity;
import com.hunter.helloandroid.module.rx_android.RxAndroidTestActivity;
import com.hunter.helloandroid.module.scan.android_zxinglibrary.MainZxingActivity;
import com.hunter.helloandroid.module.sort.ContactActivity;
import com.hunter.helloandroid.module.swipe_refresh.SwipeRefreshActivity;
import com.hunter.helloandroid.module.update.UpdateActivity;
import com.hunter.helloandroid.module.view.ViewActivity;
import com.hunter.helloandroid.module.web_js.WebActivity;
import com.hunter.helloandroid.module.win_manager.WinManagerActivity;
import com.hunter.helloandroid.util.PermissionUtil;
import com.hunter.helloandroid.util.ToastUtil;
import com.hunter.helloandroid.viewgroup.CustomGroupActivity;
import com.squareup.timessquare.CalendarPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.DensityUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements Serializable, View.OnTouchListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_on_touch).setOnTouchListener(this);

        //EventBus事件总线，注册
        EventBus.getDefault().register(this);

        EventBus.getDefault().post(new MessageBean("Hello EventBus!"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void main(MessageBean msg) {
        System.out.println("....................MAIN...msg:" + msg);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void async(MessageBean msg) {
        System.out.println("....................ASYNC...msg:" + msg);
    }

    class MessageBean {
        public MessageBean() {
        }

        public MessageBean(String msg) {
            this.msg = msg;
        }

        public MessageBean(int id, String msg) {
            this.id = id;
            this.msg = msg;
        }

        private int id;
        private String msg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "MessageBean{" +
                    "id=" + id +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }


    public void onClickADB(View view) {
//        System.out.println(".............adb:" + clickADB("adb tcpip 5555"));

        //修改/dev/bus/usb/文件夹的权限，注意添加\n
//        String commend = "chmod 777 /dev/bus/usb/ -R \n";
        String commend = "adb tcpip 5555 \n";
        String result;
        if (RootCmd.haveRoot()) {
            result = RootCmd.execRootCmd(commend);
        } else {
            RootCmd.execRootCmdSilent(commend);
            result = "no root";
        }
        System.out.println(".............adb:" + result);
//        System.out.println(".............adb:" + getResult(ARGS));
//        System.out.println(".............adb:" + getResult(new String[]{"adb", "tcpip", "5555"}));
    }

    private final static String[] ARGS = {"ls", "-l"};

    public String getResult(String[] commend) {
        ShellExecute cmdexe = new ShellExecute();
        String result = "";
        try {
            result = cmdexe.execute(commend, "/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private class ShellExecute {
        /*
         * args[0] : shell 命令 如"ls" 或"ls -1";
         * args[1] : 命令执行路径 如"/" ;
         */
        public String execute(String[] cmmend, String directory) {
            StringBuilder result = new StringBuilder();
            try {
                ProcessBuilder builder = new ProcessBuilder(cmmend);
                if (directory != null)
                    builder.directory(new File(directory));
                builder.redirectErrorStream(true);
                Process process = builder.start();
                //得到命令执行后的结果
                InputStream is = process.getInputStream();
                byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    result.append(new String(buffer));
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }
    }

    public void onClickWebJs(View view) {
        startActivity(new Intent(this, WebActivity.class));
    }

    public void onClickUpdate(View view) {
        startActivity(new Intent(this, UpdateActivity.class));
    }

    public void onClickBle(View view) {
        startActivity(new Intent(this, BleActivity.class));
    }

    public void onClickLightPrint(View view) {
        startActivity(new Intent(this, LightPrintActivity.class));
    }

    public void onClickCustomView(View view) {
        startActivity(new Intent(this, CustomViewActivity.class));
    }

    public void onClickCoordinator(View view) {
        startActivity(new Intent(this, CoordinatorActivity.class));
    }

    public void onClickEncrypt(View view) {
        startActivity(new Intent(this, EncryptActivity.class));
    }

    public void onClickWinManager(View view) {
        startActivity(new Intent(this, WinManagerActivity.class));
    }

    public void onClickGradient(View view) {
        startActivity(new Intent(this, GradientActivity.class));
    }

    public void onClickMultiAsyncTask(View view) {
//        int what = 0, value = 0;
//        for (int i = 0; i < 3; i++) {
//            what++;
//            value++;
//            new LongAsyncTask().execute(new Paramer(what, value));
//        }

        startActivity(new Intent(this, MultiListActivity.class));
    }

    public void onClickView(View view) {
        startActivity(new Intent(this, ViewActivity.class));
    }

    public void onClickScan(View view) {
        startActivity(new Intent(this, MainZxingActivity.class));
    }

    public void onClickFourView(View view) {
        startActivity(new Intent(this, FourViewActivity.class));
    }

    public void onClickOperateLog(View view) {
        startActivity(new Intent(this, OperateLogActivity.class));
    }


    public void onClickRotation(View view) {
        startActivity(new Intent(this, RotationActivity.class));
    }

    public void onClickRxAndroid(View view) {
        startActivity(new Intent(this, CheeseActivity.class));
    }

    public void onClickRxAndroidTest(View view) {
        startActivity(new Intent(this, RxAndroidTestActivity.class));
    }

    public void onClickRocket(View view) {
        startActivity(new Intent(this, RocketActivity.class));
    }

    public void onClickBeamMvp(View view) {
        startActivity(new Intent(this, BeamMvpLoginActivity.class));
    }

    public void onClickViewGroup(View view) {
        startActivity(new Intent(this, AddViewAnimActivity.class));
    }

    public void onClickSwipeRefresh(View view) {
        startActivity(new Intent(this, SwipeRefreshActivity.class));
    }

    public void onClickNested(View view) {
        startActivity(new Intent(this, NestItemActivity.class));
    }

    public void onClickTimesSquare(View v) {
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -3);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);

        showCalendarInDialog("请选择开始时间", R.layout.dialog_customized);

        Calendar today = Calendar.getInstance();
        List<Date> dates = new ArrayList<>();
        today.add(Calendar.DATE, 3);
        dates.add(today.getTime());
        today.add(Calendar.DATE, 5);
        dates.add(today.getTime());

        dialogView.init(lastYear.getTime(), instance.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());


//        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
//        calendar.init(lastYear.getTime(), nextYear.getTime())
//                .inMode(CalendarPickerView.SelectionMode.SINGLE)
//                .withSelectedDate(new Date());
//
//        calendar.setCustomDayView(new DefaultDayViewAdapter());
//        Calendar today = Calendar.getInstance();
//         List<Date> dates = new ArrayList< >();
//        today.add(Calendar.DATE, 3);
//        dates.add(today.getTime());
//        today.add(Calendar.DATE, 5);
//        dates.add(today.getTime());
//        calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
//        calendar.init(lastYear.getTime(), nextYear.getTime())
//                .inMode(CalendarPickerView.SelectionMode.RANGE)
//                .withSelectedDates(dates);
    }

    private CalendarPickerView dialogView;

    private void showCalendarInDialog(String title, int layoutResId) {
        View inflate = View.inflate(this, layoutResId, null);
        dialogView = (CalendarPickerView) inflate.findViewById(R.id.calendar_view);
        dialogView.setLayoutParams(new LinearLayout.LayoutParams(-1, DensityUtil.dip2px(290)));

        final SimpleDialog dialog = new SimpleDialog(this).setView(inflate);
        final TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);

        DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialogView.fixDialogDimens();
            }
        };

        CalendarPickerView.OnDateSelectedListener selectedListener = new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                List<Date> selectedDates = dialogView.getSelectedDates();
                int size = selectedDates.size();
                if (size > 1) {

                    tv_title.setText("选择完成");

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                            List<Date> selectedDates = dialogView.getSelectedDates();
                            Date date0 = selectedDates.get(0);
                            Date dateSize = selectedDates.get(selectedDates.size() - 1);

                            String time0 = DateUtils.formatDateTime(MainActivity.this, date0.getTime(), 0);
                            String timeSize = DateUtils.formatDateTime(MainActivity.this, dateSize.getTime(), 0);
                            String str = time0 + "--" + timeSize;

                            ToastUtil.showPrompt(str);
                        }
                    };
                    new Handler().postDelayed(runnable, 800);
                } else if (size > 0) {

                    tv_title.setText("请选择结束时间");
                } else {
                    tv_title.setText("请选择开始时间");
                }
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        };

        dialog.setLayoutParams(-1, -2)
                .setTitleName(title)
                .setCanceledOnTouchOutside()
                .setAnimatorSet().show();
        dialog.setOnShowListener(showListener);
        dialogView.setOnDateSelectedListener(selectedListener);

    }

    public void onClickGetPhoneNumber(View v) {
        startActivity(new Intent(this, PhontActivity.class));
    }

    public void onClickMatrix(View v) {
        startActivity(new Intent(this, MatrixActivity.class));
    }

    public void onClickContacts(View v) {
        startActivity(new Intent(this, ContactActivity.class));
    }

    public static final int PERMISSIONS = 12;

    public void onClickContacts2(View v) {

        String opstrReadContacts = "android:read_contacts";
        boolean check = appOpsManagerCheck(opstrReadContacts, 0);
        readContacts(check, 0);


//        startGetContacts();
    }

    /**
     * 判断是否已经授权该权限，返回未授权的权限列表
     *
     * @param permission 需要授权的权限列表
     * @return 返回未授权的权限列表
     */
    private String[] permissionDenied(String... permission) {
        List<String> list = new ArrayList<>();
        for (String p : permission) {
            int checkPermission = ContextCompat.checkSelfPermission(this, p);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                list.add(p);
            }
        }
        int size = list.size();
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = list.get(i);
        }
        return strings;
    }


    private void startGetContacts() {
        Intent intentPhone = new Intent(Intent.ACTION_PICK);
        intentPhone.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intentPhone, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS:

                returnPermissions(permissions, grantResults);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void returnPermissions(String[] permissions, int[] grantResults) {
        StringBuilder sb = new StringBuilder();
        boolean isDenied = false;
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            int grantResult = grantResults[i];
            String permission = permissions[i];

            if (TextUtils.isEmpty(permission)) {
                continue;
            }

            if (grantResult == PackageManager.PERMISSION_GRANTED) {

                String opstrReadContacts = "android:read_contacts";
                boolean check = appOpsManagerCheck(opstrReadContacts, 1);
                readContacts(check, 1);

            } else {
                isDenied = true;
                String permissionName = PermissionUtil.getPermissionName(permission);
                if (TextUtils.isEmpty(sb)) {
                    sb.append(permissionName);
                } else {
                    if (!sb.toString().contains(permissionName)) {
                        if (sb.length() > 0) {
                            sb.append("、");
                        }
                        sb.append(permissionName);
                    }
                }

            }
        }
        if (isDenied) {
            toOpenPermissionDialog(sb);
        }
    }

    private void readContacts(boolean check, int i) {
        System.out.println("...i:" + i);
        if (check) {
            startGetContacts();
        } else {
            // 判断版本大于等于6.0时先去请求权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = {Manifest.permission.READ_CONTACTS};
                String[] denied = permissionDenied(permissions);
                if (denied.length > 0) {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSIONS);
//                return;
                }
            }
        }
    }

    private boolean appOpsManagerCheck(String opstr, int i) {
        System.out.println("..appOpsManagerCheck.i:" + i);
        boolean isAllowed = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//            String opstrReadContacts = AppOpsManager.OPSTR_READ_CONTACTS;
//            String opstrReadContacts = "android:read_contacts";
            int checkOp = appOpsManager.checkOp(opstr, android.os.Process.myUid(), getPackageName());
            switch (checkOp) {
                case AppOpsManager.MODE_ALLOWED:
                    System.out.println("..............AppOpsManager.MODE_ALLOWED ：有权限:");
                    break;
                default:
                    isAllowed = false;
                    switch (checkOp) {
                        case AppOpsManager.MODE_IGNORED:
                            System.out.println("..............AppOpsManager.MODE_IGNORED：被禁止了");
                            break;
                        case AppOpsManager.MODE_DEFAULT:
                            System.out.println("......AppOpsManager.MODE_DEFAULT........ ");
                            break;
                        case AppOpsManager.MODE_ERRORED:
                            System.out.println(".......AppOpsManager.MODE_ERRORED：出错了....... ");
                            break;
                        case 4:
                            System.out.println("......AppOpsManager.OTHER：权限需要询问........ ");
                            break;
                    }
                    break;

            }
        }
        return isAllowed;
    }

    private void toOpenPermissionDialog(CharSequence sb) {
        String showContent = String.format("您没有授权“%s”权限。请到“设置-应用信息-权限管理”中打开", sb);
        System.out.println(showContent);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        };
        new SimpleDialog(this)
                .setTitleName(showContent)
                .setPositiveButton("去打开", onClickListener)
                .setNegativeButton()
                .setAnimatorSet()
                .setCanceledOnTouchOutside().show();
    }

    public void onClickCustomGroup(View v) {
        startActivity(new Intent(this, CustomGroupActivity.class));
    }


    private void setSimulateClick(View view) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int xoff = location[0];
        int yoff = location[1];

        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, xoff, yoff, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, xoff, yoff, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    private String clickADB(String cmd) {
        BufferedReader reader = null;
        String content = "";
        try {
            //("ps -P|grep bg")执行失败，PC端adb shell ps -P|grep bg执行成功
            //Process process = Runtime.getRuntime().exec("ps -P|grep tv");
            //-P 显示程序调度状态，通常是bg或fg，获取失败返回un和er
            // Process process = Runtime.getRuntime().exec("ps -P");
            //打印进程信息，不过滤任何条件
            Process process = Runtime.getRuntime().exec(cmd);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private int mAction;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getAction();
        if (mAction != action) {
            mAction = action;
            System.out.println("...................action:" + action);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                v.setPressed(true);
                System.out.println(".....ACTION_DOWN....... ");
                break;
            case MotionEvent.ACTION_MOVE:

                int top = v.getTop();
                int bottom = v.getBottom();
                int width = v.getWidth();
                int height = v.getHeight();
                float eventX = event.getX();
                float eventY = event.getY();
                float rawX = event.getRawX();
                float rawY = event.getRawY();

//                System.out.println(".....top:" + top + " bottom:" + bottom + " width:" + width + " height:" + height);
//                System.out.println(".....eventX:" + eventX + " eventY:" + eventY + " rawX:" + rawX + " rawY:" + rawY);

                if (!(eventY <= bottom && eventY > top)) {
                    v.setPressed(false);
                    System.out.println(".....ACTION_MOVE....up... ");
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                v.setPressed(false);

                System.out.println(".....ACTION_CANCEL....... ");
                break;

            case MotionEvent.ACTION_UP:
                v.setPressed(false);

                System.out.println(".....ACTION_UP....... ");
                break;
            default:

                System.out.println(".....default....... ");
                break;
        }
        return true;
    }

    //获得返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    Cursor cursor = managedQuery(uri, null, null, null, null);
                    cursor.moveToFirst();

                    int count = cursor.getCount();
                    System.out.println("...........count:" + count);
                    if (count <= 0) {
                        return;
                    }

                    int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactid = cursor.getString(columnIndex);
                    //得到ContentResolver
                    ContentResolver contentResolver = getContentResolver();
                    Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactid;
                    Cursor phone = contentResolver.query(contentUri, null, selection, null, null);

                    String displayName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
                    String number = ContactsContract.CommonDataKinds.Phone.NUMBER;
                    List<Map<String, String>> datalistView = new ArrayList<>();
                    while (phone != null && phone.moveToNext()) {
                        //联系人
                        String phoneName = phone.getString(phone.getColumnIndex(displayName));
                        //手机号码
                        String phoneNumber = phone.getString(phone.getColumnIndex(number));
                        //格式化手机号
                        phoneNumber = phoneNumber.replace("-", "");
                        phoneNumber = phoneNumber.replace(" ", "");
                        //将用户名和号码放入Map集合中
                        Map<String, String> map = new HashMap<>();
                        map.put("phoneName", phoneName);
                        map.put("phoneNumber", phoneNumber);
                        datalistView.add(map);
                    }
                    if (phone != null) {
                        phone.close();
                    }
                    ToastUtil.showPrompt(datalistView.toString());
                }
                break;
        }

    }

}
