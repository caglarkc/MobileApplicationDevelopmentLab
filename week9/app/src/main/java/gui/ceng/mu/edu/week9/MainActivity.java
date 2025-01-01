package gui.ceng.mu.edu.week9;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;



public class MainActivity extends AppCompatActivity {

    EditText txturl;
    ImageView imageView;
    Button button;
    private static int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSSON_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imgView);
        button = findViewById(R.id.btnDownload);
        txturl = findViewById(R.id.txtURL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permisssion = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permisssion != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSSON_STORAGE , REQUEST_EXTERNAL_STORAGE);
                }
                String fileName = "temp.jpg";
                String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
                downloadFile(txturl.getText().toString(),imagePath);
                preview(imagePath);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED&& grantResults[1]==PackageManager.PERMISSION_GRANTED){
            String fileName = "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
            downloadFile(txturl.getText().toString(),imagePath);
            preview(imagePath);
        }
        else {
            Toast.makeText(this,"External storage permission is not granted",Toast.LENGTH_SHORT).show();
        }
    }

    private void preview(String imagePath) {
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        float imageWith = image.getWidth();
        float imageHeight = image.getHeight();
        int rescaledWith = 480;
        int rescaledHeight = (int) ((imageHeight* rescaledWith) / imageWith);
        Bitmap bitmap = Bitmap.createScaledBitmap(image,rescaledWith, rescaledHeight,false);
        imageView.setImageBitmap(bitmap);

    }

    private void downloadFile(String url,String imagePath){
        try {
            URL strurl = new URL(url);
            URLConnection connection = strurl.openConnection();
            connection.connect();

            InputStream inputStream = new BufferedInputStream(strurl.openStream(), 8192);
            OutputStream outputStream = new FileOutputStream(imagePath);

            byte data[] = new byte[1024];
            int count;
            while ((count = inputStream.read(data))!= -1){
                outputStream.write(data,0,count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... urls) {
                try {
                    // Dosya yolu
                    String fileName = "temp.jpg";
                    String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName;

                    // Dosyayı indirin
                    downloadFile(urls[0], imagePath);

                    // Bitmap'i yükleyin
                    Bitmap image = BitmapFactory.decodeFile(imagePath);

                    // Resmi yeniden boyutlandırın
                    float imageWidth = image.getWidth();
                    float imageHeight = image.getHeight();
                    int rescaledWidth = 480;
                    int rescaledHeight = (int) ((imageHeight * rescaledWidth) / imageWidth);

                    return Bitmap.createScaledBitmap(image, rescaledWidth, rescaledHeight, false);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    // Görseli göster
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MainActivity.this, "Image loading failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}