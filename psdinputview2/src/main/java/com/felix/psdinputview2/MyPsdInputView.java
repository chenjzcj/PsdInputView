package com.felix.psdinputview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by Felix.Zhong on 2018/10/11 11:34
 * 密码输入控件
 */
public class MyPsdInputView extends RelativeLayout {

    public MyPsdInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 密码长度
     */
    public static int PSD_LENGHT = 6;
    private Runnable task;
    private StringBuilder psd = new StringBuilder();

    public void setTask(Runnable task) {
        this.task = task;
    }

    public String getPsd() {
        return psd.toString();
    }

    private int mLineColor = Color.WHITE;
    private int mTextColor = getContext().getResources().getColor(R.color.color_eee);
    /**
     * 单位默认为sp
     */
    private float mTextSize = 18;

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.layout_psd_input, this);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyPsdInputView, 0, 0);

        mLineColor = a.getColor(R.styleable.MyPsdInputView_lineColor, mLineColor);
        mTextColor = a.getColor(R.styleable.MyPsdInputView_textColor, mTextColor);
        //因为getDimension方法会自动将sp转成dp，所以再将其转成sp
        mTextSize = a.getDimension(R.styleable.MyPsdInputView_textSize, mTextSize);
        mTextSize = px2sp(mTextSize);

        a.recycle();

        setLineColor();

        EditText etPsd1 = findViewById(R.id.et_psd_1);
        EditText etPsd2 = findViewById(R.id.et_psd_2);
        EditText etPsd3 = findViewById(R.id.et_psd_3);
        EditText etPsd4 = findViewById(R.id.et_psd_4);
        EditText etPsd5 = findViewById(R.id.et_psd_5);
        EditText etPsd6 = findViewById(R.id.et_psd_6);

        initEditText(etPsd1);
        initEditText(etPsd2);
        initEditText(etPsd3);
        initEditText(etPsd4);
        initEditText(etPsd5);
        initEditText(etPsd6);

        setOnFocusChangeListener(etPsd1, etPsd2, null);
        setOnFocusChangeListener(etPsd2, etPsd3, etPsd1);
        setOnFocusChangeListener(etPsd3, etPsd4, etPsd2);
        setOnFocusChangeListener(etPsd4, etPsd5, etPsd3);
        setOnFocusChangeListener(etPsd5, etPsd6, etPsd4);
        setOnFocusChangeListener(etPsd6, null, etPsd5);

        addTextChangedListener(etPsd1, etPsd2, null);
        addTextChangedListener(etPsd2, etPsd3, etPsd1);
        addTextChangedListener(etPsd3, etPsd4, etPsd2);
        addTextChangedListener(etPsd4, etPsd5, etPsd3);
        addTextChangedListener(etPsd5, etPsd6, etPsd4);
        addTextChangedListener(etPsd6, null, etPsd5);

        setOnKeyListener(etPsd1, etPsd2, null);
        setOnKeyListener(etPsd2, etPsd3, etPsd1);
        setOnKeyListener(etPsd3, etPsd4, etPsd2);
        setOnKeyListener(etPsd4, etPsd5, etPsd3);
        setOnKeyListener(etPsd5, etPsd6, etPsd4);
        setOnKeyListener(etPsd6, null, etPsd5);
    }

    private void initEditText(EditText editText) {
        editText.setTextColor(mTextColor);
        editText.setTextSize(mTextSize);
    }

    public int px2sp(final float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private void setLineColor() {
        findViewById(R.id.line1).setBackgroundColor(mLineColor);
        findViewById(R.id.line2).setBackgroundColor(mLineColor);
        findViewById(R.id.line3).setBackgroundColor(mLineColor);
        findViewById(R.id.line4).setBackgroundColor(mLineColor);
        findViewById(R.id.line5).setBackgroundColor(mLineColor);
        findViewById(R.id.line6).setBackgroundColor(mLineColor);
    }

    /**
     * 设置焦点变化监听
     *
     * @param curEt  当前EditText
     * @param nextEt 后一个EditText
     * @param preEt  前一个EditText
     */
    private void addTextChangedListener(final EditText curEt, final EditText nextEt, final EditText preEt) {
        curEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    psd.append(s);
                    if (nextEt != null) {
                        nextEt.requestFocus();
                    } else {
                        if (psd.length() == PSD_LENGHT) {
                            task.run();
                        }
                    }
                } else {
                    psd.deleteCharAt(psd.length() - 1);
                }
            }
        });
    }

    /**
     * 设置文件内容监听
     *
     * @param curEt  当前EditText
     * @param preEt  前一个EditText
     * @param nextEt 后一个EditText
     */
    private void setOnFocusChangeListener(final EditText curEt, final EditText nextEt, final EditText preEt) {
        curEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (preEt != null) {
                        String preStr = preEt.getText().toString();
                        if (TextUtils.isEmpty(preStr)) {
                            preEt.requestFocus();
                        }
                    }
                    if (nextEt != null) {
                        String nextStr = nextEt.getText().toString();
                        if (!TextUtils.isEmpty(nextStr)) {
                            nextEt.requestFocus();
                        }
                    }
                }
            }
        });
    }

    String curContent = null;

    /**
     * Android EditText 监听软键盘删除键
     * https://www.cnblogs.com/nmj1986/archive/2013/06/15/3137448.html
     *
     * @param curEt  当前EditText
     * @param preEt  前一个EditText
     * @param nextEt 后一个EditText
     */
    private void setOnKeyListener(final EditText curEt, final EditText nextEt, final EditText preEt) {

        curEt.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //setOnKeyListener中onKey执行两次问题解决:https://blog.csdn.net/rongwenbin/article/details/51396241?utm_source=blogxgwz5
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    curContent = curEt.getText().toString();
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    //If the delete key is pressed and the content is empty, jump to the previous EditText
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (TextUtils.isEmpty(curContent)) {
                            if (preEt != null) {
                                preEt.setText("");
                                preEt.requestFocus();
                            }
                        }
                        curContent = null;
                    }
                }
                return false;
            }
        });
    }
}
