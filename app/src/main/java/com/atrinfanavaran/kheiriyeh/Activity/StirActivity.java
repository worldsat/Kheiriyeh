package com.atrinfanavaran.kheiriyeh.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Domain.Rule;
import com.atrinfanavaran.kheiriyeh.Domain.Stir;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static org.apache.commons.text.StringEscapeUtils.unescapeHtml4;

public class StirActivity extends BaseActivity {
    //    RecyclerView recyclerView;
    ProgressBar progressBar;
    private TextView fullText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        toolbar();
        getData();
    }

    private void toolbar() {
        TextView title = findViewById(R.id.titleToolbar);
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(v -> finish());
        title.setText("نحوه فعالیت ");
    }

    private void initView() {
//        recyclerView = findViewById(R.id.View);
        progressBar = findViewById(R.id.progressBarRow);
        fullText = findViewById(R.id.Text);
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);

        controller().GetFromApi2("api/Stir", new CallbackGetString() {
                    @Override
                    public void onSuccess(String resultStr) {
                        Gson gson = new Gson();

                        try {
                            String str = new JSONObject(resultStr).getString("data");
                            Stir response = gson.fromJson(str, Stir.class);
                            if (response.getdescription() == null) return;
                            String escaped = unescapeHtml4("<html><body > <head></head>" + java.net.URLDecoder.decode(response.getdescription()) + "  </body><html>");
//
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                fullText.setText(Html.fromHtml(escaped, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                fullText.setText(Html.fromHtml(escaped));
                            }
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        SnakBar(error);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

    }
}
