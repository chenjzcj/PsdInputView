密码或者验证码输入框，使用效果如下图所示：

![示意图1](https://upload-images.jianshu.io/upload_images/8903781-a1029a774f2b2c4f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)

![示意图2](https://upload-images.jianshu.io/upload_images/8903781-f1633bc5f1f6e871.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)

maven引用：

    <dependency>
      <groupId>com.felix</groupId>
      <artifactId>psdinputview</artifactId>
      <version>0.0.2</version>
      <type>pom</type>
    </dependency>
    
gradle引用：

    implementation 'com.felix:psdinputview:1.0.2'
    
使用方法：

    <com.felix.psdinputview2.MyPsdInputView
        android:id="@+id/mpiv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:lineColor="@android:color/holo_red_dark"
        app:textColor="@android:color/holo_green_light"
        app:textSize="18sp" />
       
1. lineColor:下划线的颜色
2. textColor:文字颜色
3. textSize:文字尺寸
            
设置输入完成后需要进行的操作：

    final MyPsdInputView mpiv = findViewById(R.id.mpiv);
    mpiv.setTask(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MainActivity.this, mpiv.getPsd(), Toast.LENGTH_LONG).show();
        }
    });