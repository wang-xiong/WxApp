package com.example.module_common_ui.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.module_common_ui.R;

public class MyDialogFragment3 extends AppCompatDialogFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "desc";

    public static MyDialogFragment3 newInstance(String title, String desc) {
        MyDialogFragment3 fragment = new MyDialogFragment3();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, title);
        bundle.putString(ARG_PARAM2, desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams layoutParams = window.getAttributes();
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.dialog, null);
        TextView titleTv = view.findViewById(R.id.title_tv);
        TextView descTv = view.findViewById(R.id.desc_tv);
        TextView exitTv = view.findViewById(R.id.exit_tv);

        if (getArguments() != null) {
            String title = getArguments().getString(ARG_PARAM1);
            String desc = getArguments().getString(ARG_PARAM2);
            titleTv.setText(title);
            descTv.setText(desc);
        }
        exitTv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.exit_tv) {
            dismiss();
        }
    }
}
