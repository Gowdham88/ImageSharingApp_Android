package com.numnu.android.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.support.v7.widget.AppCompatTextView;

/*
    Created By Rajesh.K on 25-Oct-2017
 */

public class ExpandableTextView extends AppCompatTextView {

    private final String readMoreText = " ...more";
    private final String readLessText = " ...less";
    private final int readMoreColor = Color.parseColor("#4A90E2");
    private Boolean isExpanded = false;

    private int _maxLines = 4;
    private CharSequence originalText;

    public ExpandableTextView(Context context) {
        super(context);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        truncateText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

        if (originalText == null) {
            originalText = text;
        }
    }

    @Override
    public int getMaxLines() {
        return _maxLines;
    }

    @Override
    public void setMaxLines(int maxLines) {
        _maxLines = maxLines;
    }

    /**
     * This method can be called to truncate the text
     */
    public void truncateText() {

        super.setMaxLines(_maxLines);
        isExpanded = false;

        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);


                int maxLines = _maxLines;

                if (getLineCount() >= maxLines) {

                    int lineEndIndex = getLayout().getLineEnd(maxLines - 1);

                    String truncatedText = getText().subSequence(0, lineEndIndex - readMoreText.length() + 1) + readMoreText;

                    Spannable spannable = new SpannableString(truncatedText);
                    spannable.setSpan(new ForegroundColorSpan(readMoreColor), truncatedText.length() - readMoreText.length(), truncatedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    setText(spannable, AppCompatTextView.BufferType.SPANNABLE);

                }

            }
        });


    }

    /**
     * This method can be called to expand the text
     */
    public void expandText() {

        setText(originalText);
        super.setMaxLines(1000);
        isExpanded = true;
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);

                int lineEndIndex = getLayout().getLineEnd(getLineCount() - 1);
                String truncatedText = getText().subSequence(0, lineEndIndex) + readLessText;

                Spannable spannable = new SpannableString(truncatedText);
                spannable.setSpan(new ForegroundColorSpan(readMoreColor), truncatedText.length() - readLessText.length(), truncatedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                setText(spannable, AppCompatTextView.BufferType.SPANNABLE);
            }
        });

    }

    public void reset() {
        originalText = null;
    }

    public Boolean isExpanded() {
        return isExpanded;
    }
}