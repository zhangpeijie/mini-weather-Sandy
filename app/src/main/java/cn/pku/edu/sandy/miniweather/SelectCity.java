package cn.pku.edu.sandy.miniweather;
import android.content.Intent;
import android.os.Bundle;
import  android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.miniweather.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pku.edu.sandy.app.MyApplication;
import cn.pku.edu.sandy.bean.City;
import cn.pku.edu.sandy.util.pinyin;

public class SelectCity extends Activity implements View.OnClickListener{
    private ImageView mBackBtn;
    private ListView mlistView=null;
    private TextView title_name;
    private MyApplication myApplication;
    private SearchView mSearchView;
    private ArrayList<String> mSearchResult = new ArrayList<>(); //搜索结果，只放城市名
    private Map<String,String> nameToCode = new HashMap<>(); //城市名到编码
    private Map<String,String> nameToPinyin = new HashMap<>(); //城市名到拼音
    private ArrayAdapter<String> arrayAdapter;
    private  String returnCode="101010100"; ;
    @Override
            protected  void onCreate(Bundle savedInstanceState){
             super.onCreate( savedInstanceState );
                setContentView(R.layout.select_city);
                mBackBtn = (ImageView)findViewById(R.id.title_back);
                mBackBtn.setOnClickListener(this);
                title_name =(TextView) findViewById(R.id.title_name);
                mlistView=(ListView)findViewById(R.id.list_view);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String returnCityName=mSearchResult.get(position);
                Toast.makeText(SelectCity.this,"你选择了:"+returnCityName,Toast.LENGTH_SHORT).show();
                returnCode=nameToCode.get(returnCityName);
                title_name.setText("当前城市：" + returnCityName);
             }
        });

        //搜索
        mSearchView=(SearchView)findViewById(R.id.searchView);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(!TextUtils.isEmpty(newText)){
                   if (mSearchResult != null) //清空上次搜索结果
                       mSearchResult.clear();
                    // Toast.makeText(SelectCity.this, "textChange--->" + newText, 1).show();
                     for(String str:nameToPinyin.keySet()){

                         if(str.contains(newText)||nameToPinyin.get(str).contains(newText))
                             mSearchResult.add(str);
                     }
                     arrayAdapter.notifyDataSetChanged();
                }else {
                    mlistView.clearTextFilter();
                }

                return false;
            }
        });
      initView();

    }
    protected void initView() {
        myApplication = MyApplication.getInstance();
        ArrayList<City> mCityList = (ArrayList<City>) myApplication.getCityList();
        String strName;
        String strNamePinyin;
        String strCode;
        for (City city : mCityList) {
            strCode = city.getNumber();
            strName = city.getCity();
            strNamePinyin = pinyin.converterToSpell(strName); //城市名解析成拼音
            nameToCode.put(strName,strCode); //城市名到城市编码
            nameToPinyin.put(strName,strNamePinyin); //城市名到拼音
            mSearchResult.add(strName); //初始状态包含全部城市
        }
        arrayAdapter = new ArrayAdapter<> //新建适配器
                (SelectCity.this, android.R.layout.simple_list_item_1, mSearchResult);
        mlistView.setAdapter(arrayAdapter); //接上适配器
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.title_back:
                Intent i=new Intent();
                i.putExtra("cityCode",returnCode);
                setResult(RESULT_OK,i);
            finish( );
            break;
            default:
                break;
        }
    }
}

