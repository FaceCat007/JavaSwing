package choose;

import java.util.*;
import java.util.Calendar;

import facecat.topin.core.*;
import facecat.topin.date.*;
import facecat.topin.swing.*;
import facecat.topin.service.FCClientService;

/*
* 平均涨跌幅
*/
public class Choose2 extends FCView {
    /*
    * 秒表ID
    */
    private int m_timerID = FCView.getNewTimerID();

    /*
    * 构造函数
    */
    public Choose2()
    {
        setBackColor(FCColor.Back);
    }

    /*
    * 日历
    */
    public FCCalendarEx2 m_calendarEx;

    /*
    * 添加控件方法
    */
    public void onAdd()
    {
        super.onAdd();
        if(m_globalServide == null){
            m_globalServide = new SGlobalService();
            FCClientService.addService(m_globalServide);
        }
        startTimer(m_timerID, 10);
        setBorderColor(FCColor.None);

        m_calendarEx = new FCCalendarEx2();
        m_calendarEx.m_mainFrame = this;
        m_calendarEx.setUseAnimation(true);
        m_calendarEx.setDock(FCDockStyle.Fill);
        //m_calendarEx.setMargin(padding);
        addView(m_calendarEx);
        m_calendarEx.getTimeDiv().setHeight(0);
        //calendar.setTopMost(true);
        m_calendarEx.getHeadDiv().setFont(new FCFont("Default", 12, false, false, false));
        DayDiv dayDiv = m_calendarEx.getDayDiv();
        dayDiv.m_dayButtons.clear();
        dayDiv.m_dayButtons_am.clear();
        for (int i = 0; i < 42; i++)
        {
            DayButtonEx2 dayButton = new DayButtonEx2(m_calendarEx);
            dayDiv.m_dayButtons.add(dayButton);
            DayButtonEx2 dayButtonAm = new DayButtonEx2(m_calendarEx);
            dayButtonAm.setVisible(false);
            dayDiv.m_dayButtons_am.add(dayButtonAm);
        }

        //Thread tThread = new Thread(new ThreadStart(doTime));
        //tThread.Start();
        new Thread(runnable).start();
        while (!m_isLoaded){
            try{
                Thread.sleep(10);
            }catch (Exception ex){

            }
        }
        m_myChartDiv = new MyChartDiv();
        m_myChartDiv.setDock(FCDockStyle.Fill);
        m_myChartDiv.m_mainFrame = this;
        m_myChartDiv.setVisible(false);
        addView(m_myChartDiv);
        Calendar calendar = Calendar.getInstance();
        try {
            m_calendarEx.setSelectedDay(new CDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        }catch (Exception ex){

        }
        m_calendarEx.update();
    }

    /*
    * 是否加载
    */
    public boolean m_isLoaded;

    /*
    * 检查数据
    */
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doTime();
        }
    };

    /*
    * K线
    */
    public MyChartDiv m_myChartDiv = null;

    /*
    * 涨跌数据
    */
    public ArrayList<UpDownInfo> m_upDownInfos = new ArrayList<UpDownInfo>();

    /*
    * 涨跌数据键值对
    */
    public HashMap<Double, UpDownInfo> m_upDownInfos2 = new HashMap<Double, UpDownInfo>();

    /*
    * 是否显示底部按钮
    */
    private boolean m_showBottomButtons = false;

    /*
    * 超时
    */
    private int m_timeOut = 10000;

    /*
    * 新的涨跌数据
    */
    private ArrayList<UpDownInfo> m_newUpDownInfos = new ArrayList<UpDownInfo>();
    
    /*
    * 数据服务
    */
    public SGlobalService m_globalServide;
    
    /*
    * 请求数据
    */
    private void doTime()
    {
        ArrayList<String> filters = new ArrayList<String>();
        m_globalServide.getUpDownInfo(filters, 0, m_upDownInfos);
        for (int i = 0; i < m_upDownInfos.size(); i++)
        {
            m_upDownInfos2.put(m_upDownInfos.get(i).m_date, m_upDownInfos.get(i));
        }
        m_isLoaded = true;
        while (!isDeleted())
        {
            try {
                Thread.sleep(m_timeOut);
            }catch (Exception ex){

            }
            synchronized (m_newUpDownInfos)
            {
                ArrayList<String> myCodes = new ArrayList<String>();
                m_newUpDownInfos.clear();
                m_globalServide.getUpDownInfo(myCodes, 1, m_newUpDownInfos);
            }
        }
    }

    /*
    * 秒表事件
    * timerID 秒表ID
    */
    public void onTimer(int timerID)
    {
        super.onTimer(timerID);
        try {
            boolean paint = false;
            ArrayList<UpDownInfo> newDatas = new ArrayList<UpDownInfo>();
            synchronized (m_newUpDownInfos) {
                if (m_newUpDownInfos.size() > 0) {
                    for (int i = 0; i < m_newUpDownInfos.size(); i++) {
                        newDatas.add(m_newUpDownInfos.get(i));
                    }
                    m_newUpDownInfos.clear();
                }
            }
            if (newDatas.size() > 0) {
                if (m_upDownInfos.size() == 0) {
                    for (int i = 0; i < newDatas.size(); i++) {
                        m_upDownInfos.add(newDatas.get(i));
                        m_upDownInfos2.put(newDatas.get(i).m_date, newDatas.get(i));
                    }
                } else {
                    for (int i = 0; i < newDatas.size(); i++) {
                        if (m_upDownInfos.get(m_upDownInfos.size() - 1).m_date != newDatas.get(i).m_date) {
                            m_upDownInfos.add(newDatas.get(i));
                        } else {
                            m_upDownInfos.set(m_upDownInfos.size() - 1, newDatas.get(i));
                        }
                        m_upDownInfos2.put(newDatas.get(i).m_date, newDatas.get(i));
                    }
                }
                paint = true;
                newDatas.clear();
            }
            if (paint) {
                invalidate();
            }
        }catch (Exception ex){

        }
    }

    /*
    * 鼠标移动事件
    * touchInfo 触摸信息
    */
    public void onTouchMove(FCTouchInfo touchInfo)
    {
        super.onTouchMove(touchInfo);
        invalidate();
    }

    /*
    * 绘制边线
    * paint 绘图对象
    * clipRect 裁剪区域
    */
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth(), height = getHeight();
        FCRect cornerRect = new FCRect(width - 30,  10, width - 10, 30);
        paint.fillEllipse(MyColor.USERCOLOR2, cornerRect);
    }
}