package com.custom.dashboardview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.dashboardview.R;

/**
 * Created by dev on 15/12/24.
 */
public class DashboardView extends RelativeLayout {
    Context context;

    private float mPercent;
    private Dashboard dashboard;
    private TextView dashboardPercent;
    private TextView dashboardDriveRange;
    public DashboardView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.view_dashboard, this);
        dashboard = (Dashboard)this.findViewById(R.id.dashboard);
        dashboardPercent = (TextView)this.findViewById(R.id.dashboard_percent);
        dashboardDriveRange = (TextView)this.findViewById(R.id.dashboard_drive_range);
    }


    public void setPercent(float percent, float mileage) {
        mPercent = percent;
        dashboardDriveRange.setText("" + (int) (mileage * percent));
        dashboardPercent.setText(getPercent(percent));

        dashboard.setPercent(percent);

        invalidate();
    }

    // 保持两位数
    private String getPercent(float percent)
    {
        int tmp = (int)(percent * 100);
        return tmp > 9 ? ("" + tmp) : ("0" + tmp);
    }

}
