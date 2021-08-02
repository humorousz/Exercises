package com.humorusz.practice.textSpan;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.QuoteSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorusz.practice.R;

/**
 * Created by zhangzhiquan on 2017/9/4.
 */

public class TextSpanFragment extends BaseFragment {
    private static final String TAG = "TextSpanFragment";
    private TextView mText,mClickSpanText;
    private ImageView mImageView;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_text_span,container,false);
    }

    @Override
    public void initView(View root) {
        mText = (TextView) root.findViewById(R.id.span_text);
        mImageView = (ImageView) root.findViewById(R.id.span_imageView);
        mText.post(new Runnable() {
            @Override
            public void run() {
                setSpan();
                setClickSpan();
            }
        });
        mClickSpanText = (TextView)root.findViewById(R.id.click_span_text);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setSpan(){
        mText.setText(getQuoteSpan());
        mText.setText(getTextRoundSpan());
        mText.setText(getAligmentSpan());
        mText.setText(getImageSpan());
    }

    private CharSequence getQuoteSpan(){
        QuoteSpan bulletSpan = new QuoteSpan();
        SpannableString spannableString = new SpannableString(mText.getText());
        spannableString.setSpan(bulletSpan,0,mText.getText().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return  spannableString;
    }

    private CharSequence getTextRoundSpan(){
        int finalHeight = mImageView.getHeight();
        int finalWidth  = mImageView.getWidth();
        float fontSpacing = mText.getPaint().getFontSpacing();
        int lines = (int) (finalHeight / fontSpacing);
        TextRoundSpan span = new TextRoundSpan(finalWidth+20,lines);
        SpannableString spannableString = new SpannableString(mText.getText());
        spannableString.setSpan(span,0,mText.getText().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return  spannableString;

    }

    private  CharSequence getAligmentSpan(){
        AlignmentSpan span = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        SpannableString spannableString = new SpannableString(mText.getText());
        spannableString.setSpan(span,0,mText.getText().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return  spannableString;
    }

    private CharSequence getImageSpan(){
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_pointer);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
        SpannableString spannableString = new SpannableString(mText.getText());
        spannableString.setSpan(span,5,10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return  spannableString;
    }

    private void setClickSpan(){
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for(int i=0;i<8;i++){
            if(i > 0)
                ssb.append(",");
            ssb.append(makeClickSpan("这是第"+i+"句话"));
        }
        mClickSpanText.setMovementMethod(PraiseMovementMethod.getInstance(Color.BLUE,Color.TRANSPARENT));
        mClickSpanText.setText(ssb);
    }


    private CharSequence makeClickSpan(final String string){
        PraiseClickSpan span = new PraiseClickSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtil.showToast(getContext(),string);
            }
        };
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(span,0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    class TextRoundSpan implements LeadingMarginSpan.LeadingMarginSpan2{
        private int margin;
        private int lines;
        TextRoundSpan(int margin,int lines){
            this.margin = margin;
            this.lines = lines;
        }
        @Override
        public int getLeadingMarginLineCount() {
            return lines;
        }

        @Override
        public int getLeadingMargin(boolean first) {
            if(first){
                return margin;
            }
            return 0;
        }

        @Override
        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

        }
    }
}
