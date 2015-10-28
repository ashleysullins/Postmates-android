package com.epicodus.postmatesclone.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.postmatesclone.R;
import com.epicodus.postmatesclone.models.Product;

public class AddProduct extends AppCompatActivity {

    private EditText mCompanyName;
    private EditText mProductName;
    private EditText mProductPrice;
    private Button mNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mCompanyName = (EditText) findViewById(R.id.companyName);
        mProductName = (EditText) findViewById(R.id.productName);
        mProductPrice = (EditText) findViewById(R.id.productPrice);
        mNewProduct = (Button) findViewById(R.id.newProduct);

        mNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = mCompanyName.getText().toString();
                String productName = mProductName.getText().toString();
                int productPrice = Integer.parseInt(mProductPrice.getText().toString());

                Product newProduct = new Product(company, productName, productPrice);
                newProduct.save();
                Intent intent = new Intent(AddProduct.this, CustomerActivity.class);
                startActivity(intent);
            }
        });
    }

}
