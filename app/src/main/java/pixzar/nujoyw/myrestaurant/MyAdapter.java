package pixzar.nujoyw.myrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by SCIENCE on 9/4/2015.
 */
public class MyAdapter extends BaseAdapter{//ต้องการทำให้มันเก่ง
    //เชื่อมต่อ
    private Context objContext;
    private String[] iconStrings, foodStrings, priceStrings;

    //String ของ รูปภาพ รายชื่อ และ ราคา
    public MyAdapter(Context objContext, String[] iconStrings, String[] foodStrings, String[] priceStrings) {
        this.objContext = objContext;
        iconStrings = iconStrings;
        this.foodStrings = foodStrings;
        this.priceStrings = priceStrings;
    } //constructor


    @Override
    public int getCount() {
        return iconStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = objLayoutInflater.inflate(R.layout.food_listview, ViewGroup, false);//จะไดร์ฟวัตถุ 3 ตัวไปยัง layout ตัวไหน เราจะไปที่ listView
        //Show Icon
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imvIcon);
        

        return null;
    }
}//Main Class
