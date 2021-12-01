package com.azhar.laundry.view.dryclean;

import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.azhar.laundry.R;
import com.azhar.laundry.utils.FunctionHelper;
import com.azhar.laundry.viewmodel.AddDataViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import im.delight.android.location.SimpleLocation;

public class DryCleanActivity extends AppCompatActivity {

    public static final String DATA_TITLE = "TITLE";
    int hargaKaos = 8000, hargaCelana = 6000, hargaJaket = 9000, hargaSprei = 65000, hargaKarpet = 200000;
    int itemCount1 = 0, itemCount2 = 0, itemCount3 = 0, itemCount4 = 0, itemCount5 = 0;
    int countKaos, countCelana, countJaket, countSprei, countKarpet, totalItems, totalPrice;
    String strTitle, strCurrentLocation, strCurrentLatLong;
    double strCurrentLatitude;
    double strCurrentLongitude;
    SimpleLocation simpleLocation;
    AddDataViewModel addDataViewModel;
    Button btnCheckout;
    ImageView imageAdd1, imageAdd2, imageAdd3, imageAdd4, imageAdd5,
            imageMinus1, imageMinus2, imageMinus3, imageMinus4, imageMinus5;
    TextView tvTitle, tvInfo, tvJumlahBarang, tvTotalPrice, tvKaos, tvCelana, tvJaket, tvSprei, tvKarpet,
            tvPriceKaos, tvPriceCelana, tvPriceJaket, tvPriceSprei, tvPriceKarpet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry);

        setLocation();
        setStatusbar();
        setInitLayout();
        setDataKaos();
        setDataCelana();
        setDataJaket();
        setDataSprei();
        setDataKarpet();
        setInputData();
        getCurrentLocation();
    }

    private void setLocation() {
        simpleLocation = new SimpleLocation(this);

        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        //get location
        strCurrentLatitude = simpleLocation.getLatitude();
        strCurrentLongitude = simpleLocation.getLongitude();

        //set location lat long
        strCurrentLatLong = strCurrentLatitude + "," + strCurrentLongitude;
    }

    private void setStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setInitLayout() {
        tvTitle = findViewById(R.id.tvTitle);
        tvInfo = findViewById(R.id.tvInfo);

        tvJumlahBarang = findViewById(R.id.tvJumlahBarang);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        tvKaos = findViewById(R.id.tvKaos);
        tvCelana = findViewById(R.id.tvCelana);
        tvJaket = findViewById(R.id.tvJaket);
        tvSprei = findViewById(R.id.tvSprei);
        tvKarpet = findViewById(R.id.tvKarpet);
        tvPriceKaos = findViewById(R.id.tvPriceKaos);
        tvPriceCelana = findViewById(R.id.tvPriceCelana);
        tvPriceJaket = findViewById(R.id.tvPriceJaket);
        tvPriceSprei = findViewById(R.id.tvPriceSprei);
        tvPriceKarpet = findViewById(R.id.tvPriceKarpet);

        imageAdd1 = findViewById(R.id.imageAdd1);
        imageAdd2 = findViewById(R.id.imageAdd2);
        imageAdd3 = findViewById(R.id.imageAdd3);
        imageAdd4 = findViewById(R.id.imageAdd4);
        imageAdd5 = findViewById(R.id.imageAdd5);
        imageMinus1 = findViewById(R.id.imageMinus1);
        imageMinus2 = findViewById(R.id.imageMinus2);
        imageMinus3 = findViewById(R.id.imageMinus3);
        imageMinus4 = findViewById(R.id.imageMinus4);
        imageMinus5 = findViewById(R.id.imageMinus5);

        btnCheckout = findViewById(R.id.btnCheckout);

        strTitle = getIntent().getExtras().getString(DATA_TITLE);
        if (strTitle != null) {
            tvTitle.setText(strTitle);
        }

        addDataViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(this.getApplication()))
                .get(AddDataViewModel.class);

        tvJumlahBarang.setText("0 items");
        tvTotalPrice.setText("Rp 0");
        tvInfo.setText("Cuci kering adalah proses pencucian pakaian menggunakan bahan kimia dan teknik tertentu tanpa air.");
    }

    private void setDataKaos() {
        tvKaos.setText(FunctionHelper.rupiahFormat(hargaKaos));
        imageAdd1.setOnClickListener(v -> {
            itemCount1 = itemCount1 + 1;
            tvPriceKaos.setText(itemCount1);
            countKaos = hargaKaos * itemCount1;
            setTotalPrice();
        });

        imageMinus1.setOnClickListener(v -> {
            if (itemCount1 > 0) {
                itemCount1 = itemCount1 - 1;
                tvPriceKaos.setText(itemCount1);
            }
            countKaos = hargaKaos * itemCount1;
            setTotalPrice();
        });
    }

    private void setDataCelana() {
        tvCelana.setText(FunctionHelper.rupiahFormat(hargaCelana));
        imageAdd2.setOnClickListener(v -> {
            itemCount2 = itemCount2 + 1;
            tvPriceCelana.setText(itemCount2);
            countCelana = hargaCelana * itemCount2;
            setTotalPrice();
        });

        imageMinus2.setOnClickListener(v -> {
            if (itemCount2 > 0) {
                itemCount2 = itemCount2 - 1;
                tvPriceCelana.setText(itemCount2);
            }
            countCelana = hargaCelana * itemCount2;
            setTotalPrice();
        });
    }

    private void setDataJaket() {
        tvJaket.setText(FunctionHelper.rupiahFormat(hargaJaket));
        imageAdd3.setOnClickListener(v -> {
            itemCount3 = itemCount3 + 1;
            tvPriceJaket.setText(itemCount3);
            countJaket = hargaJaket * itemCount3;
            setTotalPrice();
        });

        imageMinus3.setOnClickListener(v -> {
            if (itemCount3 > 0) {
                itemCount3 = itemCount3 - 1;
                tvPriceJaket.setText(itemCount3);
            }
            countJaket = hargaJaket * itemCount3;
            setTotalPrice();
        });
    }

    private void setDataSprei() {
        tvSprei.setText(FunctionHelper.rupiahFormat(hargaSprei));
        imageAdd4.setOnClickListener(v -> {
            itemCount4 = itemCount4 + 1;
            tvPriceSprei.setText(itemCount4);
            countSprei = hargaSprei * itemCount4;
            setTotalPrice();
        });

        imageMinus4.setOnClickListener(v -> {
            if (itemCount4 > 0) {
                itemCount4 = itemCount4 - 1;
                tvPriceSprei.setText(itemCount4);
            }
            countSprei = hargaSprei * itemCount4;
            setTotalPrice();
        });
    }

    private void setDataKarpet() {
        tvKarpet.setText(FunctionHelper.rupiahFormat(hargaKarpet));
        imageAdd5.setOnClickListener(v -> {
            itemCount5 = itemCount5 + 1;
            tvPriceKarpet.setText(itemCount5);
            countKarpet = hargaKarpet * itemCount5;
            setTotalPrice();
        });

        imageMinus5.setOnClickListener(v -> {
            if (itemCount5 > 0) {
                itemCount5 = itemCount5 - 1;
                tvPriceKarpet.setText(itemCount5);
            }
            countKarpet = hargaKarpet * itemCount5;
            setTotalPrice();
        });
    }

    private void setTotalPrice() {
        totalItems = itemCount1 + itemCount2 + itemCount3 + itemCount4 + itemCount5;
        totalPrice = countKaos + countCelana + countJaket + countSprei + countKarpet;

        tvJumlahBarang.setText(totalItems + " items");
        tvTotalPrice.setText(FunctionHelper.rupiahFormat(totalPrice));
    }

    private void getCurrentLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1);
            if (addressList != null && addressList.size() > 0) {
                strCurrentLocation = addressList.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInputData() {
        btnCheckout.setOnClickListener(v -> {
            if (totalItems == 0 || totalPrice == 0) {
                Toast.makeText(DryCleanActivity.this, "Harap pilih jenis barang!", Toast.LENGTH_SHORT).show();
            } else {
                addDataViewModel.addDataLaundry(strTitle, totalItems, strCurrentLocation, totalPrice);
                Toast.makeText(DryCleanActivity.this, "Pesanan Anda sedang diproses, cek di menu riwayat", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}