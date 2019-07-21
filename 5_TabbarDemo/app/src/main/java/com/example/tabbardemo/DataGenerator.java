package com.example.tabbardemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DataGenerator {

    public static final int[] mTabRes = new int[]{R.drawable.home,
            R.drawable.product,
            R.drawable.find,
            R.drawable.account};
    public static final int[] mTabResPressed = new int[]{R.drawable.home_selected,
            R.drawable.product_selected,
            R.drawable.find_selected,
            R.drawable.account_selected};
    public static final String[] mTabTitle = new String[]{"首页", "发现", "关注", "我的"};

    public static Fragment[] getFragments(String from) {
        Fragment fragments[] = new Fragment[4];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = DiscoveryFragment.newInstance(from);
        fragments[2] = AttentionFragment.newInstance(from);
        fragments[3] = ProfileFragment.newInstance(from);
        return fragments;
    }

    public static View getTabeView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }


}
