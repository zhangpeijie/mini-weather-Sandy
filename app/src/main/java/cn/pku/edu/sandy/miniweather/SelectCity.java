package cn.pku.edu.sandy.miniweather;
import android.os.Bundle;
import  android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import com.example.sandy.miniweather.R;


public class SelectCity extends Activity implements View.OnClickListener{
    private ImageView mBackBtn;
    @Override
            protected  void onCreate(Bundle savedInstanceState){
             super.onCreate( savedInstanceState );
                setContentView(R.layout.select_city);
                mBackBtn = (ImageView)findViewById(R.id.title_back);
                mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
            finish( );
            break;
            default:
                break;
        }
    }
}

