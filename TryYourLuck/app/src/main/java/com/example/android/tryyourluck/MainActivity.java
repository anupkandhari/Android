package com.example.android.tryyourluck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.tryyourluck.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int mMoney;
    private Toast toastObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mMoney = savedInstanceState.getInt("Money");
        } else {
            mMoney = 1000;
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        NumberFormat numberFormat =NumberFormat.getCurrencyInstance(new Locale("en","in"));
        binding.moneyValue.setText((String.valueOf(numberFormat.format(mMoney))));
        binding.tryButton.setOnClickListener(this::spinTheWheel);
        binding.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(v);
            }
        });


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Money",mMoney);
    }

    public void spinTheWheel(View view) {
        if(mMoney < 300) {
            binding.outcome.setText("You need at least Rs 300 to play the game!");
        }
        else {
            Random rand = new Random();
            int r = rand.nextInt(13) + 1;
            if (toastObj != null)
                toastObj.cancel();
            toastObj = Toast.makeText(MainActivity.this, "You got " + r, Toast.LENGTH_SHORT);
            toastObj.show();

            if (r < 7) {
                mMoney -= r * 100;
                binding.outcome.setText("You Lost Rs " + r * 100 + "!");
            } else if (r > 7) {
                mMoney += r * 100;
                binding.outcome.setText("You Won Rs "+ r * 100 + "!");
            } else {
                mMoney += 100000;
                binding.outcome.setText("Great! You Won the Jackpot!!");
            }
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en","in"));
            binding.moneyValue.setText(String.valueOf(numberFormat.format(mMoney)));
        }
    }

    public void showInfo(View view) {
        Snackbar.make(binding.outcome, R.string.game, Snackbar.LENGTH_LONG)
                .setAction("Know More", v -> Toast.makeText(MainActivity.this, R.string.expl, Toast.LENGTH_LONG).show())
                .show();
    }
}