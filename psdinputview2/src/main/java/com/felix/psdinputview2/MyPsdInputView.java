package com.felix.psdinputview2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
        init(context);
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

    private void init(Context context) {
        inflate(context, R.layout.layout_psd_input, this);

        EditText etPsd1 = findViewById(R.id.et_psd_1);
        EditText etPsd2 = findViewById(R.id.et_psd_2);
        EditText etPsd3 = findViewById(R.id.et_psd_3);
        EditText etPsd4 = findViewById(R.id.et_psd_4);
        EditText etPsd5 = findViewById(R.id.et_psd_5);
        EditText etPsd6 = findViewById(R.id.et_psd_6);

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
    }

    /**
     * 设置焦点变化监听
     *
     * @param curEt  当前EditText
     * @param nextEt 后一个EditText
     * @param preEt  前一个EditText
     */
    private void addTextChangedListener(final EditText curEt, final EditText nextEt, final EditText preEt) {
        curEt.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() == 1) {
                    psd.append(s.toString());
                    if (nextEt != null) {
                        nextEt.requestFocus();
                    } else {
                        if (psd.length() == PSD_LENGHT) {
                            task.run();
                            psd.setLength(0);
                        }
                    }
                } else {
                    if (preEt != null) {
                        preEt.requestFocus();
                    }
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
    private void setOnFocusChangeListener(EditText curEt, final EditText nextEt, final EditText preEt) {
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

    class TextWatcherImpl implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
