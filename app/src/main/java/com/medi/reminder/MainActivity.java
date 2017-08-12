package com.medi.reminder;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import com.medi.reminder.databinding.ActivityMainBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements Constants {
    private ActivityMainBinding binding;
    private String mTmpGalleryPicturePath;
    private boolean mFirstImage;
    private boolean mSecondImage;

    public Bitmap textAsBitmap(String text, int textColor) {
//        String fontName = "digital-7";
//        float textSize = 50;
//        Typeface font = Typeface.createFromAsset(this.getAssets(), String.format("%s.ttf", fontName));
//        Paint paint = new Paint();
//        paint.setTextSize(textSize);
//        paint.setTypeface(font);
//        paint.setColor(textColor);
//        paint.setTextAlign(Paint.Align.LEFT);
//        float baseline = -paint.ascent(); // ascent() is negative
//        int width = (int) (paint.measureText(text) + 0.5f); // round
//        int height = (int) (baseline + paint.descent() + 20f);
//        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(image);
//        canvas.drawText(text, 0, baseline, paint);
//        return image;
        Bitmap bitmap = ((BitmapDrawable) getResources()
                .getDrawable(R.mipmap.images)).getBitmap();
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

//        Rect rectangle = new Rect(0, 0, 400, 400);
//        Canvas canvas = new Canvas(mutableBitmap);
//        canvas.drawBitmap(mutableBitmap, null, rectangle, null);
        return mutableBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setClickEvent(new ClickHandler());


    }

    public void onCreateNotification(View view) {
        //CreateCustomNotification(this);
    }

    public Notification createCustomNotification() {


        //Create Intent to launch this Activity on notification click
        Intent intent = new Intent(this, MainActivity.class);

        // Send data to NotificationView Class
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.custom_notification);

        int color = Color.BLACK;
        // Set text on a TextView in the RemoteViews programmatically.
        contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, android.R.color.black));
        contentView.setTextViewText(R.id.tvNotificationTitle, "Android Notification");
        contentView.setImageViewBitmap(R.id.tvNotificationMessage, textAsBitmap("Apply Custom Notification", color));
        contentView.setImageViewBitmap(R.id.tvNotificationMessage2, textAsBitmap("Apply Custom Notification", color));


        Bitmap largeIconBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.images);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setLargeIcon(largeIconBitmap)
                // Sets the ticker text
                .setTicker(getResources().getString(R.string.app_name))
                // Sets the small icon for the ticker
                .setSmallIcon(R.mipmap.ic_launcher)

                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setContent(contentView)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Android Notification"));


        //Build the notification
        Notification notification = builder.build();

        // Use the NotificationManager to show the notification
//        NotificationManager notificationmanager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
//
//        int notificationId = new Random().nextInt(100);
//
//        notificationmanager.notify(notificationId, notification);
        return notification;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == CHOOSESTARTDATETIME) {
            Log.e("date", data.getExtras().getString(DATETIME));
            Log.e("delaytime", data.getExtras().getLong(DELAYTIME) + "");
            binding.textStartTime.setText(data.getExtras().getString(DATETIME));
        } else if (data != null && requestCode == CHOOSEEXPIRYDATETIME) {
            Log.e("date", data.getExtras().getString(DATETIME));
            binding.textExpiryTime.setText(data.getExtras().getString(DATETIME));
        } else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    mTmpGalleryPicturePath = getOriginalImagePath();

                    break;

                case REQUEST_GALLERY:
                    handleGalleryResult(data);
                    break;
            }
        }
    }

    public String getPathFromURI(Uri uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        String path;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }


    private void handleGalleryResult(Intent data) {
        Uri selectedImage = data.getData();
        mTmpGalleryPicturePath = getPathFromURI(selectedImage);
        if (mTmpGalleryPicturePath != null) {

        } else {
            try {
                InputStream is = getContentResolver().openInputStream(selectedImage);
                if (mFirstImage) {
                    binding.imageView1.setImageBitmap(BitmapFactory.decodeStream(is));
                } else {
                    binding.imageView2.setImageBitmap(BitmapFactory.decodeStream(is));

                }
                mTmpGalleryPicturePath = selectedImage.getPath();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /*
        *  Getting the real path of image through camera
        * */
    public synchronized String getOriginalImagePath() {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        int column_index_data = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();

        return cursor.getString(column_index_data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                scheduleNotification(createCustomNotification(), 5000);
                return true;
            case R.id.action_10:
                scheduleNotification(getNotification("10 second delay"), 10000);
                return true;
            case R.id.action_30:
                scheduleNotification(getNotification("30 second delay"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    public void getProfileImage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkPermission())
                    new ImagePickerDialog().show(getSupportFragmentManager(), ImagePickerDialog.class.getSimpleName());

            }
        }, 100);
    }

    private void showPermissionDeniedDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new PermissionDenied().show(getSupportFragmentManager(), PermissionDenied.class.getSimpleName());
            }
        }, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getProfileImage();
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showPermissionDeniedDialog();
                }
            }
        }

    }

    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                return false;
            }
        }
        return true;

    }

    public class ClickHandler {

        public void setStartTime(View view) {
            Intent dateTime = new Intent(MainActivity.this, ChooseDateTimePickerActivity.class);
            dateTime.putExtra(TYPE, CHOOSESTARTDATETIME);
            startActivityForResult(dateTime, CHOOSESTARTDATETIME);


        }

        public void setExpirtyTime(View view) {
            Intent dateTime = new Intent(MainActivity.this, ChooseDateTimePickerActivity.class);
            dateTime.putExtra(TYPE, CHOOSEEXPIRYDATETIME);
            startActivityForResult(dateTime, CHOOSEEXPIRYDATETIME);

        }

        public void chooseImage(View view) {

        }

        public void chooseImage1(View view) {
            mFirstImage = true;
            mSecondImage = false;
            getProfileImage();
        }

        public void chooseImage2(View view) {
            mFirstImage = false;
            mSecondImage = true;
            getProfileImage();
        }
    }


}
