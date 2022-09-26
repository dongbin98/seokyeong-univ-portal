package com.dbsh.skup.tuition;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dbsh.skup.HttpUrlConnector;
import com.dbsh.skup.R;
import com.dbsh.skup.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class TuitionActivity extends AppCompatActivity {
    private static final String tuitionURL = "https://sportal.skuniv.ac.kr/sportal/common/selectOne.sku";

    ImageView view;         // 백그라운드
    TextView divide_text;   // 납부 완료 OR 납부 미완료
    ImageView circle;       // 납부 완료 미완료 동그라미

    TextView title;         // 제목
    TextView entFee;        // 입학금
    String entFeeStr;
    TextView lesnFee;       // 수업료
    String lesnFeeStr;
    TextView coopDegree;    // 공동학위
    String coopDegreeStr;
    TextView ussEntFee;     // 장학입학금
    String ussEntFeeStr;
    TextView ussLesnFee;    // 장학수업료
    String ussLesnFeeStr;
    TextView sclsTot;       // 장학금(합계)
    String sclsTotStr;
    TextView totAmt;        // 등록금합계
    String totAmtStr;
    TextView regAmt;        // 납부금액
    String regAmtStr;
    String nonPay;          // 미납금액
    TextView tmpAcct;       // 신한은행 가상계좌
    String tmpAcctStr;

	User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuition_form);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.qr_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

	    user = ((User) getApplication());

        Intent intent = getIntent();
        String token = user.getToken();
        String id = user.getId();
        String year = user.getSchYear();
        String term = user.getSchTerm();

        view = (ImageView) findViewById(R.id.tuition_background);             // 백그라운드
        divide_text = (TextView) findViewById(R.id.tuition_circle_text);// 납부 완료 or 납부 미완료
        circle = (ImageView) findViewById(R.id.tuition_circle);         // 납부 완료 미완료 동그라미
        
        title = (TextView) findViewById(R.id.tuition_title);          // 제목
        entFee = (TextView) findViewById(R.id.tuition_entfee);        // 입학금
        lesnFee = (TextView) findViewById(R.id.tuition_lesnfee);      // 수업료
        coopDegree = (TextView) findViewById(R.id.tuition_coopdegree);// 공동학위
        ussEntFee = (TextView) findViewById(R.id.tuition_ussentfee);  // 장학입학금
        ussLesnFee = (TextView) findViewById(R.id.tuition_usslesnfee);// 장학수업료
        sclsTot = (TextView) findViewById(R.id.tuition_sclstot);      // 장학금(합계)
        totAmt = (TextView) findViewById(R.id.tuition_totamt);        // 등록금합계
        regAmt = (TextView) findViewById(R.id.tuition_regamt);        // 납부금액
        tmpAcct = (TextView) findViewById(R.id.tuition_tmpacct);     // 신한은행 가상계좌

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 가져온 년도, 학기 정보를 갖고 등록금 납부내역 불러오기
                getTuition(token, id, year, term);
                // 쓰레드 안에서 UI 변경 시 필요
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 년 월을 userClass에서 가져올지 아니면 웹에서 가져올지 미정 (계절학기 여부)
                        title.setText(year + "년 " + term + "학기 등록금");
                        entFee.setText(entFeeStr);
                        lesnFee.setText(lesnFeeStr);
                        coopDegree.setText(coopDegreeStr);
                        ussEntFee.setText(ussEntFeeStr);
                        ussLesnFee.setText(ussLesnFeeStr);
                        sclsTot.setText(sclsTotStr);
                        totAmt.setText(totAmtStr);
                        regAmt.setText(regAmtStr);
                        tmpAcct.setText(tmpAcctStr);
                        if(nonPay.equals("0")) {    // 미납금액 0원원
                           view.setImageResource(R.drawable.tuition_background);
                           circle.setImageResource(R.drawable.tuition_paid_circle);
                           divide_text.setText("납부 완료");
                        }
                        else {
                            view.setImageResource(R.drawable.tuition_none_paid_background);
                            circle.setImageResource(R.drawable.tuition_none_paid_circle);
                            divide_text.setText("납부 미완료");
                        }
                    }
                });
            }
        }).start();
    }

    public void getInform(String token, String id) {
        try {
            JSONObject payload = new JSONObject();
            JSONObject parameter = new JSONObject();

            parameter.put("ID", id);
            parameter.put("P_STU_NO", id);
            parameter.put("SCH_REG_STAT", "");
            try {
                payload.put("MAP_ID", "education.urg.URG_common.SELECT_URG_STD");
                payload.put("SYS_ID", "AL");
                payload.put("accessToken", token);
                payload.put("parameter", parameter);
                payload.put("path", "common/selectOne");
                payload.put("programID", "URG_02012_V");
                payload.put("userID", id);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }

            JSONObject response = HttpUrlConnector.getInstance().getResponseWithToken(tuitionURL, payload, token);
            System.out.println(response.toString());

            String name = response.getJSONObject("MAP").get("KOR_NM").toString();
            String number = response.getJSONObject("MAP").get("STU_NO").toString();
            String major = response.getJSONObject("MAP").get("MAJ_NM").toString();
            String schyear = response.getJSONObject("MAP").get("SCHYR").toString();

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    public void getTuition(String token, String id, String year, String term) {
        try {
            JSONObject payload = new JSONObject();
            JSONObject parameter = new JSONObject();

            parameter.put("SCH_YEAR", year);
            parameter.put("SCH_TERM", term);
            parameter.put("ID", id);
            parameter.put("STU_NO", id);
            try {
                payload.put("MAP_ID", "education.urg.URG_02012_V.SELECT");
                payload.put("SYS_ID", "AL");
                payload.put("accessToken", token);
                payload.put("parameter", parameter);
                payload.put("path", "common/selectOne");
                payload.put("programID", "URG_02012_V");
                payload.put("userID", id);

            } catch (JSONException exception) {
                exception.printStackTrace();
            }

	        JSONObject response = HttpUrlConnector.getInstance().getResponseWithToken(tuitionURL, payload, token);

            String tmp = response.getJSONObject("MAP").get("SCH_YEAR").toString();

            entFeeStr = response.getJSONObject("MAP").get("ENT_FEE").toString();
            lesnFeeStr = response.getJSONObject("MAP").get("LESN_FEE").toString();
            coopDegreeStr = response.getJSONObject("MAP").get("COOP_DGR_AMT").toString();
            ussEntFeeStr = response.getJSONObject("MAP").get("USS_ENT_FEE").toString();
            ussLesnFeeStr = response.getJSONObject("MAP").get("USS_LESN_FEE").toString();
            sclsTotStr = response.getJSONObject("MAP").get("SCLS_TOT").toString();
            totAmtStr = response.getJSONObject("MAP").get("TOT_AMT").toString();
            regAmtStr = response.getJSONObject("MAP").get("REG_AMT").toString();
            nonPay = response.getJSONObject("MAP").get("NON_PAY").toString();
            tmpAcctStr = response.getJSONObject("MAP").get("TEMP_ACCT").toString();

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
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
