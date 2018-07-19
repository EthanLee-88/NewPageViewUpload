package com.example.jiangnan.newpageview.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.jiangnan.newpageview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Fragment> list;
    private AHBottomNavigation ahBottomNavigation;
    private List<AHBottomNavigationItem> itemsList;
    private AHBottomNavigationAdapter ahBottomNavigationAdapter;

    /********************************************
     * 百度语音识别与合成
     *   APPID : 11117429
     *   API Key : skNlxw9PBaBE9z6zoVMtrT3q
     *   Secret Key : AQV2CwmYgPlpOGfsPM5Nd8KgDC5fE232
     * ******************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRes();
    }
    private void initRes(){
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.page_select);
        createFragment();
    }
    private void createFragment(){
        FragmentOneHorizontalScroView fragmentOne = new FragmentOneHorizontalScroView();
        FragmentTwoBlueTooth fragmentTwo = new FragmentTwoBlueTooth();
        FragmentThreeRxJava fragmentThree = new FragmentThreeRxJava();
        FragmentFour fragmentFour = new FragmentFour();

        list = new ArrayList<Fragment>();
        list.add(fragmentOne);
        list.add(fragmentTwo);
        list.add(fragmentThree);
        list.add(fragmentFour);

        int[] str = {R.color.blue,R.color.green,R.color.blue , R.color.green};

        ahBottomNavigationAdapter = new AHBottomNavigationAdapter(this , R.menu.ah_navigation_page);
        ahBottomNavigationAdapter.setupWithBottomNavigation(ahBottomNavigation);

        ahBottomNavigation.setBehaviorTranslationEnabled(false);

        ahBottomNavigation.setAccentColor(Color.parseColor("#008cff"));

        ahBottomNavigation.setForceTitlesDisplay(true);

        setAdapter();
    }

    private void setAdapter(){
        viewPager.setAdapter(new mFragmentAdapter(getSupportFragmentManager() ,list));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ahBottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Log.d("MainActivity" , "position" + position);
                viewPager.setCurrentItem(position);

                String str1 = "javajianhj";
                String str2 = "全为汉字";
                String reg = "[\\u4e00-\\u9fa5]";
                Pattern p = Pattern.compile(reg);
//                Matcher m = p.matcher(str1);
//                Matcher m2 = p.matcher(str2);
                boolean result1 = str1.matches(reg);
                boolean result2 = str2.matches(reg);

                char c = 'a';
                if (true) Log.d("MainActivity" , "是否汉字=" + isChinese(c));
                if (true) Log.d("MainActivity" , "是否英文字母=" + isEnglish(c));

                return true;
            }
        });
    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
    public static boolean isEnglish(char c) {
        return c>='a'&&c<='z'||c>='A'&&c<='Z';// 根据字节码判断
    }

    public class mFragmentAdapter extends FragmentPagerAdapter{
        List<Fragment> list ;

        public mFragmentAdapter(FragmentManager fragmentManager){
           super(fragmentManager);
        }
        public mFragmentAdapter(FragmentManager fragmentManager , List<Fragment> list){
             super(fragmentManager);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
