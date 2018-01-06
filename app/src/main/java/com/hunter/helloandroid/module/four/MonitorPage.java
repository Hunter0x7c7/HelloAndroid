package com.hunter.helloandroid.module.four;

//import com.nongtt.farmerlookup.R;
//import com.nongtt.farmerlookup.base.BaseApplication;
//import com.nongtt.farmerlookup.bean.FieldBean;
//import com.nongtt.farmerlookup.bean.SerialChanelBean;
//import com.nongtt.farmerlookup.global.ServerConfigInfo;
//import com.nongtt.farmerlookup.interfaces.OnItemClickListener;
//import com.nongtt.farmerlookup.interfaces.OnPermissionListener;
//import com.nongtt.farmerlookup.module.base.BasePager;
//import com.nongtt.farmerlookup.module.monitor.PlayBackListActivity;
//import com.nongtt.farmerlookup.module.monitor.adapter.MonitorAdapter;
//import com.nongtt.farmerlookup.module.monitor.realplay.DataManager;
//import com.nongtt.farmerlookup.module.monitor.realplay.RemoteListContant;
//import com.nongtt.farmerlookup.module.monitor.realplay.ScreenOrientationHelper;
//import com.nongtt.farmerlookup.module.monitor.realplay.VerifyCodeInput;
//import com.nongtt.farmerlookup.module.monitor.realplay.util.AudioPlayUtil;
//import com.nongtt.farmerlookup.module.monitor.realplay.util.EZUtils;
//import com.nongtt.farmerlookup.module.monitor.realplay.view.LoadingView;
//import com.nongtt.farmerlookup.module.monitor.view.PtzPanelLand;
//import com.nongtt.farmerlookup.util.AnimatorSetUtil;
//import com.nongtt.farmerlookup.util.ClickUtil;
//import com.nongtt.farmerlookup.util.CommandUtil;
//import com.nongtt.farmerlookup.util.DateUtil;
//import com.nongtt.farmerlookup.util.DensityUtil;
//import com.nongtt.farmerlookup.util.DirectoryUtil;
//import com.nongtt.farmerlookup.util.ImageUtil;
//import com.nongtt.farmerlookup.util.JsonUtil;
//import com.nongtt.farmerlookup.util.ListUtil;
//import com.nongtt.farmerlookup.util.PermissionUtil;
//import com.nongtt.farmerlookup.util.PostRequestUtil;
//import com.nongtt.farmerlookup.util.PrefUtil;
//import com.nongtt.farmerlookup.util.SystemUtil;
//import com.nongtt.farmerlookup.util.TSnackbarUtil;
//import com.nongtt.farmerlookup.util.ToastUtil;
//import com.nongtt.farmerlookup.util.WinManagerUtil;
//import com.nongtt.farmerlookup.widget.SpeechPanel;
//import com.videogo.constant.Constant;
//import com.videogo.constant.IntentConsts;
//import com.videogo.errorlayer.ErrorInfo;
//import com.videogo.exception.BaseException;
//import com.videogo.exception.ErrorCode;
//import com.videogo.exception.InnerException;
//import com.videogo.openapi.EZConstants;
//import com.videogo.openapi.EZPlayer;
//import com.videogo.openapi.bean.EZCameraInfo;
//import com.videogo.openapi.bean.EZDeviceInfo;
//import com.videogo.realplay.RealPlayStatus;
//import com.videogo.util.ConnectionDetector;
//import com.videogo.util.DateTimeUtil;
//import com.videogo.util.LocalInfo;
//import com.videogo.util.MediaScanner;
//import com.videogo.util.RotateViewUtil;
//import com.videogo.util.SDCardUtil;
//import com.videogo.util.Utils;
//import com.videogo.widget.CheckTextButton;
//import com.videogo.widget.CustomRect;
//import com.videogo.widget.CustomTouchListener;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/5/11 10:06
 * <p>
 * 描    述：监控页面实现
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MonitorPage{

}
//
//
//public class MonitorPage extends BasePager implements OnItemClickListener, View.OnClickListener
//        , SurfaceHolder.Callback, Handler.Callback, VerifyCodeInput.VerifyCodeInputListener
//        , PtzPanelLand.OnCloseClickListener, SpeechPanel.OnToggleChangeListener {
//
//    // UI消息
//    private static final int MSG_PLAY_UI_UPDATE = 200;
//    private static final int MSG_AUTO_START_PLAY = 202;
//    private static final int MSG_HIDE_PTZ_DIRECTION = 204;
//    private static final int MSG_PLAY_UI_REFRESH = 206;
//    private static final int MSG_PREVIEW_START_PLAY = 207;
//    private static final int MSG_HIDE_CONTROL_BAR = 208;
//    private static final int MSG_SHOW_CONTROL_BAR = 209;
//    private static final int MSG_HIDE_DELAYED_CONTROL_BAR = 210;
//
//    private LocalInfo mLocalInfo = null;
//    private Handler mHandler = null;
//    private float mRealRatio = Constant.LIVE_VIEW_RATIO;
//    //标识是否正在播放
//    private int mStatus = RealPlayStatus.STATUS_INIT;
//    private boolean mIsOnStop = false;
//    //屏幕当前方向
//    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
//
//    private SurfaceView mRealplaySurfaceView = null;
//    private SurfaceHolder mRealplayHolder = null;
//    private CustomTouchListener mRealPlayTouchListener = null;
//    // 播放比例
//    private float mPlayScale = 1;
//    //监听锁屏解锁的事件
//    private RealPlayBroadcastReceiver mBroadcastReceiver = null;
//    // 弱提示预览信息
//    private long mStartTime = 0;
//    private long mStopTime = 0;
//    // 云台控制状态
//    private float mZoomScale = 0;
//    //存放上一次的流量
//    private long mStreamFlow = 0;
//    //演示点预览控制对象
//    private EZPlayer mEZPlayer = null;
//    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;
//    private EZDeviceInfo mDeviceInfo = null;
//    private EZCameraInfo mCameraInfo = null;
//    //定时器
//    private Timer mUpdateTimer = null;
//    //定时器执行的任务
//    private TimerTask mUpdateTimerTask = null;
//    //录像时间
//    private int mRecordSecond = 0;
//    private int mCaptureDisplaySec = 0;
//    private int mControlDisplaySec = 0;
//    private boolean mIsRecording = false;
//    private String mRecordTime = null;
//    private RotateViewUtil mRecordRotateViewUtil = null;
//    private ScreenOrientationHelper mScreenOrientationHelper;
//    private AudioPlayUtil mAudioPlayUtil;
//    private Map<String, List<SerialChanelBean>> mSerialChanelNumMap = new HashMap<>();
//
//    // 直播预告
//    private TextView mRealPlayPreviewTv = null;
//    private LoadingView mRealPlayPlayLoading;
//    private RelativeLayout mRealPlayLoadingRl;
//    private RelativeLayout rlEzPlayControl;
//    private TextView mRealPlayTipTv;
//    private ImageView mRealplayCenterPlay;
//    private LinearLayout mRealPlayPlayPrivacyLy;
//    private ViewGroup mRealPlayControl;
//    private ImageButton mRealPlayBtn;
//    private Button mRealplayQualityButton;
//    private PopupWindow mQualityPopupWindow;
//    private TextView mRealPlayFlowTv;
//    private CheckTextButton mFullscreenButton;
//    private LinearLayout mPtzControlLy;
//    private PtzPanelLand mPtzPanelLand;
//    private SpeechPanel mPtzMiniPanel;
//    private ImageButton mRealplayRecordButton;
//    private ImageButton mRealPlayRecordStartBtn;
//    private View mRealplayRecordView;
//    private View mRealPlayRecordContainer;
//    private TextView mRealplayRecordTextView;
//    private View btnPlus, btnSubtract;
//    private ImageView btnPtzUp, btnPtzLeft, btnPtzRight, btnPtzDown;
//
//    private MonitorAdapter mMonitorAdapter;
//    private List<SerialChanelBean> mMenuItemList = new ArrayList<>();
//    private PopupWindow mMonitorListPopup;
//    private List<EZCameraInfo> mCameraInfoList;
//    private ImageView mMonitorBgImageView;
//    private Button mBackRecordButton;
//    private Button mScreenshotsButton;
//    private ViewGroup rlFullScreenTitle;
//    private TextView tvFullScreenTitle;
//    private SimpleDialog mShareDialog;
//
//    public MonitorPage(Activity activity) {
//        super(activity);
//        mMonitorPage = this;
//    }
//
//    @Override
//    public void initDatas() {
//        super.initDatas();
//        SystemUtil.println("初始化监控页面数据....");
//
//        flContent.removeAllViews();
//        ViewGroup homeLayout = (ViewGroup) View.inflate(mActivity, R.layout.activity_monitor, null);
//        flContent.addView(homeLayout);
//
//        initView(homeLayout);
//        initMyData();
//    }
//
//    /**
//     * 更新当前页面信息
//     *
//     * @param position 从其它页面切换进来position == -1
//     */
//    @Override
//    public void onUpdateFieldText(final int position) {
//        //初始化地块副标题为可见
//        setSubTitleVisible(true);
//
//        updateFieldText();
//
//        // 停止播放
//        stopRealPlayAndStopUI();
//        prepareMonitorMenu(null);//准备监控视频列表
//        mDeviceInfo = null;
//        mCameraInfo = null;
//        mCameraInfoList = null;
//
//        showLoadingLayout();
//        Runnable target = new Runnable() {
//            @Override
//            public void run() {
//                int size = mDeviceInfoList.size();
//                if (size > 0) {
//                    getMonitorList();//从后台获取监控视频列表
//                } else {
//                    String token = ServerConfigInfo.getInstance().getMonitorToken();
//                    if (TextUtils.isEmpty(token)) {
////                        PostRequestUtil.getAccessTokenFromServer(mCallBack);
//                        getPresenter().getAccessToken();
//                    } else {
//                        getCameraInfoList();
//                    }
//                }
//            }
//        };
//        postDelayed(target);
//    }
//
//    /**
//     * 从后台获取监控视频列表
//     */
//    private void getMonitorList() {
//        Map<String, FieldBean> regionInfoList = ServerConfigInfo.getInstance().getFieldList();
//        if (ListUtil.isEmpty(regionInfoList)) {
//            return;
//        }
//        String key = getFieldText();
//        FieldBean fieldBean = regionInfoList.get(key);
//
//        FieldBean bean = pasreFieldBean(fieldBean, key);
//        if (bean == null) {
//            return;
//        }
//        String fieldName = bean.getField_Name();
//        String fieldImg = bean.getField_Img();
//
////        PostRequestUtil.getMonitorListFromServer(fieldName, fieldImg, mCallBack);
//        getPresenter().getMonitorList(fieldName, fieldImg);
//    }
//
//
//    /**
//     * 递归取出正确的地块
//     */
//    private FieldBean pasreFieldBean(FieldBean fieldBean, String key) {
//        FieldBean bean = null;
//        String fieldType = fieldBean.getField_Type();
//        String fieldName = fieldBean.getField_Name();
//        if (("地块".equals(fieldType) || "监控".equals(fieldType)) && key.equals(fieldName)) {
//            bean = fieldBean;
//        } else {
//            List<FieldBean> fieldChild = fieldBean.getField_Child();
//            if (!ListUtil.isEmpty(fieldChild)) {
//                FieldBean rbb;
//                for (FieldBean rb : fieldChild) {
//                    rbb = pasreFieldBean(rb, key);
//                    if (rbb != null) {
//                        return rbb;
//                    }
//                }
//            }
//        }
//        return bean;
//    }
//
//    /**
//     * 初始化视图
//     *
//     * @param homeLayout 父View
//     */
//    private void initView(ViewGroup homeLayout) {
//
//        // 保持屏幕常亮
//        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        mRealplaySurfaceView = (SurfaceView) homeLayout.findViewById(R.id.sv_realplay);
//        mRealplayHolder = mRealplaySurfaceView.getHolder();
//        mRealplayHolder.addCallback(this);
//
//        //录像回看
//        mBackRecordButton = (Button) homeLayout.findViewById(R.id.btn_back_record);
//        //截图
//        mScreenshotsButton = (Button) homeLayout.findViewById(R.id.btn_screenshots);
//        //录屏
//        mRealplayRecordButton = (ImageButton) homeLayout.findViewById(R.id.btn_record);
//        mRealplayRecordButton.setOnClickListener(this);
//
//        initControlBar(homeLayout);
//        initFullScreenTitle(homeLayout);
//        initPtz(homeLayout);
//        initLandPtz();
//
//        //录制的时间
//        mRealplayRecordView = homeLayout.findViewById(R.id.ll_realplay_record);
//        mRealplayRecordView.setVisibility(View.GONE);
//        mRealplayRecordTextView = (TextView) homeLayout.findViewById(R.id.tv_realplay_record);
//
//        initLoadingUI(homeLayout);
//
//        //视频播放背景图片
//        mMonitorBgImageView = (ImageView) homeLayout.findViewById(R.id.iv_monitor_bg);
//
//        mBackRecordButton.setOnClickListener(this);
//        mScreenshotsButton.setOnClickListener(this);
//
//        //默认不可用
//        mBackRecordButton.setEnabled(false);
//        mScreenshotsButton.setEnabled(false);
//        mRealplayRecordButton.setEnabled(false);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//            return;
//        }
//        postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                if (mRealplaySurfaceView != null) {
//                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(mRealplaySurfaceView.getWindowToken(), 0);
//                }
//            }
//        }, 200);
//
//        initUI();
//        mRealplaySurfaceView.setVisibility(View.VISIBLE);
//
//        if (mCameraInfo != null && mDeviceInfo != null && mDeviceInfo.getStatus() != 1) {
//            if (mStatus != RealPlayStatus.STATUS_STOP) {
//                stopRealPlay();
//            }
//            setRealPlaySuccessUI();
//        } else {
//            if (mStatus == RealPlayStatus.STATUS_INIT || mStatus == RealPlayStatus.STATUS_PAUSE
//                    || mStatus == RealPlayStatus.STATUS_DECRYPT) {
//                // 开始播放
//                startRealPlay();
//            }
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mScreenOrientationHelper != null) {
//            mScreenOrientationHelper.postOnStop();
//        }
//
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_AUTO_START_PLAY);
//
//        if (mStatus != RealPlayStatus.STATUS_STOP) {
//            stopRealPlay();
//            mStatus = RealPlayStatus.STATUS_PAUSE;
//            setRealPlayStopUI();
//        }
//        if (mRealplaySurfaceView != null)
//            mRealplaySurfaceView.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        if (mEZPlayer != null) {
//            mEZPlayer.release();
//        }
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_AUTO_START_PLAY);
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//
//        mHandler = null;
//
//        if (mBroadcastReceiver != null) {
//            // 取消锁屏广播的注册
//            mActivity.unregisterReceiver(mBroadcastReceiver);
//            mBroadcastReceiver = null;
//        }
//        mScreenOrientationHelper = null;
//
//        exit();
//    }
//
//    /**
//     * 初始化加载中UI
//     */
//    private void initLoadingUI(View homeLayout) {
//
//        //加载中控件
//        mRealPlayPlayLoading = (LoadingView) homeLayout.findViewById(R.id.loading_view);
//        mRealPlayLoadingRl = (RelativeLayout) homeLayout.findViewById(R.id.realplay_loading_rl);
//        rlEzPlayControl = (RelativeLayout) homeLayout.findViewById(R.id.rl_realplay_root);
//        mRealPlayTipTv = (TextView) homeLayout.findViewById(R.id.realplay_tip_tv);
//        //中间大播放按钮
//        mRealplayCenterPlay = (ImageView) homeLayout.findViewById(R.id.ic_realplay_center_play);
//        mRealPlayPlayPrivacyLy = (LinearLayout) homeLayout.findViewById(R.id.realplay_privacy_ly);
//
//        mRealPlayPlayLoading.setVisibility(View.INVISIBLE);
//        // 设置点击图标的监听响应函数
//        mRealplayCenterPlay.setOnClickListener(this);
//    }
//
//    /**
//     * 初始化全屏标题栏
//     */
//    private void initFullScreenTitle(View homeLayout) {
//        View ib_full_screen_back = homeLayout.findViewById(R.id.ib_full_screen_back);
//        rlFullScreenTitle = (ViewGroup) homeLayout.findViewById(R.id.rl_full_screen_title);
//        tvFullScreenTitle = (TextView) homeLayout.findViewById(R.id.tv_full_screen_title);
//
//        ib_full_screen_back.setOnClickListener(this);
//        rlFullScreenTitle.setVisibility(View.GONE);
//    }
//
//    /**
//     * 初始化控制栏
//     */
//    private void initControlBar(View homeLayout) {
//        mRealPlayFlowTv = (TextView) homeLayout.findViewById(R.id.realplay_flow_tv);
//        mRealPlayFlowTv.setText("0k/s");
//        mRealPlayControl = (ViewGroup) homeLayout.findViewById(R.id.rl_realplay_control);
//        mRealPlayBtn = (ImageButton) homeLayout.findViewById(R.id.btn_realplay_play);
//        mRealplayQualityButton = (Button) homeLayout.findViewById(R.id.btn_realplay_quality);
//        mFullscreenButton = (CheckTextButton) homeLayout.findViewById(R.id.btn_fullscreen);
//
//        mRealPlayBtn.setOnClickListener(this);
//        mRealplayQualityButton.setOnClickListener(this);
//
//        //不显示网络流量提示
//        mRealPlayFlowTv.setVisibility(View.GONE);
//        //不显示控制栏
//        mRealPlayControl.setVisibility(View.GONE);
////        mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//    }
//
//    /**
//     * 初始化云台控制
//     */
//    private void initPtz(ViewGroup viewGroup) {
//        mPtzControlLy = (LinearLayout) viewGroup.findViewById(R.id.ptz_control_ly);
//        btnPlus = viewGroup.findViewById(R.id.btn_plus);
//        btnSubtract = viewGroup.findViewById(R.id.btn_subtract);
//        btnPtzLeft = (ImageView) viewGroup.findViewById(R.id.btn_ptz_left);
//        btnPtzUp = (ImageView) viewGroup.findViewById(R.id.btn_ptz_up);
//        btnPtzRight = (ImageView) viewGroup.findViewById(R.id.btn_ptz_right);
//        btnPtzDown = (ImageView) viewGroup.findViewById(R.id.btn_ptz_down);
//
//        btnPlus.setOnTouchListener(mOnZoomTouchListener);
//        btnSubtract.setOnTouchListener(mOnZoomTouchListener);
//        btnPtzLeft.setOnTouchListener(mOnTouchListener);
//        btnPtzUp.setOnTouchListener(mOnTouchListener);
//        btnPtzRight.setOnTouchListener(mOnTouchListener);
//        btnPtzDown.setOnTouchListener(mOnTouchListener);
//
//        //默认不可用
//        if (mPtzControlLy != null)
//            setChildEnabled(mPtzControlLy, false);//是否支持云台控制
//        if (btnPlus != null)
//            btnPlus.setEnabled(false);
//        if (btnSubtract != null)
//            btnSubtract.setEnabled(false);
//    }
//
//    private void initLandPtz() {
//        mPtzPanelLand = new PtzPanelLand(mActivity);
//        mPtzMiniPanel = new SpeechPanel(mActivity);
//
////        mPtzPanelLand.setTag(false);
//        mPtzPanelLand.setOnCloseClickListener(this);
//        new WinManagerOnTouch(mPtzPanelLand);
//
////        mPtzMiniPanel.setTag(false);
//        new WinManagerOnTouch(mPtzMiniPanel);
//        mPtzMiniPanel.setPanelIcon(R.mipmap.ic_cloud);
//        mPtzMiniPanel.setOnToggleChangeListener(this);
//    }
//
//    /**
//     * 初始化数据
//     */
//    private void initMyData() {
//
//        // 获取本地信息
//        mAudioPlayUtil = AudioPlayUtil.getInstance(mActivity.getApplication());
//        // 获取配置信息操作对象
//        mLocalInfo = LocalInfo.getInstance();
//        if (mLocalInfo == null) {
//            return;
//        }
//        // 获取屏幕参数
//        DisplayMetrics metric = new DisplayMetrics();
//        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        mLocalInfo.setScreenWidthHeight(metric.widthPixels, metric.heightPixels);
//        mLocalInfo.setNavigationBarHeight((int) Math.ceil(25 * mActivity.getResources().getDisplayMetrics().density));
//
//        mHandler = new Handler(MonitorPage.this);
//        mRecordRotateViewUtil = new RotateViewUtil();
//
//        mBroadcastReceiver = new RealPlayBroadcastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_USER_PRESENT);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        try {
//            mActivity.registerReceiver(mBroadcastReceiver, filter, "", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mRealPlayTouchListener = new CustomTouchListener() {
//
//            @Override
//            public boolean canZoom(float scale) {
//                return isStatusPlay();
//            }
//
//            @Override
//            public boolean canDrag(int direction) {
//                if (mStatus != RealPlayStatus.STATUS_PLAY) {
//                    return false;
//                }
//                if (mEZPlayer != null && mDeviceInfo != null) {
//                    // 出界判断
//                    if (DRAG_LEFT == direction || DRAG_RIGHT == direction) {
//                        // 左移/右移出界判断
//                        if (mDeviceInfo.isSupportPTZ()) {
//                            return true;
//                        }
//                    } else if (DRAG_UP == direction || DRAG_DOWN == direction) {
//                        // 上移/下移出界判断
//                        if (mDeviceInfo.isSupportPTZ()) {
//                            return true;
//                        }
//                    }
//                }
//                return false;
//            }
//
//            @Override
//            public void onSingleClick() {
//                if (isStatusPlay() && mRealPlayControl != null) {
//                    if (mHandler != null)
//                        if (mRealPlayControl.getVisibility() == View.VISIBLE) {
//                            mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//                        } else {
//                            mHandler.sendEmptyMessage(MSG_SHOW_CONTROL_BAR);
//                        }
//                }
//            }
//
//            @Override
//            public void onDoubleClick(MotionEvent e) {
//            }
//
//            @Override
//            public void onZoom(float scale) {
//                if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportZoom()) {
//                    startZoom(scale);
//                }
//            }
//
//            @Override
//            public void onDrag(int direction, float distance, float rate) {
//            }
//
//            @Override
//            public void onEnd(int mode) {
//                if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportZoom()) {
//                    stopZoom();
//                }
//            }
//
//            @Override
//            public void onZoomChange(float scale, CustomRect oRect, CustomRect curRect) {
//                if (mEZPlayer != null && mDeviceInfo != null && mDeviceInfo.isSupportZoom()) {
//                    //采用云台调焦
//                    return;
//                }
//                if (isStatusPlay()) {
//                    if (scale > 1.0f && scale < 1.1f) {
//                        scale = 1.1f;
//                    }
//                    setPlayScaleUI(scale, oRect, curRect);
//                }
//            }
//        };
//        mRealplaySurfaceView.setOnTouchListener(mRealPlayTouchListener);
//        setRealPlaySvLayout();
//
//        mScreenOrientationHelper = new ScreenOrientationHelper(mActivity, mFullscreenButton);
//        mMonitorAdapter = new MonitorAdapter(mMenuItemList);
//    }
//
//    @Override
//    public void onClickSubTitle(View view) {
//        if (!isMonitorList()) {
//            noMonitorDialog();
//            return;
//        }
//
//        int width = view.getMeasuredWidth();
//        int height = view.getMeasuredHeight();
//        final RecyclerView rvMenu = new RecyclerView(mActivity);
//        rvMenu.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
//        rvMenu.setLayoutManager(new LinearLayoutManager(mActivity));
//        rvMenu.setBackgroundResource(R.drawable.shape_corners6_bottom_black88);
//        final int dip1 = DensityUtil.dip2px(1);
//        rvMenu.setPadding(0, 0, 0, dip1 * 6);
//
//        mMonitorAdapter.setOnItemClickListener(this);
//        rvMenu.setAdapter(mMonitorAdapter);
//
//        rvMenu.post(new Runnable() {
//            @Override
//            public void run() {
//
//                View childAt = rvMenu.getChildAt(0);
//                int childAtHeight = childAt.getHeight() * 10 + dip1 * 6;
//                int rvMenuHeight = rvMenu.getHeight();
//
//                if (rvMenuHeight > childAtHeight) {
//                    ViewGroup.LayoutParams params = rvMenu.getLayoutParams();
//                    params.height = childAtHeight;
//                    rvMenu.setLayoutParams(params);
//                }
//            }
//        });
//
//        mMonitorListPopup = new PopupWindow(rvMenu, width, -2, true);
//        mMonitorListPopup.setBackgroundDrawable(new BitmapDrawable());
//        try {
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//            int xoff = location[0];
//            int yoff = location[1];
//
//            yoff = yoff + height + DensityUtil.dip2px(2);
//
//            mMonitorListPopup.showAtLocation(view, Gravity.NO_GRAVITY, xoff, yoff);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 暂无监控视频Dialog
//     */
//    private void noMonitorDialog() {
//        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
//        new SimpleDialog(mActivity)
//                .setLayoutParams(wrapContent, wrapContent)
//                .setTitleName("暂无监控视频")
//                .setPositiveButton(R.string.i_know, null)
//                .setCanceledOnTouchOutside()
//                .setAnimatorSet()
//                .show();
//    }
//
//    private int defIndex = 0;
//    private int clickIndex = defIndex;
//    private boolean isSupportPtz = false;
//
//    @Override
//    public void onItemClick(View view, int position) {
//        SystemUtil.println("第" + position + "个被点击");
//
//        dismissMonitorListPopup();
//
//        if (clickIndex == position && isStatusPlay()) {
//            return;
//        }
//        clickIndex = position;
//        if (mMenuItemList != null) {
//            SerialChanelBean scb = mMenuItemList.get(position);
//            String text = scb.getIeName();
//            String ieType = scb.getIeType();
//            boolean isSupportPtz = ieType.contains("球形");
//            int resource = isSupportPtz ? R.mipmap.ic_monitor_qiuji : R.mipmap.ic_monitor_qiangji;
//
//            //设置地块副标题
//            setSubTitleText(text);
//            setSubTitleCompoundDrawables(resource, -1, -1, -1);
//
//            setSupportPtz(isSupportPtz);
//        }
//
//        if (mCameraInfoList != null) {
//            mCameraInfo = mCameraInfoList.get(position);
//            List<SerialChanelBean> scbList = mSerialChanelNumMap.get(getFieldText());
//            SerialChanelBean serialChanelBean = scbList.get(position);
//            String ieSerial = serialChanelBean.getIeSerial();
//            String ieChanel = serialChanelBean.getIeChanel();
//
//            findCameraInfo(ieSerial, ieChanel);//找到正确的设备信息
//            resumePealplay();//重新播放新的序列号与通道号
//        }
//    }
//
//    /**
//     * 根据序列号与通道号找到正确的设备信息mDeviceInfo等
//     *
//     * @param ieSerial 序列号
//     * @param ieChanel 通道号
//     */
//    private void findCameraInfo(String ieSerial, String ieChanel) {
//        mDeviceInfo = null;
//        int size = mDeviceInfoList.size();
//        for (int i = 0; i < size; i++) {
//            EZDeviceInfo deviceInfo = mDeviceInfoList.get(i);
//            String serial = deviceInfo.getDeviceSerial();
//            if (ieSerial.equals(serial)) {
//
//                mDeviceInfo = deviceInfo;
//                mCameraInfoList = deviceInfo.getCameraInfoList();
//                SystemUtil.println("mDeviceInfo:" + JsonUtil.toJson(deviceInfo));
//
//                int infoSize = mCameraInfoList.size();
//                for (int j = 0; j < infoSize; j++) {
//                    EZCameraInfo info = mCameraInfoList.get(j);
//                    String cameraNo = String.valueOf(info.getCameraNo());
//                    if (ieChanel.equals(cameraNo)) {
//                        mCameraInfo = mCameraInfoList.get(j);
//                        break;
//                    }
//                }
//                if (deviceInfo.getStatus() == 2) {
//                    ToastUtil.showPrompt("该设备离线");
//                }
//                break;
//            }
//        }
//    }
//
//    /**
//     * 重新播放新的序列号与通道号
//     */
//    private void resumePealplay() {
//        getMonitorOperate().resumePealplay();
//    }
//
//    /**
//     * 准备监控视频列表
//     */
//    private void prepareMonitorMenu(List<SerialChanelBean> scbList) {
//        List<SerialChanelBean> list = new ArrayList<>();
//        if (!ListUtil.isEmpty(scbList)) {
//            for (SerialChanelBean info : scbList) {
//                if (info != null && !TextUtils.isEmpty(info.getIeChanel())) {
//                    list.add(info);
//                }
//            }
//        }
//        if (ListUtil.isEmpty(list)) {
//            dismissLoadingLayout();
//            SerialChanelBean bean = new SerialChanelBean();
//            bean.setIeName("暂无监控视频");
//            list.add(bean);
//        }
//        if (mMenuItemList == null) {
//            mMenuItemList = new ArrayList<>();
//        } else {
//            mMenuItemList.clear();
//        }
//        mMenuItemList.addAll(list);
//
//        int index = defIndex;
//        try {
//            int size = mMenuItemList.size();
//            //如果是跳转的，去找跳转的index
//            if (isJump && jumpChanel > -1) {
//                for (int i = 0; i < size; i++) {
//                    SerialChanelBean scb = mMenuItemList.get(i);
//                    String ieChanel = scb.getIeChanel();
//                    if (String.valueOf(jumpChanel).equalsIgnoreCase(ieChanel)) {
//                        index = i;
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        SerialChanelBean scb = mMenuItemList.get(index);
//        String text = scb.getIeName();
//        String ieType = scb.getIeType();
//        int monitorIcon = R.mipmap.ic_monitor_qiangji;
//        if (!TextUtils.isEmpty(ieType)) {
//            boolean isSupportPtz = ieType.contains("球形");
//            monitorIcon = isSupportPtz ? R.mipmap.ic_monitor_qiuji : monitorIcon;
//            setSupportPtz(isSupportPtz);
//        }
//
//        //设置地块副标题
//        setSubTitleText(text);
//        setSubTitleCompoundDrawables(monitorIcon, -1, -1, -1);
//
//        if (mMonitorAdapter != null) {
//            mMonitorAdapter.notifyDataSetChanged();
//        }
//    }
//
//
//    /**
//     * 从服务器获取最新事件消息
//     */
//    private void getCameraInfoList() {
//        String token = ServerConfigInfo.getInstance().getMonitorToken();
//        BaseApplication.getOpenSDK().setAccessToken(token);
//        new GetCamersInfoListTask().execute();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ic_realplay_center_play://中间大播放按钮
//            case R.id.btn_realplay_play://控制栏播放
//                onClickPlayButton();
//                break;
//            case R.id.btn_realplay_quality://控制栏播放质量
//                onClickQuality();
//                break;
//            case R.id.btn_back_record://录像回看
//                onClickBackRecord(view);
//                break;
//            case R.id.btn_screenshots://截图
//                onClickScreenshots();
//                break;
//            case R.id.btn_record://录屏
//                onClickRecord();
//                break;
//            case R.id.ib_full_screen_back://全屏返回
//                onBackPressed();
//                break;
//        }
//    }
//
//    /**
//     * 点击：录屏
//     */
//    private void onClickRecord() {
//        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        OnPermissionListener onPermissionListener = new OnPermissionListener() {
//            @Override
//            public void onGranted() {
//                onRecordBtnClick();
//            }
//
//            @Override
//            public void onDenied(List<String> permissions) {
//                ToastUtil.showPrompt("您没有授权\"读写SD卡\"权限，录屏失败");
//            }
//        };
//        PermissionUtil.request(onPermissionListener, permission);
//    }
//
//    /**
//     * 点击：截图
//     */
//    private void onClickScreenshots() {
//        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        OnPermissionListener onPermissionListener = new OnPermissionListener() {
//            @Override
//            public void onGranted() {
//                onCapturePicture();
//            }
//
//            @Override
//            public void onDenied(List<String> permissions) {
//                ToastUtil.showPrompt("您没有授权\"读写SD卡\"权限，截图失败");
//            }
//        };
//        PermissionUtil.request(onPermissionListener, permissions);
//    }
//
//    /**
//     * 点击：录像回看
//     */
//    private void onClickBackRecord(View view) {
//        if (mCameraInfo == null) {
//            view.setEnabled(false);
//            SystemUtil.println("mCameraInfo==null");
//            return;
//        }
//        Intent intent = new Intent(mActivity, PlayBackListActivity.class);
//        intent.putExtra(RemoteListContant.QUERY_DATE_INTENT_KEY, DateTimeUtil.getNow());
//        intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, mCameraInfo);
//        intent.putExtra("titlebar_name", getFieldText() + " - " + getSubTitleText());
//        mActivity.startActivity(intent);
//    }
//
//    /**
//     * 点击：控制栏播放质量
//     */
//    private void onClickQuality() {
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_HIDE_DELAYED_CONTROL_BAR);
//        if (mHandler != null)
//            mHandler.sendEmptyMessage(MSG_HIDE_DELAYED_CONTROL_BAR);
//        openQualityPopupWindow(mRealplayQualityButton);
//    }
//
//    /**
//     * 点击：中间大播放按钮，播放或暂停
//     */
//    private void onClickPlayButton() {
//        if (!isMonitorList()) {
//            noMonitorDialog();
//            return;
//        }
//
//        if (mDeviceInfo == null) {
//            dismissLoadingLayout();
//            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
//            new SimpleDialog(mActivity)
//                    .setLayoutParams(wrapContent, wrapContent)
//                    .setTitleName("无效序列号")
//                    .setPositiveButton(R.string.i_know, null)
//                    .setCanceledOnTouchOutside()
//                    .setAnimatorSet()
//                    .show();
//            mBackRecordButton.setEnabled(false);
//            return;
//        }
//
//        if (mStatus != RealPlayStatus.STATUS_STOP) {
//            stopRealPlayAndStopUI();
//        } else {
//            startRealPlay();
//        }
//    }
//
//    /**
//     * 停止播放和更新UI
//     */
//    private void stopRealPlayAndStopUI() {
//        final Runnable action = new Runnable() {
//            @Override
//            public void run() {
//                stopRealPlay();
//                setRealPlayStopUI();
//            }
//        };
//        Runnable target = new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(300);
//                if (mMonitorBgImageView != null)
//                    while (mMonitorBgImageView.getVisibility() != View.VISIBLE) {
//                        mActivity.runOnUiThread(action);
//                        SystemClock.sleep(500);
//                    }
//            }
//        };
//        new Thread(target).start();
//
//        stopRealPlay();
//        setRealPlayStopUI();
//    }
//
//    /**
//     * 是否存在监控视频列表
//     *
//     * @return true存在
//     */
//    private boolean isMonitorList() {
//        boolean isMonitorList = true;
//        ;
//        if (!ListUtil.isEmpty(mMenuItemList)) {
//            if (mMenuItemList.size() == 1) {
//                String str = mMenuItemList.get(0).getIeName();
//                if ("暂无监控视频".equals(str)) {
//                    isMonitorList = false;
//                }
//            }
//        } else {
//            isMonitorList = false;
//        }
//        return isMonitorList;
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        if (mEZPlayer != null) {
//            mEZPlayer.setSurfaceHold(holder);
//        }
//        mRealplayHolder = holder;
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        //解决视频画面没有占满整个视图窗口
//        if (holder != null)
//            holder.setFixedSize(width, height);
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (mEZPlayer != null) {
//            mEZPlayer.setSurfaceHold(null);
//        }
//        mRealplayHolder = null;
//    }
//
//    @Override
//    public boolean handleMessage(Message msg) {
//        if (mActivity.isFinishing()) {
//            return false;
//        }
//        switch (msg.what) {
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
//                handlePlaySuccess(msg);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
//                handlePlayFail(msg.obj);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS:
//                handleSetVedioModeSuccess();
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL:
//                handleSetVedioModeFail(msg.arg1);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_PTZ_SET_FAIL:
//                handlePtzControlFail(msg);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_STOP:
//                handleVoiceTalkStoped(false);
//                break;
//            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_FAIL:
//                ErrorInfo errorInfo = (ErrorInfo) msg.obj;
//                handleVoiceTalkFailed(errorInfo);
//                break;
//            case MSG_AUTO_START_PLAY:
//                startRealPlay();
//                break;
//            case MSG_HIDE_PTZ_DIRECTION:
//                handleHidePtzDirection(msg);
//                break;
//            case MSG_PLAY_UI_REFRESH:
//                initUI();
//                break;
//            case MSG_PREVIEW_START_PLAY:
//                mRealPlayPreviewTv.setVisibility(View.GONE);
//                mStatus = RealPlayStatus.STATUS_INIT;
//                startRealPlay();
//                break;
//            case MSG_PLAY_UI_UPDATE:
//                updateRealPlayUI();
//                break;
//            case MSG_HIDE_CONTROL_BAR://隐藏控制栏
//
//                if (isShowControlBar) {
//                    isShowControlBar = false;
//                    closeQualityPopupWindow();
//                    MyAnimatorListener animatorListener = new MyAnimatorListener() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            closeQualityPopupWindow();
//                            if (mRealPlayControl != null)
//                                mRealPlayControl.setVisibility(View.GONE);
//                            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE)
//                                rlFullScreenTitle.setVisibility(View.GONE);
//                        }
//                    };
//                    hideControlBarAnimation(mRealPlayControl, animatorListener);
//
//                    if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//                        hideFullScreenTitleAnimation(rlFullScreenTitle, animatorListener);
//                    }
//                }
//                break;
//            case MSG_SHOW_CONTROL_BAR://显示控制栏
//
//                isShowControlBar = true;
//                if (mRealPlayControl.getVisibility() == View.VISIBLE) {
//                    if (mHandler != null)
//                        mHandler.sendEmptyMessage(MSG_HIDE_DELAYED_CONTROL_BAR);
//                } else {
//                    MyAnimatorListener animatorListener = new MyAnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            if (mRealPlayControl != null)
//                                mRealPlayControl.setVisibility(View.VISIBLE);
//                            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE)
//                                rlFullScreenTitle.setVisibility(View.VISIBLE);
//                        }
//                    };
//                    showControlBarAnimation(mRealPlayControl, animatorListener);
//                    if (mHandler != null)
//                        mHandler.sendEmptyMessage(MSG_HIDE_DELAYED_CONTROL_BAR);
//
//                    if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//                        showFullScreenTitleAnimation(rlFullScreenTitle, animatorListener);
//                        String titleMonitorText = getSubTitleText();
//                        String fieldText = getFieldText();
//                        String text = fieldText.equals(titleMonitorText) ? fieldText : fieldText + "-" + titleMonitorText;
//                        tvFullScreenTitle.setText(text);
//                    }
//                }
//                break;
//            case MSG_HIDE_DELAYED_CONTROL_BAR://延时隐藏控制栏
//
//                if (mHandler != null)
//                    mHandler.removeMessages(MSG_HIDE_CONTROL_BAR);
//                if (mHandler != null)
//                    mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL_BAR, 5000);
//                break;
//        }
//        return false;
//    }
//
//    private boolean isShowControlBar = false;
//    private List<EZDeviceInfo> mDeviceInfoList = new ArrayList<>();
//    private boolean isOpenPanelLand = false;  //打开大控制面板的标记
//    private int mPanelCentreX, mPanelCentreY;
//
//    //语音
//    @Override
//    public boolean onSpeechResult(SpeechPanel speechPanel, String result) {
//        super.onSpeechResult(speechPanel, result);
//
//        CommandUtil.CommandWordEnum commandWordEnum = CommandUtil.getInstance().findCommand("监控", result);
//        if (commandWordEnum != null) {
//            View clickView = null;
//            int millis = 500;
//            String value = commandWordEnum.getValue();
//            switch (commandWordEnum) {
//                case MONITOR_ZOOM_IN://画面放大
//                    clickView = btnPlus;
//                    millis = 700;
//                    break;
//                case MONITOR_ZOOM_OUT://画面缩小
//                    clickView = btnSubtract;
//                    millis = 700;
//                    break;
//                case MONITOR_MOVE_LEFT://画面向左移动
//                    clickView = btnPtzLeft;
//                    break;
//                case MONITOR_MOVE_TOP://画面向上移动
//                    clickView = btnPtzUp;
//                    break;
//                case MONITOR_MOVE_RIGHT://画面向右移动
//                    clickView = btnPtzRight;
//                    break;
//                case MONITOR_MOVE_BOTTOM://画面向下移动
//                    clickView = btnPtzDown;
//                    break;
//                case MONITOR_OPEN_PANEL://打开控制面板
//                    SystemUtil.println("执行指令:" + value + ":" + result);
//                    if (mOrientation != Configuration.ORIENTATION_PORTRAIT) {
////                        ToastUtil.showPrompt("执行指令:" + value, ToastUtil.LENGTH_LONG);
//
//                        openPanel(true); //打开大控制面板
//                        return true;
//                    }
//                    break;
//                case MONITOR_CLOSE_PANEL://关闭云台控制面板
//                    SystemUtil.println("执行指令:" + value + ":" + result);
//                    if (mOrientation != Configuration.ORIENTATION_PORTRAIT) {
//
//                        openPanel(false);//打开小控制面板
//                        return true;
//                    }
//                    break;
//            }
//
//            if (clickView != null) {
//                SystemUtil.println("执行指令:" + value + ":" + result);
////                ToastUtil.showPrompt("执行指令:" + value, ToastUtil.LENGTH_LONG);
//                ClickUtil.simulateClick(clickView, millis);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 关闭云台控制面板
//     */
//    private void closePanel(ViewGroup viewGroup) {
//        WindowManager.LayoutParams params = (WindowManager.LayoutParams) viewGroup.getLayoutParams();
//        if (params != null) {
//
//            if (viewGroup instanceof PtzPanelLand) {
//                PtzPanelLand ptzPanelLand = (PtzPanelLand) viewGroup;
//                mPanelCentreX = params.x + (int) ptzPanelLand.getCentreX();
//                mPanelCentreY = params.y + (int) ptzPanelLand.getCentreY();
//            } else {
//                mPanelCentreX = params.x + viewGroup.getWidth() / 2;
//                mPanelCentreY = params.y + viewGroup.getHeight() / 2;
//            }
//        }
//
//        WinManagerUtil.getInstance().remove(viewGroup);
//        initPtz(flContent);
//        updateSupportPtz(); //是否支持云台控制
//    }
//
//    @Override
//    public void onClose(View view) {
//        openPanel(false);//打开小控制面板
//    }
//
//    @Override
//    public void onToggleChanged(View view, boolean isChecked) {
//        openPanel(true); //打开大控制面板
//    }
//
//    /**
//     * 打开控制面板
//     */
//    private void openPanel(boolean isBig) {
//        //打开的面板是否是大控制面板
//        isOpenPanelLand = isBig;
//        mPtzPanelLand.clearAnimation();
//        if (isBig) {
//            //关闭小的控制面板
//            closePanel(mPtzMiniPanel);
//
//            //打开大的控制面板
//            WinManagerUtil.getInstance().setup(mActivity, mPtzPanelLand
//                    , mPanelCentreX - (int) mPtzPanelLand.getCentreX()
//                    , mPanelCentreY - (int) mPtzPanelLand.getCentreY()
//                    , WinManagerUtil.ModeEnum.LAUNCH_MODE_NORMAL);
//
//            View background = mPtzPanelLand.getPanelBackground();
//            mPtzPanelLand.setPivotX(mPtzPanelLand.getCentreX());
//            mPtzPanelLand.setPivotY(mPtzPanelLand.getCentreY());
//
//            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.playTogether(
//                    ObjectAnimator.ofFloat(background, "rotation", 0, 360),
//                    ObjectAnimator.ofFloat(mPtzPanelLand, "scaleX", 0, 1),
//                    ObjectAnimator.ofFloat(mPtzPanelLand, "scaleY", 0, 1)
//            );
//            animatorSet.setDuration(200);
//            animatorSet.start();
//
//
//            initPtz(mPtzPanelLand);
//            updateSupportPtz(); //是否支持云台控制
//        } else {
//            //关闭大的先更新面板中心点坐标
//            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mPtzPanelLand.getLayoutParams();
//            if (params != null) {
//                mPanelCentreX = params.x + (int) mPtzPanelLand.getCentreX();
//                mPanelCentreY = params.y + (int) mPtzPanelLand.getCentreY();
//            }
//
//            //关闭大的控制面板
//            View background = mPtzPanelLand.getPanelBackground();
//            mPtzPanelLand.setPivotX(mPtzPanelLand.getCentreX());
//            mPtzPanelLand.setPivotY(mPtzPanelLand.getCentreY());
//            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.playTogether(
//                    ObjectAnimator.ofFloat(background, "rotation", 0, 360),
//                    ObjectAnimator.ofFloat(mPtzPanelLand, "scaleX", 1, 0),
//                    ObjectAnimator.ofFloat(mPtzPanelLand, "scaleY", 1, 0)
//            );
//            animatorSet.setDuration(300);
//            animatorSet.addListener(new AnimatorSetUtil.AnimatorListener() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mPtzMiniPanel.setChildEnabled(true);//设置所有子控件可用
//                }
//            });
//            animatorSet.start();
//
//            //避免动画执行完毕后，关闭大面板有一个闪烁的问题
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    closePanel(mPtzPanelLand); //关闭大的控制面板
//                }
//            }, 250);
//
//            mPtzMiniPanel.setChildEnabled(false);//禁用所有子控件
//            int miniPanelWidth = mPtzMiniPanel.getWidth();
//            int miniPanelHeight = mPtzMiniPanel.getHeight();
//            if (miniPanelWidth <= 0 && miniPanelHeight <= 0) {
//                mPtzMiniPanel.measure(0, 0);
//                miniPanelWidth = mPtzMiniPanel.getMeasuredWidth();
//                miniPanelHeight = mPtzMiniPanel.getMeasuredHeight();
//            }
//
//            //打开小的控制面板
//            WinManagerUtil.getInstance().setup(mActivity, mPtzMiniPanel
//                    , mPanelCentreX - miniPanelWidth / 2
//                    , mPanelCentreY - miniPanelHeight / 2
//                    , WinManagerUtil.ModeEnum.LAUNCH_MODE_EMBED);
//        }
//        savePanelStatus();
//    }
//
//    /**
//     * 解决子View消费掉OnTouch事件后，整个View无法移动
//     */
//    private class WinManagerOnTouch implements View.OnTouchListener {
//        private WinManagerUtil.OnChildTouchListener viewGroup;
//
//        private WinManagerOnTouch(WinManagerUtil.OnChildTouchListener viewGroup) {
//            this.viewGroup = viewGroup;
//            viewGroup.setOnChildTouchListener(this);
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            //解决子View消费掉OnTouch事件后，整个View无法移动
//            WinManagerUtil.getInstance().addOnTouchListener((ViewGroup) viewGroup, event);
//            return false;
//        }
//    }
//
//    /**
//     * 获取事件消息任务
//     */
//    private class GetCamersInfoListTask extends AsyncTask<Void, Void, List<EZDeviceInfo>> {
//        private int mErrorCode = 0;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected List<EZDeviceInfo> doInBackground(Void... params) {
//
//            try {
//                return BaseApplication.getOpenSDK().getDeviceList(0, 500);
//            } catch (BaseException e) {
//                ErrorInfo errorInfo = (ErrorInfo) e.getObject();
//                mErrorCode = errorInfo.errorCode;
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(List<EZDeviceInfo> result) {
//            super.onPostExecute(result);
//            SystemUtil.println("onPostExecute result:" + JsonUtil.toJson(result));
//
//            dismissLoadingLayout();
//            if (result != null) {
//                mDeviceInfoList = result;
//                getMonitorList();//从后台获取监控视频列表
//            }
//            if (mErrorCode != 0) {
//                onError(mErrorCode);
//            }
//        }
//
//        protected void onError(int errorCode) {
//            switch (errorCode) {
//                case ErrorCode.ERROR_WEB_SESSION_ERROR:
//                case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    /**
//     * screen状态广播接收者
//     */
//    private class RealPlayBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
//                if (mStatus != RealPlayStatus.STATUS_STOP) {
//                    stopRealPlay();
//                    mStatus = RealPlayStatus.STATUS_PAUSE;
//                    setRealPlayStopUI();
//                }
//            }
//        }
//    }
//
//    /**
//     * 上下左右OnTouch监听
//     */
//    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
//
//        @Override
//        public boolean onTouch(View view, MotionEvent motionevent) {
//            int action = motionevent.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:
//                    switch (view.getId()) {
//                        case R.id.btn_ptz_up:
//                            setPtzDirectionIv(RealPlayStatus.PTZ_UP);
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTART);
//                            break;
//                        case R.id.btn_ptz_down:
//                            setPtzDirectionIv(RealPlayStatus.PTZ_DOWN);
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTART);
//                            break;
//                        case R.id.btn_ptz_left:
//                            setPtzDirectionIv(RealPlayStatus.PTZ_LEFT);
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTART);
//                            break;
//                        case R.id.btn_ptz_right:
//                            setPtzDirectionIv(RealPlayStatus.PTZ_RIGHT);
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTART);
//                            break;
//                        default:
//                            break;
//                    }
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                case MotionEvent.ACTION_UP:
//                    switch (view.getId()) {
//                        case R.id.btn_ptz_up:
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTOP);
//                            break;
//                        case R.id.btn_ptz_down:
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTOP);
//                            break;
//                        case R.id.btn_ptz_left:
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTOP);
//                            break;
//                        case R.id.btn_ptz_right:
//                            ptzOption(EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTOP);
//                            break;
//                        default:
//                            break;
//                    }
//                    break;
//            }
//            return false;
//        }
//    };
//
//    /**
//     * 缩放OnTouch监听
//     */
//    private View.OnTouchListener mOnZoomTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            String accessToken = BaseApplication.getOpenSDK().getEZAccessToken().getAccessToken();
//            String deviceSerial = mCameraInfo.getDeviceSerial();
//            int cameraNo = mCameraInfo.getCameraNo();
//
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//
//                    v.setPressed(true);
//                    switch (v.getId()) {
//                        case R.id.btn_plus:
//
//                            PostRequestUtil.startPtzFromServer(accessToken, deviceSerial, cameraNo + "", "8", null);
//                            break;
//                        case R.id.btn_subtract:
//
//                            PostRequestUtil.startPtzFromServer(accessToken, deviceSerial, cameraNo + "", "9", null);
//                            break;
//                    }
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                case MotionEvent.ACTION_UP:
//                    v.setPressed(false);
//
//                    PostRequestUtil.stopPtzFromServer(accessToken, deviceSerial, cameraNo + "", null);
//                    break;
//            }
//            return true;
//        }
//    };
//
//    private void exit() {
//        if (mStatus != RealPlayStatus.STATUS_STOP) {
//            stopRealPlay();
//            setRealPlayStopUI();
//        }
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_AUTO_START_PLAY);
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//        if (mBroadcastReceiver != null) {
//            // 取消锁屏广播的注册
//            mActivity.unregisterReceiver(mBroadcastReceiver);
//            mBroadcastReceiver = null;
//        }
//        if (mActivity.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
//            if (mScreenOrientationHelper != null)
//                mScreenOrientationHelper.portrait();
//        }
//    }
//
//
//    private void startZoom(float scale) {
//        if (mEZPlayer == null) {
//            return;
//        }
//
//        boolean preZoomIn = mZoomScale > 1.01;
//        boolean zoomIn = scale > 1.01;
//        if (mZoomScale != 0 && preZoomIn != zoomIn) {
//            mZoomScale = 0;
//        }
//        if (scale != 0 && (mZoomScale == 0 || preZoomIn != zoomIn)) {
//            mZoomScale = scale;
//        }
//    }
//
//    private void stopZoom() {
//        if (mEZPlayer == null) {
//            return;
//        }
//        if (mZoomScale != 0) {
//            mZoomScale = 0;
//        }
//    }
//
//    private void setPtzDirectionIv(int command) {
//        setPtzDirectionIv(command, 0);
//    }
//
//    private void setPtzDirectionIv(int command, int errorCode) {
//        if (command != -1 && errorCode == 0) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            switch (command) {
//                case RealPlayStatus.PTZ_LEFT:
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                    break;
//                case RealPlayStatus.PTZ_RIGHT:
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    break;
//                case RealPlayStatus.PTZ_UP:
//                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                    break;
//                case RealPlayStatus.PTZ_DOWN:
//                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                    params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.sv_realplay);
//                    break;
//                default:
//                    break;
//            }
//            if (mHandler != null)
//                mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//            Message msg = new Message();
//            msg.what = MSG_HIDE_PTZ_DIRECTION;
//            msg.arg1 = 1;
//            if (mHandler != null)
//                mHandler.sendMessageDelayed(msg, 500);
//        } else if (errorCode != 0) {
//            RelativeLayout.LayoutParams svParams = (RelativeLayout.LayoutParams) mRealplaySurfaceView.getLayoutParams();
//            RelativeLayout.LayoutParams params;
//            switch (errorCode) {
//                case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED:
//                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, svParams.height);
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                    break;
//                case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED:
//                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, svParams.height);
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    break;
//                case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED:
//                    params = new RelativeLayout.LayoutParams(svParams.width, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                    break;
//                case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED:
//                    params = new RelativeLayout.LayoutParams(svParams.width, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                    params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.sv_realplay);
//                    break;
//                default:
//                    break;
//            }
//            if (mHandler != null)
//                mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//            Message msg = new Message();
//            msg.what = MSG_HIDE_PTZ_DIRECTION;
//            msg.arg1 = 1;
//            if (mHandler != null)
//                mHandler.sendMessageDelayed(msg, 500);
//        } else {
//            if (mHandler != null)
//                mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//        }
//    }
//
//    // 初始化UI
//    private void initUI() {
//
//        if (mCameraInfo != null) {
//            updateUI();
//        }
//
//        mRealplayQualityButton.setVisibility(View.VISIBLE);
//    }
//
//    /**
//     * 设置视频质量
//     */
//    private void setVideoLevel() {
//        getMonitorOperate().setVideoLevel();
//    }
//
//    /**
//     * 屏幕方向改变
//     */
//    private void onOrientationChanged() {
//        dismissMonitorListPopup();
//        dismissFieldWindow();
//
//        setRemoteListSvLayout();
//
//        boolean isPortrait = mOrientation == Configuration.ORIENTATION_PORTRAIT;
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) flContent.getLayoutParams();
//        if (isPortrait) {
//            params.setMargins(0, DensityUtil.dip2px(48.5f), 0, 0);
//        } else {
//            params.setMargins(0, 0, 0, 0);
//        }
//        flContent.setLayoutParams(params);
//
//        isShowTitlebar(!isPortrait);
//        updateOperatorUI();
//
//        //如果屏幕是竖屏，隐藏全屏才显示的标题栏
//        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
//            MyAnimatorListener animatorListener = new MyAnimatorListener() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    if (mOrientation == Configuration.ORIENTATION_PORTRAIT)
//                        rlFullScreenTitle.setVisibility(View.GONE);
//                }
//            };
//            hideFullScreenTitleAnimation(rlFullScreenTitle, animatorListener);
//
//            // 退出全屏
//            // 隐藏全屏的监控控制面板
//            closePanel(mPtzPanelLand);//关闭云台控制面板
//            closePanel(mPtzMiniPanel);//关闭云台控制面板
//        } else {
//
//            // 进入全屏后，如果之前打开过则打开监控控制面板
//            openPanel(isOpenPanelLand);
//        }
//
//    }
//
//    private void setRemoteListSvLayout() {
//        boolean isPortrait = mOrientation == Configuration.ORIENTATION_PORTRAIT;
//
//        int getScreenHeight = mLocalInfo.getScreenHeight();
//        int getScreenWidth = mLocalInfo.getScreenWidth();
//
//        int portrait = getScreenHeight - mLocalInfo.getNavigationBarHeight();
//
//        // 设置播放窗口位置
//        int screenHeight = isPortrait ? portrait : getScreenHeight;
//        int canDisplayHeight = (int) (getScreenWidth * Constant.LIVE_VIEW_RATIO);
//        RelativeLayout.LayoutParams realPlaySvlp = Utils.getPlayViewLp(mRealRatio, mOrientation
//                , getScreenWidth, canDisplayHeight, getScreenWidth, screenHeight);
//
//        RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(realPlaySvlp.width, realPlaySvlp.height);
//        svLp.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//        mRealplaySurfaceView.setLayoutParams(svLp);
//        mRealPlayLoadingRl.setLayoutParams(svLp);
//        rlEzPlayControl.setLayoutParams(svLp);
//        mMonitorBgImageView.setLayoutParams(svLp);
//
//        mRealPlayTouchListener.setSacaleRect(Constant.MAX_SCALE, 0, 0, realPlaySvlp.width, realPlaySvlp.height);
//        setPlayScaleUI(1, null, null);
//    }
//
//    /**
//     * 设置声音
//     */
//    private void setRealplaySound() {
//        getMonitorOperate().setRealplaySound();
//    }
//
//    /**
//     * 开始播放
//     */
//    private void startRealPlay() {
//        getMonitorOperate().startRealPlay();
//    }
//
//    /**
//     * 停止播放
//     */
//    private void stopRealPlay() {
//        getMonitorOperate().stopRealPlay();
//    }
//
//    private void setRealPlayLoadingUI() {
//        mStartTime = System.currentTimeMillis();
//        mRealplaySurfaceView.setVisibility(View.VISIBLE);
//
//        setStartloading();
//        mRealPlayBtn.setImageResource(R.drawable.btn_ez_stop_selector);
//
//        if (mCameraInfo != null && mDeviceInfo != null) {
//            if (mScreenshotsButton != null)
//                mScreenshotsButton.setEnabled(false);
//            if (mRealplayRecordButton != null)
//                mRealplayRecordButton.setEnabled(false);
//            if (mDeviceInfo.getStatus() == 1) {
//                mRealplayQualityButton.setEnabled(true);
//            } else {
//                mRealplayQualityButton.setEnabled(false);
//            }
//        }
//    }
//
//    private void setRealPlayStopUI() {
//        getMonitorOperate().setRealPlayStopUI();
//    }
//
//    /**
//     * 隐藏全屏时的标题栏动画
//     */
//    private void hideFullScreenTitleAnimation(View view, Animator.AnimatorListener animatorListener) {
//        SystemUtil.println("hideFullScreenTitleAnimation");
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", 0, -view.getHeight());
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0f);
//        AnimatorSet set = new AnimatorSet();
//        set.play(animator1).with(animator2);
//        if (animatorListener != null) {
//            set.addListener(animatorListener);
//        }
//        set.setDuration(500);
//        set.start();
//    }
//
//    /**
//     * 显示控制栏动画
//     */
//    private void showFullScreenTitleAnimation(View view, Animator.AnimatorListener animatorListener) {
//        SystemUtil.println("showFullScreenTitleAnimation");
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", -view.getHeight(), 0);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//        AnimatorSet set = new AnimatorSet();
//        set.play(animator1).with(animator2);
//        if (animatorListener != null) {
//            set.addListener(animatorListener);
//        }
//        set.setDuration(500);
//        set.start();
//    }
//
//    /**
//     * 隐藏控制栏动画
//     */
//    private void hideControlBarAnimation(View view, Animator.AnimatorListener animatorListener) {
//        SystemUtil.println("hideControlBarAnimation");
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight());
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0f);
//        AnimatorSet set = new AnimatorSet();
//        set.play(animator1).with(animator2);
//        if (animatorListener != null) {
//            set.addListener(animatorListener);
//        }
//        set.setDuration(500);
//        set.start();
//    }
//
//    /**
//     * 显示控制栏动画
//     */
//    private void showControlBarAnimation(View view, Animator.AnimatorListener animatorListener) {
//        SystemUtil.println("showControlBarAnimation");
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//        AnimatorSet set = new AnimatorSet();
//        set.play(animator1).with(animator2);
//        if (animatorListener != null) {
//            set.addListener(animatorListener);
//        }
//        set.setDuration(500);
//        set.start();
//    }
//
//    private void setRealPlaySuccessUI() {
//
//        mStopTime = System.currentTimeMillis();
//        showType();
//
//        updateOrientation();
//        setLoadingSuccess();
//
//        if (mCameraInfo != null && mDeviceInfo != null) {
//            mRealplayQualityButton.setEnabled(mDeviceInfo.getStatus() == 1);
//        }
//        if (mBackRecordButton != null)
//            mBackRecordButton.setEnabled(true);
//        if (mScreenshotsButton != null)
//            mScreenshotsButton.setEnabled(true);
//        if (mRealplayRecordButton != null)
//            mRealplayRecordButton.setEnabled(true);
//
//        updateSupportPtz(); //是否支持云台控制
//
//        if (mRealPlayFlowTv != null)// 显示网络流量提示
//            mRealPlayFlowTv.setVisibility(View.VISIBLE);
//        if (mHandler != null)  //不显示控制栏
//            mHandler.sendEmptyMessage(MSG_SHOW_CONTROL_BAR);
//        startUpdateTimer();
//
//        Runnable target = new Runnable() {
//            @Override
//            public void run() {
//                if (mMonitorBgImageView != null)
//                    mMonitorBgImageView.setVisibility(View.GONE);
//            }
//        };
//        postDelayed(target, 100);
//    }
//
//    /**
//     * 是否支持云台控制
//     */
//    private void updateSupportPtz() {
//        boolean isSupport = getSupportPtz();
//        if (mPtzControlLy != null)
//            setChildEnabled(mPtzControlLy, isSupport);//是否支持云台控制
//        if (btnPlus != null)
//            btnPlus.setEnabled(isSupport);
//        if (btnSubtract != null)
//            btnSubtract.setEnabled(isSupport);
//    }
//
//    /**
//     * 遍历布局，设置所有子控件是否可用
//     */
//    private void setChildEnabled(ViewGroup viewGroup, boolean enabled) {
//        int childCount = viewGroup.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = viewGroup.getChildAt(i);
//            if (view instanceof ViewGroup) {
//                setChildEnabled((ViewGroup) view, enabled);
//            } else {
//                view.setEnabled(enabled);
//            }
//        }
//    }
//
//    private void setRealPlayFailUI(String errorStr) {
//        mStopTime = System.currentTimeMillis();
//        showType();
//
//        stopUpdateTimer();
//        updateOrientation();
//        setLoadingFail(errorStr);
//
//        if (mRealPlayBtn != null)
//            mRealPlayBtn.setImageResource(R.drawable.btn_ez_play_selector);
//
//        if (mCameraInfo != null && mDeviceInfo != null) {
//            if (mDeviceInfo.getStatus() == 1) {
////            if (mDeviceInfo.getStatus() == 1 && (mEZPlayer == null)) {
//                mRealplayQualityButton.setEnabled(true);
//            } else {
//                mRealplayQualityButton.setEnabled(false);
//            }
//        }
//
//        if (mScreenshotsButton != null)
//            mScreenshotsButton.setEnabled(false);
//        if (mRealplayRecordButton != null)
//            mRealplayRecordButton.setEnabled(false);
//        //关闭云台控制
//        if (mPtzControlLy != null)
//            setChildEnabled(mPtzControlLy, false);//是否支持云台控制
//        if (btnPlus != null)
//            btnPlus.setEnabled(false);
//        if (btnSubtract != null)
//            btnSubtract.setEnabled(false);
//        if (mRealPlayFlowTv != null)//不显示网络流量提示
//            mRealPlayFlowTv.setVisibility(View.GONE);
//        if (mHandler != null)  //不显示控制栏
//            mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//    }
//
//    private void setLoadingSuccess() {
//        if (mRealPlayLoadingRl != null)
//            mRealPlayLoadingRl.setVisibility(View.INVISIBLE);
//        if (mRealPlayTipTv != null)
//            mRealPlayTipTv.setVisibility(View.GONE);
//        if (mRealPlayPlayLoading != null)
//            mRealPlayPlayLoading.setVisibility(View.GONE);
//        if (mRealplayCenterPlay != null)
//            mRealplayCenterPlay.setVisibility(View.GONE);
//    }
//
//    private void setStopLoading() {
//        if (mRealPlayLoadingRl != null)
//            mRealPlayLoadingRl.setVisibility(View.VISIBLE);
//
//        if (mRealPlayTipTv != null)
//            mRealPlayTipTv.setVisibility(View.GONE);
//        if (mRealPlayPlayLoading != null)
//            mRealPlayPlayLoading.setVisibility(View.GONE);
//        if (mRealplayCenterPlay != null)
//            mRealplayCenterPlay.setVisibility(View.VISIBLE);
//        if (mRealPlayPlayPrivacyLy != null)
//            mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
//        if (mMonitorBgImageView != null)
//            mMonitorBgImageView.setVisibility(View.VISIBLE);
//        if (mRealPlayFlowTv != null)//不显示网络流量提示
//            mRealPlayFlowTv.setVisibility(View.GONE);
//        if (mHandler != null)  //不显示控制栏
//            mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//    }
//
//    private void setStartloading() {
//        if (mRealPlayLoadingRl != null)
//            mRealPlayLoadingRl.setVisibility(View.VISIBLE);
//        if (mRealPlayTipTv != null)
//            mRealPlayTipTv.setVisibility(View.GONE);
//        if (mRealPlayPlayLoading != null)
//            mRealPlayPlayLoading.setVisibility(View.VISIBLE);
//        if (mRealplayCenterPlay != null)
//            mRealplayCenterPlay.setVisibility(View.GONE);
//        if (mRealPlayPlayPrivacyLy != null)
//            mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
//        if (mMonitorBgImageView != null)
//            mMonitorBgImageView.setVisibility(View.VISIBLE);
//        if (mRealPlayFlowTv != null)//不显示网络流量提示
//            mRealPlayFlowTv.setVisibility(View.GONE);
//        if (mHandler != null)  //不显示控制栏
//            mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//    }
//
//    private void setLoadingFail(String errorStr) {
//        if (mRealPlayLoadingRl != null)
//            mRealPlayLoadingRl.setVisibility(View.VISIBLE);
//        if (mRealPlayTipTv != null)
//            mRealPlayTipTv.setVisibility(View.VISIBLE);
//        if (mRealPlayTipTv != null)
//            mRealPlayTipTv.setText(errorStr);
//        if (mRealPlayPlayLoading != null)
//            mRealPlayPlayLoading.setVisibility(View.GONE);
//        if (mRealplayCenterPlay != null)
//            mRealplayCenterPlay.setVisibility(View.GONE);
//        if (mRealPlayPlayPrivacyLy != null)
//            mRealPlayPlayPrivacyLy.setVisibility(View.GONE);
//        if (mMonitorBgImageView != null)
//            mMonitorBgImageView.setVisibility(View.VISIBLE);
//        if (mRealPlayFlowTv != null)//不显示网络流量提示
//            mRealPlayFlowTv.setVisibility(View.GONE);
//        if (mHandler != null)  //不显示控制栏
//            mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//    }
//
//    private void updateUI() {
//        setVideoLevel();
//    }
//
//    private void handleHidePtzDirection(Message msg) {
//
//        if (mHandler != null)
//            mHandler.removeMessages(MSG_HIDE_PTZ_DIRECTION);
//        if (msg.arg1 <= 2) {
//            Message message = new Message();
//            message.what = MSG_HIDE_PTZ_DIRECTION;
//            message.arg1 = msg.arg1 + 1;
//            if (mHandler != null)
//                mHandler.sendMessageDelayed(message, 500);
//        }
//    }
//
//    private void handlePtzControlFail(Message msg) {
//        switch (msg.arg1) {
//            case ErrorCode.ERROR_CAS_PTZ_CONTROL_CALLING_PRESET_FAILED:// 正在调用预置点，键控动作无效
//                showToast(R.string.camera_lens_too_busy, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_PRESET_PRESETING_FAILE:// 当前正在调用预置点
//                showToast(R.string.ptz_is_preseting, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_SOUND_LACALIZATION_FAILED:// 当前正在声源定位
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_CONTROL_TIMEOUT_CRUISE_TRACK_FAILED:// 键控动作超时(当前正在轨迹巡航)
//                showToast(R.string.ptz_control_timeout_cruise_track_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_PRESET_INVALID_POSITION_FAILED:// 当前预置点信息无效
//                showToast(R.string.ptz_preset_invalid_position_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_PRESET_CURRENT_POSITION_FAILED:// 该预置点已是当前位置
//                showToast(R.string.ptz_preset_current_position_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_PRESET_SOUND_LOCALIZATION_FAILED:// 设备正在响应本次声源定位
//                showToast(R.string.ptz_preset_sound_localization_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_OPENING_PRIVACY_FAILED:// 当前正在开启隐私遮蔽
//            case ErrorCode.ERROR_CAS_PTZ_CLOSING_PRIVACY_FAILED:// 当前正在关闭隐私遮蔽
//            case ErrorCode.ERROR_CAS_PTZ_MIRRORING_FAILED:// 设备正在镜像操作（设备镜像要几秒钟，防止频繁镜像操作）
//                showToast(R.string.ptz_operation_too_frequently, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_CONTROLING_FAILED:// 设备正在键控动作（上下左右）(一个客户端在上下左右控制，另外一个在开其它东西)
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_FAILED:// 云台当前操作失败
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_PRESET_EXCEED_MAXNUM_FAILED:// 当前预置点超过最大个数
//                showToast(R.string.ptz_preset_exceed_maxnum_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_PRIVACYING_FAILED:// 设备处于隐私遮蔽状态（关闭了镜头，再去操作云台相关）
//                showToast(R.string.ptz_privacying_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_TTSING_FAILED:// 设备处于语音对讲状态(区别以前的语音对讲错误码，云台单独列一个）
//                showToast(R.string.ptz_mirroring_failed, msg.arg1);
//                break;
//            case ErrorCode.ERROR_CAS_PTZ_ROTATION_UP_LIMIT_FAILED:// 设备云台旋转到达上限位
//            case ErrorCode.ERROR_CAS_PTZ_ROTATION_DOWN_LIMIT_FAILED:// 设备云台旋转到达下限位
//            case ErrorCode.ERROR_CAS_PTZ_ROTATION_LEFT_LIMIT_FAILED:// 设备云台旋转到达左限位
//            case ErrorCode.ERROR_CAS_PTZ_ROTATION_RIGHT_LIMIT_FAILED:// 设备云台旋转到达右限位
//                setPtzDirectionIv(-1, msg.arg1);
//                break;
//            default:
//                showToast(R.string.ptz_operation_failed, msg.arg1);
//                break;
//        }
//    }
//
//    private void handleVoiceTalkFailed(ErrorInfo errorInfo) {
//
//        switch (errorInfo.errorCode) {
//            case ErrorCode.ERROR_TRANSF_DEVICE_TALKING:
//                ToastUtil.showPrompt(R.string.realplay_play_talkback_fail_ison);
//                break;
//            case ErrorCode.ERROR_TRANSF_DEVICE_PRIVACYON:
//                ToastUtil.showPrompt(R.string.realplay_play_talkback_fail_privacy);
//                break;
//            case ErrorCode.ERROR_TRANSF_DEVICE_OFFLINE:
//                ToastUtil.showPrompt(R.string.realplay_fail_device_not_exist);
//                break;
//            case ErrorCode.ERROR_TTS_MSG_REQ_TIMEOUT:
//            case ErrorCode.ERROR_TTS_MSG_SVR_HANDLE_TIMEOUT:
//            case ErrorCode.ERROR_TTS_WAIT_TIMEOUT:
//            case ErrorCode.ERROR_TTS_HNADLE_TIMEOUT:
//                showToast(R.string.realplay_play_talkback_request_timeout, errorInfo.errorCode);
//                break;
//            case ErrorCode.ERROR_CAS_AUDIO_SOCKET_ERROR:
//            case ErrorCode.ERROR_CAS_AUDIO_RECV_ERROR:
//            case ErrorCode.ERROR_CAS_AUDIO_SEND_ERROR:
//                showToast(R.string.realplay_play_talkback_network_exception, errorInfo.errorCode);
//                break;
//            default:
//                showToast(R.string.realplay_play_talkback_fail, errorInfo.errorCode);
//                break;
//        }
//    }
//
//    /**
//     * 说话声音结束
//     */
//    private void handleVoiceTalkStoped(boolean startAnim) {
//        getMonitorOperate().handleVoiceTalkStoped(startAnim);
//    }
//
//    private boolean isStatusPlay() {
//        return mStatus == RealPlayStatus.STATUS_PLAY;
//    }
//
//    private void handleSetVedioModeSuccess() {
//        setVideoLevel();
//        // 停止播放
//        stopRealPlay();
//        //下面语句防止stopRealPlay线程还没释放surface, startRealPlay线程已经开始使用surface
//        //因此需要等待500ms
//        SystemClock.sleep(500);
//        // 开始播放
//        startRealPlay();
//    }
//
//    private void handleSetVedioModeFail(int errorCode) {
//        setVideoLevel();
//
//        showToast(R.string.realplay_set_vediomode_fail, errorCode);
//    }
//
//    /**
//     * 处理播放成功的情况
//     */
//    private void handlePlaySuccess(Message msg) {
//        getMonitorOperate().handlePlaySuccess(msg);
//    }
//
//    /**
//     * 处理播放失败的情况
//     *
//     * @param obj - 错误码
//     */
//    private void handlePlayFail(Object obj) {
//        int errorCode = 0;
//        if (obj != null) {
//            ErrorInfo errorInfo = (ErrorInfo) obj;
//            errorCode = errorInfo.errorCode;
//        }
//
//        stopRealPlay();
//        updateRealPlayFailUI(errorCode);
//    }
//
//    private void updateRealPlayFailUI(int errorCode) {
//        String txt = null;
//        // 判断返回的错误码
//        switch (errorCode) {
//            case ErrorCode.ERROR_TRANSF_ACCESSTOKEN_ERROR:
//                ToastUtil.showPrompt("错误:token过期");
//                return;
//            case ErrorCode.ERROR_CAS_MSG_PU_NO_RESOURCE:
//                txt = mActivity.getString(R.string.remoteplayback_over_link);
//                break;
//            case ErrorCode.ERROR_TRANSF_DEVICE_OFFLINE:
//                if (mCameraInfo != null) {
//                    mCameraInfo.setIsShared(0);
//                }
//                txt = mActivity.getString(R.string.realplay_fail_device_not_exist);
//                break;
//            case ErrorCode.ERROR_INNER_STREAM_TIMEOUT:
//                txt = mActivity.getString(R.string.realplay_fail_connect_device);
//                break;
//            case ErrorCode.ERROR_TRANSF_TERMINAL_BINDING:
//                txt = "请在萤石客户端关闭终端绑定";
//                break;
//            // 收到这两个错误码，可以弹出对话框，让用户输入密码后，重新取流预览
//            case ErrorCode.ERROR_INNER_VERIFYCODE_NEED:
//            case ErrorCode.ERROR_INNER_VERIFYCODE_ERROR: {
//                DataManager.getInstance().setDeviceSerialVerifyCode(mCameraInfo.getDeviceSerial(), null);
//                VerifyCodeInput.VerifyCodeInputDialog(mActivity, this).show();
//            }
//            break;
//            case ErrorCode.ERROR_EXTRA_SQUARE_NO_SHARING:
//            default:
//                txt = Utils.getErrorTip(mActivity, R.string.realplay_play_fail, errorCode);
//                break;
//        }
//
//        if (!TextUtils.isEmpty(txt)) {
//            setRealPlayFailUI(txt);
//            //播放失败 显示播放按钮
//            if (mRealplayCenterPlay != null)
//                mRealplayCenterPlay.setVisibility(View.VISIBLE);
//        } else {
//            setRealPlayStopUI();
//        }
//    }
//
//    private void setRealPlaySvLayout() {
//        if (mLocalInfo == null) {
//            return;
//        }
//        // 设置播放窗口位置
//        final int screenWidth = mLocalInfo.getScreenWidth();
//        final int screenHeight = (mOrientation == Configuration.ORIENTATION_PORTRAIT) ? (mLocalInfo.getScreenHeight() - mLocalInfo
//                .getNavigationBarHeight()) : mLocalInfo.getScreenHeight();
//        int canDisplayHeight = (int) (mLocalInfo.getScreenWidth() * Constant.LIVE_VIEW_RATIO);
//        int screenWidth1 = mLocalInfo.getScreenWidth();
//
//        final RelativeLayout.LayoutParams realPlaySvlp = Utils.getPlayViewLp(
//                mRealRatio,
//                mOrientation,
//                screenWidth1,
//                canDisplayHeight,
//                screenWidth,
//                screenHeight);
//
//        RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(realPlaySvlp.width, realPlaySvlp.height);
//        mRealplaySurfaceView.setLayoutParams(svLp);
//
//        mRealPlayTouchListener.setSacaleRect(Constant.MAX_SCALE, 0, 0, realPlaySvlp.width, realPlaySvlp.height);
//        setPlayScaleUI(1, null, null);
//    }
//
//    private void setPlayScaleUI(float scale, CustomRect oRect, CustomRect curRect) {
//        getMonitorOperate().setPlayScaleUI(scale, oRect, curRect);
//    }
//
//    private void showType() {
//        SystemUtil.println("取流耗时：" + (mStopTime - mStartTime));
//        dismissLoadingLayout();
//    }
//
//    @Override
//    public void onInputVerifyCode(final String verifyCode) {
//        DataManager.getInstance().setDeviceSerialVerifyCode(mCameraInfo.getDeviceSerial(), verifyCode);
//
//        if (getMonitorOperate().getPlayer() != null) {
//            startRealPlay();
//        }
//    }
//
//    private void openQualityPopupWindow(View view) {
//        if (getMonitorOperate().getPlayer() == null) {
//            return;
//        }
//        closeQualityPopupWindow();
//        ViewGroup layoutView = (ViewGroup) View.inflate(mActivity, R.layout.realplay_quality_items, null);
//
//        Button qualityHdBtn = (Button) layoutView.findViewById(R.id.quality_hd_btn);
//        Button qualityBalancedBtn = (Button) layoutView.findViewById(R.id.quality_balanced_btn);
//        Button qualityFlunetBtn = (Button) layoutView.findViewById(R.id.quality_flunet_btn);
//        qualityHdBtn.setOnClickListener(mOnPopWndClickListener);
//        qualityBalancedBtn.setOnClickListener(mOnPopWndClickListener);
//        qualityFlunetBtn.setOnClickListener(mOnPopWndClickListener);
//
//        // 视频质量，2-高清，1-标清，0-流畅
//        VideoLevel level = getMonitorOperate().getVideoLevel();
//        switch (level) {
//            case VIDEO_LEVEL_FLUNET:
//                qualityFlunetBtn.setEnabled(false);
//                break;
//            case VIDEO_LEVEL_BALANCED:
//                qualityBalancedBtn.setEnabled(false);
//                break;
//            case VIDEO_LEVEL_HD:
//                qualityHdBtn.setEnabled(false);
//                break;
//        }
////        if (mCameraInfo.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET) {
////            qualityFlunetBtn.setEnabled(false);
////        } else if (mCameraInfo.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED) {
////            qualityBalancedBtn.setEnabled(false);
////        } else if (mCameraInfo.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_HD) {
////            qualityHdBtn.setEnabled(false);
////        }
//
//        qualityFlunetBtn.setVisibility(View.VISIBLE);
//        qualityBalancedBtn.setVisibility(View.VISIBLE);
//        qualityHdBtn.setVisibility(View.VISIBLE);
//
//        mQualityPopupWindow = new PopupWindow(layoutView, -2, -2, true);
//        mQualityPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        try {
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//            int x = location[0];
//            int y = location[1];
//
//            //获取自身的长宽高
//            layoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            int popupWidth = layoutView.getMeasuredWidth();
//            int popupHeight = layoutView.getMeasuredHeight();
//            int xoff = x + view.getWidth() / 2 - popupWidth / 2;
//            int yoff = y - popupHeight;
//
//            mQualityPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xoff, yoff);
//        } catch (Exception e) {
//            closeQualityPopupWindow();
//            e.printStackTrace();
//        }
//    }
//
//    private void closeQualityPopupWindow() {
//        try {
//            dismissPopupWindow(mQualityPopupWindow);
//            mQualityPopupWindow = null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private View.OnClickListener mOnPopWndClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.quality_hd_btn:
//                    closeQualityPopupWindow();
//                    setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_HD);
//                    break;
//                case R.id.quality_balanced_btn:
//                    closeQualityPopupWindow();
//                    setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED);
//                    break;
//                case R.id.quality_flunet_btn:
//                    closeQualityPopupWindow();
//                    setQualityMode(EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET);
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 码流配置 清晰度 2-高清，1-标清，0-流畅
//     */
//    private void setQualityMode(final EZConstants.EZVideoLevel mode) {
//        // 检查网络是否可用
//        if (!ConnectionDetector.isNetworkAvailable(mActivity)) {
//            // 提示没有连接网络
//            ToastUtil.showPrompt(R.string.realplay_set_fail_network);
//            return;
//        }
//
//        if (mEZPlayer != null) {
//
//            Runnable target = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        // need to modify by yudan at 08-11
//                        String deviceSerial = mCameraInfo.getDeviceSerial();
//                        int cameraNo = mCameraInfo.getCameraNo();
//                        int videoLevel = mode.getVideoLevel();
//                        BaseApplication.getOpenSDK().setVideoLevel(deviceSerial, cameraNo, videoLevel);
//
//                        mCurrentQulityMode = mode;
//                        Message msg = Message.obtain();
//                        msg.what = EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_SUCCESS;
//                        if (mHandler != null)
//                            mHandler.sendMessage(msg);
//                    } catch (BaseException e) {
//                        mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET;
//                        e.printStackTrace();
//                        Message msg = Message.obtain();
//                        msg.what = EZConstants.EZRealPlayConstants.MSG_SET_VEDIOMODE_FAIL;
//                        if (mHandler != null)
//                            mHandler.sendMessage(msg);
//                    }
//                }
//            };
//            new Thread(target).start();
//        }
//    }
//
//    private void updateRealPlayUI() {
//        checkRealPlayFlow();//更新流量统计
//        if (mIsRecording) {
//            updateRecordTime();//更新录像时间
//        }
//    }
//
//    /**
//     * 更新流量统计
//     */
//    private void checkRealPlayFlow() {
//        if ((mRealPlayFlowTv.getVisibility() == View.VISIBLE)) {
//            // 更新流量数据
//            long streamFlow = getMonitorOperate().getStreamFlow();
//            updateRealPlayFlowTv(streamFlow);
//        }
//    }
//
//    private void updateRealPlayFlowTv(long streamFlow) {
//        long streamFlowUnit = streamFlow - mStreamFlow;
//        if (streamFlowUnit < 0)
//            streamFlowUnit = 0;
//        float fKBUnit = (float) streamFlowUnit / (float) Constant.KB;
//        String descUnit = String.format("%.2f k/s ", fKBUnit);
//        // 显示流量
//        mRealPlayFlowTv.setText(descUnit);
//        mStreamFlow = streamFlow;
//    }
//
//    /**
//     * 启动定时器
//     */
//    private void startUpdateTimer() {
//        stopUpdateTimer();
//        // 开始录像计时
//        mUpdateTimer = new Timer();
//        mUpdateTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (mControlDisplaySec < 5) {
//                    mControlDisplaySec++;
//                }
//                if (mCaptureDisplaySec < 4) {
//                    mCaptureDisplaySec++;
//                }
//
//                // 更新录像时间
//                if (mEZPlayer != null && mIsRecording) {
//                    // 更新录像时间
//                    Calendar OSDTime = mEZPlayer.getOSDTime();
//                    if (OSDTime != null) {
//                        String playtime = Utils.OSD2Time(OSDTime);
//                        if (!TextUtils.equals(playtime, mRecordTime)) {
//                            mRecordSecond++;
//                            mRecordTime = playtime;
//                        }
//                    }
//                }
//                if (mHandler != null) {
//                    mHandler.sendEmptyMessage(MSG_PLAY_UI_UPDATE);
//                }
//            }
//        };
//        // 延时1000ms后执行，1000ms执行一次
//        mUpdateTimer.schedule(mUpdateTimerTask, 0, 1000);
//    }
//
//    /**
//     * 停止定时器
//     */
//    private void stopUpdateTimer() {
//        mCaptureDisplaySec = 4;
//        if (mHandler != null) {
//            mHandler.removeMessages(MSG_PLAY_UI_UPDATE);
//        }
//        // 停止录像计时
//        if (mUpdateTimer != null) {
//            mUpdateTimer.cancel();
//            mUpdateTimer = null;
//        }
//
//        if (mUpdateTimerTask != null) {
//            mUpdateTimerTask.cancel();
//            mUpdateTimerTask = null;
//        }
//    }
//
//    /**
//     * 云台操作
//     *
//     * @param command ptz控制命令
//     * @param action  控制启动/停止
//     */
//    private void ptzOption(final EZConstants.EZPTZCommand command, final EZConstants.EZPTZAction action) {
//        Runnable target = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String deviceSerial = mCameraInfo.getDeviceSerial();
//                    int cameraNo = mCameraInfo.getCameraNo();
//                    int ptzSpeedDefault = EZConstants.PTZ_SPEED_DEFAULT;
//                    BaseApplication.getOpenSDK().controlPTZ(deviceSerial, cameraNo, command, action, ptzSpeedDefault);
//                } catch (BaseException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(target).start();
//    }
//
//    /**
//     * 抓拍按钮响应函数（截图）
//     */
//    private void onCapturePicture() {
//        getMonitorOperate().onCapturePicture();
//    }
//
//    private class OnShareDialogListener implements View.OnClickListener {
//        private Dialog mDialog;
//        private String path;
//
//        public OnShareDialogListener(Dialog mDialog, String path) {
//            this.mDialog = mDialog;
//            this.path = path;
//        }
//
//        @Override
//        public void onClick(View v) {
//            dismissDialog(mDialog);
//
//            if (v.getId() == R.id.btn_share && !TextUtils.isEmpty(path)) {
//                Uri uri = Uri.fromFile(new File(path));
//                String share = mActivity.getResources().getString(R.string.share);
//                //应用内发送分享
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                shareIntent.setType("image/*");
//                mActivity.startActivity(Intent.createChooser(shareIntent, share));
//            }
//        }
//    }
//
//    /**
//     * 开始录像
//     */
//    private void onRecordBtnClick() {
//        getMonitorOperate().startRecord();
//    }
//
//
//    /**
//     * 停止录像
//     */
//    private void stopRealPlayRecord() {
//        if (mEZPlayer == null || !mIsRecording) {
//            return;
//        }
//
//        if (mOrientation != Configuration.ORIENTATION_PORTRAIT) {
//            return;
//        }
//        ToastUtil.showPrompt(mActivity.getString(R.string.already_saved_to_volume));
//        // 设置录像按钮为check状态
//        if (!mIsOnStop) {
//            if (mRecordRotateViewUtil != null && mRealPlayRecordContainer != null)
//                mRecordRotateViewUtil.applyRotation(mRealPlayRecordContainer,
//                        mRealPlayRecordStartBtn,
//                        mRealplayRecordButton, 0, 90);
//        } else {
//            if (mRealPlayRecordStartBtn != null)
//                mRealPlayRecordStartBtn.setVisibility(View.GONE);
//            if (mRealplayRecordButton != null)
//                mRealplayRecordButton.setVisibility(View.VISIBLE);
//        }
////        mRealPlayFullRecordStartBtn.setVisibility(View.GONE);
////        mRealPlayFullRecordBtn.setVisibility(View.VISIBLE);
//
//        if (mAudioPlayUtil != null)
//            mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
//        if (mEZPlayer != null)
//            mEZPlayer.stopLocalRecord();
//
//        // 计时按钮不可见
//        if (mRealplayRecordView != null)
//            mRealplayRecordView.setVisibility(View.GONE);
//        mCaptureDisplaySec = 0;
//        mIsRecording = false;
//    }
//
//    /**
//     * 开始录像成功
//     */
//    private void handleRecordSuccess() {
//        if (mCameraInfo == null) {
//            return;
//        }
//
//        if (mOrientation != Configuration.ORIENTATION_PORTRAIT) {
//            return;
//        }
//        // 要设置录像按钮为check状态
//        if (!mIsOnStop) {
//            if (mRecordRotateViewUtil != null && mRealPlayRecordContainer != null)
//                mRecordRotateViewUtil.applyRotation(mRealPlayRecordContainer, mRealplayRecordButton,
//                        mRealPlayRecordStartBtn, 0, 90);
//        } else {
//            if (mRealplayRecordButton != null)
//                mRealplayRecordButton.setVisibility(View.GONE);
//            if (mRealPlayRecordStartBtn != null)
//                mRealPlayRecordStartBtn.setVisibility(View.VISIBLE);
//        }
////        mRealPlayFullRecordBtn.setVisibility(View.GONE);
////        mRealPlayFullRecordStartBtn.setVisibility(View.VISIBLE);
//
//        mIsRecording = true;
//        // 计时按钮可见
//        if (mRealplayRecordView != null)
//            mRealplayRecordView.setVisibility(View.VISIBLE);
//        if (mRealplayRecordTextView != null)
//            mRealplayRecordTextView.setText("00:00");
//        mRecordSecond = 0;
//    }
//
//    private void handleRecordFail() {
//        ToastUtil.showPrompt(R.string.remoteplayback_record_fail);
//        if (mIsRecording) {
//            stopRealPlayRecord();
//        }
//    }
//
//    /**
//     * 更新录像时间
//     */
//    private void updateRecordTime() {
//
//        // 计算分秒
//        int leftSecond = mRecordSecond % 3600;
//        int minitue = leftSecond / 60;
//        int second = leftSecond % 60;
//
//        // 显示录像时间
//        String recordTime = String.format("%02d:%02d", minitue, second);
//        mRealplayRecordTextView.setText(recordTime);
//    }
//
//    private void setOrientation(int sensor) {
//
//        if (mScreenOrientationHelper != null) {
//            if (sensor == ActivityInfo.SCREEN_ORIENTATION_SENSOR)
//                mScreenOrientationHelper.enableSensorOrientation();
//            else
//                mScreenOrientationHelper.disableSensorOrientation();
//        }
//    }
//
//    private void updateOrientation() {
//        if (mStatus != RemoteListContant.STATUS_PLAYING) {
//            // 不允许选择屏幕
//            mScreenOrientationHelper.disableSensorOrientation();
//        }
//        if (isStatusPlay()) {
//            setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//        } else {
//            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
//                setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else {
//                setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//            }
//        }
//    }
//
//    private void updateOperatorUI() {
//        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
//            // 显示状态栏
//            fullScreen(false);
//            updateOrientation();
//        } else {
//            // 隐藏状态栏
//            fullScreen(true);
//        }
//        closeQualityPopupWindow();
//    }
//
//    private void fullScreen(boolean enable) {
//
//        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//        //全屏
//        if (enable) {
//            // 全屏：判断版本大于等于16
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                View decorView = mActivity.getWindow().getDecorView();
//                int flagImmersiveSticky = 0x00001000; //  View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                decorView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                                | flagImmersiveSticky);
//            } else {
//                View decorView = mActivity.getWindow().getDecorView();
//                int option = 0x00000004; // View.SYSTEM_UI_FLAG_FULLSCREEN;
//                decorView.setSystemUiVisibility(option);
//            }
//        } else {
//            if (Build.VERSION.SDK_INT < 16) {
//                lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                mActivity.getWindow().setAttributes(lp);
//                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            } else {
//                int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
//                mActivity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
//            }
//        }
//
//
//    }
//
//    /**
//     * 判断是否支持云台控制
//     */
//    private boolean getSupport() {
//        return mDeviceInfo != null && mEZPlayer != null && mDeviceInfo.isSupportPTZ();
//    }
//
//    /**
//     * 判断是否支持云台控制
//     */
//    private boolean getSupportPtz() {
//        return isSupportPtz;
//    }
//
//    private void setSupportPtz(boolean supportPtz) {
//        isSupportPtz = supportPtz;
//    }
//
//    private void showToast(int id, int errCode) {
//        String text = mActivity.getString(id);
//        if (errCode != 0) {
//            text = text + " (" + errCode + ")";
//        }
//        ToastUtil.showPrompt(text);
//    }
//
//
//    private class MyAnimatorListener implements Animator.AnimatorListener {
//
//        @Override
//        public void onAnimationStart(Animator animation) {
//        }
//
//        @Override
//        public void onAnimationEnd(Animator animation) {
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animation) {
//        }
//
//        @Override
//        public void onAnimationRepeat(Animator animation) {
//        }
//    }
//
//    @Override
//    public void onPageEnter(int oldIndex) {
//        super.onPageEnter(oldIndex);
//        if (!isJump) {
//            onUpdateFieldText(-1);
//        }
//
//        setOrientationPortrait();//设置屏幕方向为ORIENTATION_PORTRAIT
//
//        initPanelStatus();
//    }
//
//    @Override
//    public void onPageExit() {
//        super.onPageExit();
//        //页面退出:如果从监控页面切换到其它页面，通知监控停止播放
//        stopRealPlayAndStopUI();
//
//        if (mScreenOrientationHelper != null) {
//            int orientation = mActivity.getResources().getConfiguration().orientation;
//            int portrait = Configuration.ORIENTATION_PORTRAIT;
//            if (orientation != portrait) {
//                mScreenOrientationHelper.portrait();
//                mOrientation = portrait;
//                onOrientationChanged();
//            }
//            mScreenOrientationHelper.disableSensorOrientation();
//        }
//
//        savePanelStatus();
//        setOrientationPortrait();//设置屏幕方向为ORIENTATION_PORTRAIT
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        mOrientation = newConfig.orientation;
//        onOrientationChanged();
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onBackPressed() {
//
//        if (mScreenOrientationHelper != null) {
//            int orientation = mActivity.getResources().getConfiguration().orientation;
//            int portrait = Configuration.ORIENTATION_PORTRAIT;
//            if (orientation != portrait) {
//                mScreenOrientationHelper.portrait();
//                mOrientation = portrait;
//                onOrientationChanged();
//                return true;
//            }
//        }
//        return super.onBackPressed();
//    }
//
//    private boolean isJump = false;
//    private int jumpChanel = -1;
//
//    @Override
//    public void onJump(Bundle bundle) {
//        super.onJump(bundle);
//
//        if (bundle == null) {
//            return;
//        }
//        String field = bundle.getString("field", "");
//        jumpChanel = bundle.getInt("chanel");
//
//        int fieldPosition = 0;
//
//        ArrayList<String> fieldList = getFieldList();
//        int size = fieldList.size();
//        for (int i = 0; i < size; i++) {
//            String str = fieldList.get(i);
//            if (str.equals(field)) {
//                fieldPosition = i;
//                break;
//            }
//        }
//        new MyOnClickListener().onItemClick(null, fieldPosition);
//        isJump = true;
//    }
//
//    /**
//     * 初始化面板状态
//     */
//    private void initPanelStatus() {
//        //页面进入时获取面板状态
//        isOpenPanelLand = PrefUtil.getBoolean("isOpenPanelLand", false);
//        //获取保存的面板中心点
//        mPanelCentreX = PrefUtil.getInt("mPanelCentreX", -1);
//        mPanelCentreY = PrefUtil.getInt("mPanelCentreY", -1);
//    }
//
//    /**
//     * 保存面板状态
//     */
//    private void savePanelStatus() {
//        //页面退出时保存面板状态到本地
//        PrefUtil.setBoolean("isOpenPanelLand", isOpenPanelLand);
//        //保存面板中心点到本地
//        PrefUtil.setInt("mPanelCentreX", mPanelCentreX);
//        PrefUtil.setInt("mPanelCentreY", mPanelCentreY);
//    }
//
//    /**
//     * 关闭监控列表PopupWindow
//     */
//    protected void dismissMonitorListPopup() {
//        dismissPopupWindow(mMonitorListPopup);
//    }
//
//
//    /**
//     * 获取监控视频列表
//     */
//    private void serverReturnMonitorList(final Map<String, List<SerialChanelBean>> result) {
//
//        try {
//            mSerialChanelNumMap.clear();
//            mSerialChanelNumMap.putAll(result);
//
//            String key = getFieldText();
//            final List<SerialChanelBean> serialChanelBeen = mSerialChanelNumMap.get(key);
//            SystemUtil.println("key:" + key + "," + JsonUtil.toJson(serialChanelBeen));
//
//            prepareMonitorMenu(serialChanelBeen);//准备监控视频列表
//            boolean isMonitor = isMonitorList();
//            mBackRecordButton.setEnabled(isMonitor);
//            if (!isMonitor) {
//                TSnackbarUtil.showPrompt(mActivity, "暂无监控视频");
//                return;
//            }
//
//            SerialChanelBean serialChanelBean;
//            int index = defIndex;
//            try {
//                //如果是跳转的，去找跳转的index
//                if (isJump && jumpChanel > -1) {
//                    int size = serialChanelBeen.size();
//                    for (int i = 0; i < size; i++) {
//                        SerialChanelBean scb = serialChanelBeen.get(i);
//                        String ieChanel = scb.getIeChanel();
//                        if (ieChanel.equalsIgnoreCase(String.valueOf(jumpChanel))) {
//                            index = i;
//                            isJump = false;
//                            break;
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            serialChanelBean = serialChanelBeen.get(index);
//
//            String ieSerial = serialChanelBean.getIeSerial();
//            String ieChanel = serialChanelBean.getIeChanel();
//            SystemUtil.println("ieSerial:" + ieSerial + "," + ieChanel);
//
//            findCameraInfo(ieSerial, ieChanel);//找到正确的设备信息
//            resumePealplay();//重新播放新的序列号与通道号
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            dismissLoadingLayout();
//        }
//    }
//
//
//    private static MonitorPage mMonitorPage;
//
//    public static MonitorPage getView() {
//        return mMonitorPage;
//    }
//
//    private MonitorPresenter getPresenter() {
//        return MonitorPresenter.getPresenter();
//    }
//
//    public void getMonitorListSucceed(Map<String, List<SerialChanelBean>> result) {
//        serverReturnMonitorList(result);
//    }
//
//    public void getAccessTokenSucceed() {
//        getCameraInfoList();
//    }
//
//    public void getMonitorListFailure(Throwable t) {
//        dismissLoadingLayout();
//    }
//
//    public void getAccessTokenFailure(Throwable t) {
//        dismissLoadingLayout();
//        ToastUtil.showPrompt("获取监控视频失败");
//    }
//
//    private SparseArray<MonitorOperate> mSparseArray = new SparseArray<>();
//
//    private MonitorOperate getMonitorOperate(SurfaceView surfaceView) {
//        int key = surfaceView.hashCode();
//        MonitorOperate operate = mSparseArray.get(key);
//        if (operate == null) {
//            operate = new MonitorOperate(surfaceView);
//            mSparseArray.put(key, operate);
//        }
//        return operate;
//    }
//
//    private MonitorOperate getMonitorOperate() {
//        return getMonitorOperate(mRealplaySurfaceView);
//    }
//
//    public class MonitorOperate {
//
//        //        private EZPlayer mEZPlayer;
//        private SurfaceView mRealplaySurfaceView = null;
//        private SurfaceHolder mRealplayHolder = null;
//
//        public MonitorOperate(SurfaceView surfaceView) {
//            init(surfaceView);
//        }
//
//        public void init(SurfaceView surfaceView) {
//            mRealplaySurfaceView = surfaceView;
//        }
//
//        /**
//         * 开始播放
//         */
//        public void startRealPlay() {
//            if (mStatus == RealPlayStatus.STATUS_START || isStatusPlay()) {
//                return;
//            }
//            // 检查网络是否可用
//            if (!ConnectionDetector.isNetworkAvailable(mActivity)) {
//                // 提示没有连接网络
//                ToastUtil.showPrompt(R.string.network_disabled);
//                return;
//            }
//
//            mStatus = RealPlayStatus.STATUS_START;
//            setRealPlayLoadingUI();
//
//            if (mCameraInfo != null) {
//                String deviceSerial = mCameraInfo.getDeviceSerial();
//                if (mEZPlayer == null) {
//                    int cameraNo = mCameraInfo.getCameraNo();
//
//                    SystemUtil.println("正在播放：" + mCameraInfo.getCameraName());
//                    dismissLoadingLayout();
//                    mEZPlayer = BaseApplication.getOpenSDK().createPlayer(deviceSerial, cameraNo);
//                }
//
//                if (mEZPlayer == null || mDeviceInfo == null) {
//                    return;
//                }
//                if (mDeviceInfo.getIsEncrypt() == 1) {
//                    String verifyCode = DataManager.getInstance().getDeviceSerialVerifyCode(deviceSerial);
//                    mEZPlayer.setPlayVerifyCode(verifyCode);
//                }
//
//                mEZPlayer.setHandler(mHandler);
//                mEZPlayer.setSurfaceHold(mRealplayHolder);
//                mEZPlayer.startRealPlay();
//            }
//        }
//
//        /**
//         * 停止播放
//         */
//        public void stopRealPlay() {
//            mStatus = RealPlayStatus.STATUS_STOP;
//
//            if (mEZPlayer != null) {
//                mEZPlayer.stopRealPlay();
//            }
//            if (mMonitorBgImageView != null)
//                mMonitorBgImageView.setVisibility(View.VISIBLE);
//            mStreamFlow = 0;
//        }
//
//
//        /**
//         * 设置视频质量
//         */
//        public void setVideoLevel() {
//
//            if (mCameraInfo == null || mEZPlayer == null || mDeviceInfo == null) {
//                return;
//            }
//
//            if (mDeviceInfo.getStatus() == 1) {
//                mRealplayQualityButton.setEnabled(true);
//            } else {
//                mRealplayQualityButton.setEnabled(false);
//            }
//            /************** 本地数据保存 需要更新之前获取到的设备列表信息，开发者自己设置 *********************/
//            mCameraInfo.setVideoLevel(mCurrentQulityMode.getVideoLevel());
//
//            // 视频质量，2-高清，1-标清，0-流畅
//            if (mCurrentQulityMode.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
//                mRealplayQualityButton.setText(R.string.quality_flunet);
//            } else if (mCurrentQulityMode.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
//                mRealplayQualityButton.setText(R.string.quality_balanced);
//            } else if (mCurrentQulityMode.getVideoLevel() == EZConstants.EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
//                mRealplayQualityButton.setText(R.string.quality_hd);
//            }
//        }
//
//        /**
//         * 设置声音
//         */
//        public void setRealplaySound() {
//            if (mEZPlayer != null) {
//
//                if (mLocalInfo.isSoundOpen()) {
//                    mEZPlayer.openSound();
//                } else {
//                    mEZPlayer.closeSound();
//                }
//            }
//        }
//
//        /**
//         * 开始录像
//         */
//        public void startRecord() {
//
//            mControlDisplaySec = 0;
//            if (mIsRecording) {
//                stopRealPlayRecord();
//                return;
//            }
//
//            if (!SDCardUtil.isSDCardUseable()) {
//                // 提示SD卡不可用
//                ToastUtil.showPrompt(R.string.remoteplayback_SDCard_disable_use);
//                return;
//            }
//
//            if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
//                // 提示内存不足
//                ToastUtil.showPrompt(R.string.remoteplayback_record_fail_for_memory);
//                return;
//            }
//
//            if (mEZPlayer != null) {
//                mCaptureDisplaySec = 4;
//
//                mAudioPlayUtil.playAudioFile(AudioPlayUtil.RECORD_SOUND);
//
//                String cameraDir;
//                if (FileUtil.existsSdcard()) {
//                    String dcim = Environment.DIRECTORY_DCIM;
//                    cameraDir = Environment.getExternalStoragePublicDirectory(dcim).getAbsolutePath() + "/Camera";
//                    File cameraDirFile = new File(cameraDir);
//                    if (!cameraDirFile.exists()) {
//                        cameraDirFile.mkdirs();
//                    }
//                } else {
//                    cameraDir = DirectoryUtil.getPrivateDir("Camera");
//                }
//                final String recordPath = cameraDir + "/NTT_"
//                        + mCameraInfo.getDeviceSerial() + "-"
//                        + mCameraInfo.getCameraNo() + "_"
//                        + DateUtil.getToday("yyyyMMdd_HHmmss")
//                        + ".mp4";
//
//                if (mEZPlayer.startLocalRecordWithFile(recordPath)) {
//                    handleRecordSuccess();
//                } else {
//                    handleRecordFail();
//                }
//            }
//        }
//
//        /**
//         * 重新播放新的序列号与通道号
//         */
//        public void resumePealplay() {
//            if (mDeviceInfo == null) {
//                dismissLoadingLayout();
//                TSnackbarUtil.showPrompt(mActivity, "无效序列号");
//                mBackRecordButton.setEnabled(false);
//                return;
//            }
//            onStop();
//            if (mEZPlayer != null) {
//                mEZPlayer.release();
//                mEZPlayer = null;
//            }
//            onResume();
//            if (mCameraInfo != null) {
//                mCurrentQulityMode = (mCameraInfo.getVideoLevel());
////                mCurrentQulityMode = (getMonitorOperate().getVideoLevel());
////                mCurrentQulityMode = ( getVideoLevel());
//            }
//            startRealPlay();
//        }
//
//        /**
//         * 抓拍按钮响应函数（截图）
//         */
//        private void onCapturePicture() {
//            mControlDisplaySec = 0;
//            try {
//                if (!SDCardUtil.isSDCardUseable()) {
//                    // 提示SD卡不可用
//                    ToastUtil.showPrompt(R.string.remoteplayback_SDCard_disable_use);
//                    return;
//                }
//                if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
//                    // 提示内存不足
//                    ToastUtil.showPrompt(R.string.remoteplayback_capture_fail_for_memory);
//                    return;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (mEZPlayer != null) {
//                mCaptureDisplaySec = 4;
//
//                Runnable target = new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Bitmap bmp = mEZPlayer.capturePicture();
//                        if (bmp != null) {
//                            try {
//                                mAudioPlayUtil.playAudioFile(AudioPlayUtil.CAPTURE_SOUND);
//
////                            String dir = Environment.getExternalStorageDirectory().getPath()
////                                    + "/" + mActivity.getPackageName() + "/Pictures";
//
//                                String cameraDir;
//                                if (FileUtil.existsSdcard()) {
//                                    String dcim = Environment.DIRECTORY_DCIM;
//                                    cameraDir = Environment.getExternalStoragePublicDirectory(dcim).getAbsolutePath() + "/Camera";
//                                    File cameraDirFile = new File(cameraDir);
//                                    if (!cameraDirFile.exists()) {
//                                        cameraDirFile.mkdirs();
//                                    }
//                                } else {
//                                    cameraDir = DirectoryUtil.getPrivateDir("Camera");
//                                }
//                                final String path = cameraDir + "/NTT_"
//                                        + mCameraInfo.getDeviceSerial() + "-"
//                                        + mCameraInfo.getCameraNo() + "_"
//                                        + DateUtil.getToday("yyyyMMdd_HHmmss")
//                                        + ".jpg";
//
//                                if (TextUtils.isEmpty(path)) {
//                                    bmp.recycle();
//                                    bmp = null;
//                                    return;
//                                }
//                                EZUtils.saveCapturePictrue(path, bmp);
//
//                                MediaScanner mMediaScanner = new MediaScanner(mActivity);
//                                mMediaScanner.scanFile(path, "jpg");
//
//                                mActivity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        dismissDialog(mShareDialog);
//                                        mShareDialog = new SimpleDialog(mActivity);
//                                        mShareDialog.setLayout(R.layout.dialog_monitor_share)
//                                                .setLayoutParams(-1, -2)
//                                                .setCanceledOnTouchOutside()
//                                                .show();
//                                        Window window = mShareDialog.getWindow();
//                                        if (window != null)
//                                            window.setWindowAnimations(R.style.DialogAnimation);  //添加动画
//
//                                        ImageView iv_share = (ImageView) mShareDialog.findViewById(R.id.iv_share);
//                                        Button btn_share = (Button) mShareDialog.findViewById(R.id.btn_share);
//
//                                        ImageUtil.display(iv_share, path);
//                                        btn_share.setOnClickListener(new OnShareDialogListener(mShareDialog, path));
////                                    String text = mActivity.getString(R.string.already_saved_to_volume) + path;
////                                    ToastUtil.showPrompt(text);
//                                    }
//                                });
//                            } catch (InnerException e) {
//                                e.printStackTrace();
//                            } finally {
//                                if (bmp != null) {
//                                    bmp.recycle();
//                                }
//                            }
//                        }
//                    }
//                };
//
//                new Thread(target).start();
//            }
//        }
//
//        private void setPlayScaleUI(float scale, CustomRect oRect, CustomRect curRect) {
//            if (scale == 1) {
//                if (mPlayScale == scale) {
//                    return;
//                }
//                try {
//                    if (mEZPlayer != null) {
//                        mEZPlayer.setDisplayRegion(false, null, null);
//                    }
//                } catch (BaseException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                if (mPlayScale == scale) {
//                    try {
//                        if (mEZPlayer != null) {
//                            mEZPlayer.setDisplayRegion(true, oRect, curRect);
//                        }
//                    } catch (BaseException e) {
//                        e.printStackTrace();
//                    }
//                    return;
//                }
//                try {
//                    if (mEZPlayer != null) {
//                        mEZPlayer.setDisplayRegion(true, oRect, curRect);
//                    }
//                } catch (BaseException e) {
//                    e.printStackTrace();
//                }
//            }
//            mPlayScale = scale;
//        }
//
//        /**
//         * 处理播放成功的情况
//         */
//        private void handlePlaySuccess(Message msg) {
//            mStatus = RealPlayStatus.STATUS_PLAY;
//
//            // 声音处理
//            setRealplaySound();
//            mRealRatio = Constant.LIVE_VIEW_RATIO;
//
//            initUI();
//
//            setRealPlaySvLayout();
//            setRealPlaySuccessUI();
//
//            if (mEZPlayer != null) {
//                mStreamFlow = mEZPlayer.getStreamFlow();
//            }
//        }
//
//        private void setRealPlayStopUI() {
//            if (mMonitorBgImageView != null)
//                mMonitorBgImageView.setVisibility(View.VISIBLE);
//
//            stopUpdateTimer();
////        updateOrientation();
//            setRealPlaySvLayout();
//            setStopLoading();
//            if (mRealPlayBtn != null)
//                mRealPlayBtn.setImageResource(R.drawable.btn_ez_play_selector);
//            if (mScreenshotsButton != null)
//                mScreenshotsButton.setEnabled(false);
//            if (mRealplayRecordButton != null)
//                mRealplayRecordButton.setEnabled(false);
//            //关闭云台控制
//            if (mPtzControlLy != null)
//                setChildEnabled(mPtzControlLy, false);//是否支持云台控制
//            if (btnPlus != null)
//                btnPlus.setEnabled(false);
//            if (btnSubtract != null)
//                btnSubtract.setEnabled(false);
//            if (mRealPlayFlowTv != null)//不显示网络流量提示
//                mRealPlayFlowTv.setVisibility(View.GONE);
//            if (mHandler != null)  //不显示控制栏
//                mHandler.sendEmptyMessage(MSG_HIDE_CONTROL_BAR);
//        }
//
//        /**
//         * 说话声音结束
//         */
//        private void handleVoiceTalkStoped(boolean startAnim) {
//            if (isStatusPlay()) {
//                if (mEZPlayer != null) {
//                    if (mLocalInfo.isSoundOpen()) {
//                        mEZPlayer.openSound();
//                    } else {
//                        mEZPlayer.closeSound();
//                    }
//                }
//            }
//        }
//
//        /**
//         * 更新流量数据
//         */
//        public long getStreamFlow() {
//            long streamFlow = 0;
//            if (mEZPlayer != null) {
//                streamFlow = mEZPlayer.getStreamFlow();
//            }
//            return streamFlow;
//        }
//
//        public EZPlayer getPlayer() {
//            return mEZPlayer;
//        }
//
//        /**
//         * 获取视频质量
//         */
//        public VideoLevel getVideoLevel() {
//            VideoLevel level = null;
//            EZConstants.EZVideoLevel videoLevel = mCameraInfo.getVideoLevel();
//            switch (videoLevel) {
//                case VIDEO_LEVEL_FLUNET:
//                    level = VideoLevel.VIDEO_LEVEL_FLUNET;
//                    break;
//                case VIDEO_LEVEL_BALANCED:
//                    level = VideoLevel.VIDEO_LEVEL_BALANCED;
//                    break;
//                case VIDEO_LEVEL_HD:
//                    level = VideoLevel.VIDEO_LEVEL_HD;
//                    break;
//                case VIDEO_LEVEL_SUPERCLEAR:
//                    level = VideoLevel.VIDEO_LEVEL_SUPERCLEAR;
//                    break;
//            }
//            return level;
//        }
//
//
//    }
//
//    public static enum VideoLevel {
//        VIDEO_LEVEL_SUPERCLEAR(3),
//        VIDEO_LEVEL_HD(2),
//        VIDEO_LEVEL_BALANCED(1),
//        VIDEO_LEVEL_FLUNET(0);
//
//        private int fM;
//
//        private VideoLevel(int videoLevel) {
//            this.fM = videoLevel;
//        }
//
//        public int getVideoLevel() {
//            return this.fM;
//        }
//    }
//
//
//}
