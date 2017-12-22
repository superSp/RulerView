package rulerview.lsp.com.rulerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lsp.RulerView;


public class MainActivity extends AppCompatActivity {
    private RulerView rulerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rulerView = (RulerView) findViewById(R.id.rulerView);

        rulerView.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {

            }
        });
    }

    public void computeScroll(View view) {
        rulerView.computeScrollTo(Float.parseFloat(((EditText) findViewById(R.id.edt)).getText().toString()));
    }
}
