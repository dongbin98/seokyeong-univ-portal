package com.dbsh.skup.tmpstructure.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.TuitionTmpFormBinding;
import com.dbsh.skup.tmpstructure.data.UserData;
import com.dbsh.skup.tmpstructure.model.ResponseTuitionMap;
import com.dbsh.skup.tmpstructure.viewmodels.TuitionViewModel;

public class TuitionActivity extends AppCompatActivity {
	private TuitionTmpFormBinding binding;
	private TuitionViewModel viewModel;

	public UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* DataBinding */
	    binding = DataBindingUtil.setContentView(this, R.layout.tuition_tmp_form);
	    binding.setLifecycleOwner(this);
	    viewModel = new TuitionViewModel();
	    binding.setViewModel(viewModel);
	    binding.executePendingBindings();	// 바인딩 강제 즉시실행

        Toolbar mToolbar = binding.tuitionToolbar;
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

	    userData = ((UserData) getApplication());

        Intent intent = getIntent();
        String token = userData.getToken();
        String id = userData.getId();
        String year = userData.getSchYear();
        String term = userData.getSchTerm();

		binding.tuitionTitle.setText(year + "년 " + term + "학기 등록금");

		viewModel.tuitionLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseTuitionMap>() {
			@Override
			public void onChanged(ResponseTuitionMap responseTuitionMap) {
				binding.tuitionEntfee.setText(responseTuitionMap.getEntFee());
				binding.tuitionLesnfee.setText(responseTuitionMap.getLesnFee());
				binding.tuitionCoopdegree.setText(responseTuitionMap.getCoopDgrAmt());
				binding.tuitionUssentfee.setText(responseTuitionMap.getUssEntFee());
				binding.tuitionUsslesnfee.setText(responseTuitionMap.getUssLesnFee());
				binding.tuitionSclstot.setText(responseTuitionMap.getSclsTot());
				binding.tuitionTotamt.setText(responseTuitionMap.getTotAmt());
				binding.tuitionRegamt.setText(responseTuitionMap.getRegAmt());
				binding.tuitionTmpacct.setText(responseTuitionMap.getTempAcct());
				if (responseTuitionMap.getNonPay().equals("0")) {
					binding.tuitionBackground.setImageResource(R.drawable.tuition_background);
					binding.tuitionCircle.setImageResource(R.drawable.tuition_paid_circle);
					binding.tuitionCircleText.setText("납부 완료");
				} else {
					binding.tuitionBackground.setImageResource(R.drawable.tuition_none_paid_background);
					binding.tuitionCircle.setImageResource(R.drawable.tuition_none_paid_circle);
					binding.tuitionCircleText.setText("납부 미완료");
				}
			}
		});

	    viewModel.getTuition(token, id, year, term);

	    binding.tuitionPaste.setOnClickListener(new View.OnClickListener() {    // 가상계좌 복사버튼 이벤트
		    @Override
		    public void onClick(View view) {
			    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			    ClipData clip = ClipData.newPlainText("sex", binding.tuitionTmpacct.getText());
			    clipboard.setPrimaryClip(clip);
			    Toast.makeText(TuitionActivity.this, "복사되었습니다.", Toast.LENGTH_SHORT).show();
		    }
	    });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
