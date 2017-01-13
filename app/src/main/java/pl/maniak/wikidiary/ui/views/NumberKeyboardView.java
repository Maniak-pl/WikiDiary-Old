package pl.maniak.wikidiary.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.maniak.wikidiary.R;

/**
 * Created by Sony on 2016-01-17.
 */
public class NumberKeyboardView extends LinearLayout implements View.OnClickListener {

    private static final int MAX_NUMBER_COUNT = 8;

     TextView inputText;
    ImageView numberBtnAccept;
    ImageView numberBtnDel;
    ImageView numberBtn0;
    ImageView numberBtn1;
    ImageView numberBtn2;
    ImageView numberBtn3;
    ImageView numberBtn4;
    ImageView numberBtn5;
    ImageView numberBtn6;
    ImageView numberBtn7;
    ImageView numberBtn8;
    ImageView numberBtn9;

    private String numberText = "";

    private NumberKeyboardEventListener listener = null;

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_number_keyboard, this, true);

        inputText = (TextView) view.findViewById(R.id.inputText);

        numberBtnAccept = (ImageView) view.findViewById(R.id.numberKeyAccept);
        numberBtnDel = (ImageView) view.findViewById(R.id.numberKeyDel);
        numberBtn0 = (ImageView) view.findViewById(R.id.numberKey0);
        numberBtn1 = (ImageView) view.findViewById(R.id.numberKey1);
        numberBtn2 = (ImageView) view.findViewById(R.id.numberKey2);
        numberBtn3 = (ImageView) view.findViewById(R.id.numberKey3);
        numberBtn4 = (ImageView) view.findViewById(R.id.numberKey4);
        numberBtn5 = (ImageView) view.findViewById(R.id.numberKey5);
        numberBtn6 = (ImageView) view.findViewById(R.id.numberKey6);
        numberBtn7 = (ImageView) view.findViewById(R.id.numberKey7);
        numberBtn8 = (ImageView) view.findViewById(R.id.numberKey8);
        numberBtn9 = (ImageView) view.findViewById(R.id.numberKey9);

        numberBtnAccept.setOnClickListener(this);
        numberBtnDel.setOnClickListener(this);
        numberBtn0.setOnClickListener(this);
        numberBtn1.setOnClickListener(this);
        numberBtn2.setOnClickListener(this);
        numberBtn3.setOnClickListener(this);
        numberBtn4.setOnClickListener(this);
        numberBtn5.setOnClickListener(this);
        numberBtn6.setOnClickListener(this);
        numberBtn7.setOnClickListener(this);
        numberBtn8.setOnClickListener(this);
        numberBtn9.setOnClickListener(this);

        initView();
    }

    public NumberKeyboardEventListener getListener() {
        return listener;
    }

    public void setListener(NumberKeyboardEventListener listener) {
        this.listener = listener;
    }

    public String getNumberText() {
        return numberText;
    }

    private void initView() {

        numberBtnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeAllDigits();
                return true;
            }
        });
    }

    private void appendNumberToRight(int digit) {
        if (MAX_NUMBER_COUNT == numberText.length()) {
            if (null != listener) {
                listener.onMaxNumberReached();
            }

            return;
        }

        numberText = numberText.concat(String.valueOf(digit));

        if (null != listener) {
            listener.onNumberTextChanged(numberText);
        }

        if (numberText.length() < MAX_NUMBER_COUNT) {

        } else if (numberText.length() == MAX_NUMBER_COUNT) {

            if (null != listener) {

                listener.onMaxNumberReached();
            }
        }

        inputText.setText(getNumberText());
    }

    private void removeOneDigit() {
        if (numberText.length() > 0) {
            numberText = numberText.substring(0, numberText.length() - 1);

        }
        inputText.setText(getNumberText());
    }

    private void removeAllDigits() {
        numberText = "";
        inputText.setText(getNumberText());
    }


    public void reset() {
        this.numberText = "";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numberKey0:
                appendNumberToRight(0);
                break;
            case R.id.numberKey1:
                appendNumberToRight(1);
                break;
            case R.id.numberKey2:
                appendNumberToRight(2);
                break;
            case R.id.numberKey3:
                appendNumberToRight(3);
                break;
            case R.id.numberKey4:
                appendNumberToRight(4);
                break;
            case R.id.numberKey5:
                appendNumberToRight(5);
                break;
            case R.id.numberKey6:
                appendNumberToRight(6);
                break;
            case R.id.numberKey7:
                appendNumberToRight(7);
                break;
            case R.id.numberKey8:
                appendNumberToRight(8);
                break;
            case R.id.numberKey9:
                appendNumberToRight(9);
                break;
            case R.id.numberKeyAccept:
                if (null != listener) {
                    listener.onAcceptBtnClicked();
                }
                break;
            case R.id.numberKeyDel:
                removeOneDigit();
                break;
            default:
                break;
        }
    }


    public interface NumberKeyboardEventListener {
        void onNumberTextChanged(String numberString);

        void onMaxNumberReached();

        void onAcceptBtnClicked();

        void onNumberKeyboardReset();
    }
}