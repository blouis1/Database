package edu.westga.database;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox = (EditText) findViewById(R.id.productQuantity);
    }

    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        boolean isError = false;
        if (quantityBox.getText().toString().trim().length() == 0 ) {
            isError = true;
            quantityBox.setError("Please enter quantity");
            quantityBox.requestFocus();
        }
        if (productBox.getText().toString().trim().length() == 0 ) {
            isError = true;
            productBox.setError("Please enter product name");
            productBox.requestFocus();
        }
        if (!isError) {
            int quantity =
                    Integer.parseInt(quantityBox.getText().toString());

            Product product =
                    new Product(productBox.getText().toString(), quantity);

            dbHandler.addProduct(product);
            Toast.makeText(this, R.string.record_added_string, Toast.LENGTH_LONG).show();
            idView.setText(R.string.record_added_string);
            productBox.setText("");
            quantityBox.setText("");
        }
    }

    public void lookupProduct (View view) {
        boolean isError = false;
        if (productBox.getText().toString().trim().length() == 0 ) {
            isError = true;
            productBox.setError("Please enter product name");
            productBox.requestFocus();
        }
        if (!isError) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            Product product =
                    dbHandler.findProduct(productBox.getText().toString());

            if (product != null) {
                idView.setText(String.valueOf(product.getID()));

                quantityBox.setText(String.valueOf(product.getQuantity()));
            } else {
                idView.setText(R.string.not_found_string);
                quantityBox.setText("");
            }
        }
    }

    public void removeProduct (View view) {
        boolean isError = false;
        if (productBox.getText().toString().trim().length() == 0 ) {
            isError = true;
            productBox.setError("Please enter product name");
            productBox.requestFocus();
        }
        if (!isError) {
            MyDBHandler dbHandler = new MyDBHandler(this, null,
                    null, 1);

            boolean result = dbHandler.deleteProduct(
                    productBox.getText().toString());

            if (result) {
                idView.setText(R.string.deleted_string);
                productBox.setText("");
                quantityBox.setText("");
            } else {
                idView.setText(R.string.not_found_string);
                quantityBox.setText("");
            }
        }
    }

    public void deleteAll (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        dbHandler.deleteAll();

        idView.setText(R.string.all_deleted_string);
        productBox.setText("");
        quantityBox.setText("");
    }

    public void updateProduct (View view) {
        boolean isError = false;
        if (quantityBox.getText().toString().trim().length() == 0 ) {
            isError = true;
            quantityBox.setError("Please enter quantity");
            quantityBox.requestFocus();
        }
        if (productBox.getText().toString().trim().length() == 0 ) {
            isError = true;
            productBox.setError("Please enter product name");
            productBox.requestFocus();
        }
        if (!isError) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            int rowsAffected = dbHandler.updateProduct(productBox.getText().toString(),
                    Integer.parseInt(quantityBox.getText().toString()));

            if (rowsAffected > 0) {
                //Toast.makeText(this, R.string.record_updated_string, Toast.LENGTH_LONG).show();
                //Snackbar.make(view, "Record Updated", Snackbar.LENGTH_LONG).show();
                idView.setText(R.string.record_updated_string);
                productBox.setText("");
                quantityBox.setText("");
            } else {
                idView.setText(R.string.not_found_string);
                //quantityBox.setText("");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
