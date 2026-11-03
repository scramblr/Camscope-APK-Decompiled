package com.joyhonest.wifi_check_adj;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class MyDialog extends Dialog {
    private TextView message;
    private String messageStr;
    private View midView;
    private Button no;
    private onClickListener noOnclickListener;
    private String noStr;
    private String titleStr;
    private TextView titleTV;
    private Button yes;
    private onClickListener yesOnclickListener;
    private String yesStr;

    public interface onClickListener {
        void onClick();
    }

    public MyDialog(Context context, int i) {
        super(context, i);
    }

    public void setNoOnclickListener(String str, onClickListener onclicklistener) {
        if (str != null) {
            this.noStr = str;
        }
        this.noOnclickListener = onclicklistener;
    }

    public void setYesOnclickListener(String str, onClickListener onclicklistener) {
        if (str != null) {
            this.yesStr = str;
        }
        this.yesOnclickListener = onclicklistener;
    }

    @Override // android.app.Dialog
    public void show() {
        Window window = getWindow();
        NavigationBarUtil.focusNotAle(window);
        super.show();
        NavigationBarUtil.hideNavigationBar(window);
        NavigationBarUtil.clearFocusNotAle(window);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        this.yes = (Button) findViewById(R.id.yes);
        this.no = (Button) findViewById(R.id.no);
        this.titleTV = (TextView) findViewById(R.id.title);
        this.message = (TextView) findViewById(R.id.message);
        this.midView = findViewById(R.id.midView);
        String str = this.yesStr;
        boolean z = str != null && str.length() > 0;
        String str2 = this.noStr;
        boolean z2 = str2 != null && str2.length() > 0;
        if (z && z2) {
            this.midView.setVisibility(0);
            this.yes.setVisibility(0);
            this.no.setVisibility(0);
        } else if (z) {
            this.midView.setVisibility(8);
            this.yes.setVisibility(0);
            this.no.setVisibility(8);
        } else if (z2) {
            this.midView.setVisibility(8);
            this.yes.setVisibility(8);
            this.no.setVisibility(0);
        } else {
            this.midView.setVisibility(8);
            this.yes.setVisibility(8);
            this.no.setVisibility(8);
        }
    }

    private void initData() {
        String str = this.titleStr;
        if (str != null) {
            this.titleTV.setText(str);
        }
        String str2 = this.messageStr;
        if (str2 != null) {
            this.message.setText(str2);
        }
        String str3 = this.yesStr;
        if (str3 != null) {
            this.yes.setText(str3);
        }
        String str4 = this.noStr;
        if (str4 != null) {
            this.no.setText(str4);
        }
    }

    private void initEvent() {
        this.yes.setOnClickListener(new View.OnClickListener() { // from class: com.joyhonest.wifi_check_adj.MyDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m170lambda$initEvent$0$comjoyhonestwifi_check_adjMyDialog(view);
            }
        });
        this.no.setOnClickListener(new View.OnClickListener() { // from class: com.joyhonest.wifi_check_adj.MyDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m171lambda$initEvent$1$comjoyhonestwifi_check_adjMyDialog(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initEvent$0$com-joyhonest-wifi_check_adj-MyDialog, reason: not valid java name */
    /* synthetic */ void m170lambda$initEvent$0$comjoyhonestwifi_check_adjMyDialog(View view) {
        onClickListener onclicklistener = this.yesOnclickListener;
        if (onclicklistener != null) {
            onclicklistener.onClick();
        }
    }

    /* JADX INFO: renamed from: lambda$initEvent$1$com-joyhonest-wifi_check_adj-MyDialog, reason: not valid java name */
    /* synthetic */ void m171lambda$initEvent$1$comjoyhonestwifi_check_adjMyDialog(View view) {
        onClickListener onclicklistener = this.noOnclickListener;
        if (onclicklistener != null) {
            onclicklistener.onClick();
        }
    }

    public void setTitle(String str) {
        this.titleStr = str;
    }

    @Override // android.app.Dialog
    public void setTitle(int i) {
        this.titleStr = getContext().getString(i);
    }

    public void setMessage(String str) {
        this.messageStr = str;
    }

    public void setMessage(int i) {
        this.messageStr = getContext().getString(i);
    }
}
