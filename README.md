# 使用

> 在根build.gradle 中添加
````
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }

    }
}
````
>在module的build.gradle中添加

`implementation 'com.github.superSp:RulerView:v1.5'`

# 示例
````
<com.lsp.RulerView
        android:id="@+id/rulerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        lsp:scaleLimit="10"
        lsp:bgColor="#fcfffc"
        lsp:firstScale="5"
        lsp:isBgRoundRect="true"
        lsp:largeScaleColor="#50b586"
        lsp:largeScaleStroke="5dp"
        lsp:minScale="-100"
        lsp:maxScale="100"
        lsp:midScaleColor="#999999"
        lsp:midScaleStroke="3dp"
        lsp:resultNumColor="#50b586"
        lsp:resultNumTextSize="20sp"
        lsp:rulerHeight="50dp"
        lsp:rulerToResultgap="30dp"
        lsp:scaleCount="10"
        lsp:scaleGap="10dp"
        lsp:scaleNumColor="#666666"
        lsp:scaleNumTextSize="16sp"
        lsp:showScaleResult="true"
        lsp:smallScaleColor="#aaaaaa"
        lsp:smallScaleStroke="2dp"
        lsp:unit="kg"
        lsp:unitColor="#50b586"
        lsp:unitTextSize="10sp" />
````

# 实现思路以及流程
[我的简书地址](http://www.jianshu.com/p/baf143364e04)

# 演示

![演示gif](https://github.com/superSp/RulerView/blob/master/RulerViewGif.gif)

![演示gif2](https://github.com/superSp/RulerView/blob/master/RulerViewGif2.gif)


# 支持设置的属性
````
<attr name="scaleCount" format="integer" />                  <!--相邻2个大刻度之间小刻度的数目-->
<attr name="scaleLimit" format="integer" />                  <!--相邻2个大刻度之间的差值默认为1-->
<attr name="rulerHeight" format="dimension" />               <!--尺子的高度-->
<attr name="rulerToResultgap" format="dimension" />          <!--尺子距离结果的高度-->
<attr name="scaleGap" format="dimension" />                  <!--刻度间距-->
<attr name="firstScale" format="float" />                    <!--默认选中的刻度-->
<attr name="maxScale" format="integer" />                    <!--最大刻度-->
<attr name="minScale" format="integer" />                    <!--最小刻度-->
<attr name="bgColor" format="color" />                       <!--背景色-->
<attr name="smallScaleColor" format="color" />               <!--小刻度的颜色-->
<attr name="midScaleColor" format="color" />                 <!--中刻度的颜色-->
<attr name="largeScaleColor" format="color" />               <!--大刻度的颜色-->
<attr name="scaleNumColor" format="color" />                 <!--刻度数的颜色-->
<attr name="resultNumColor" format="color" />                <!--结果字体的颜色-->
<attr name="unit" format="string" />                         <!--单位-->
<attr name="unitColor" format="color" />                     <!--单位颜色-->
<attr name="smallScaleStroke" format="dimension" />          <!--小刻度的宽度-->
<attr name="midScaleStroke" format="dimension" />            <!--中刻度的宽度-->
<attr name="largeScaleStroke" format="dimension" />          <!--大刻度的宽度-->
<attr name="resultNumTextSize" format="dimension" />         <!--结果字体大小-->
<attr name="scaleNumTextSize" format="dimension" />          <!--刻度字体大小-->
<attr name="unitTextSize" format="dimension" />              <!--单位字体大小-->
<attr name="showScaleResult" format="boolean" />             <!--是否显示结果值-->
<attr name="isBgRoundRect" format="boolean" />               <!--背景是否圆角-->
````
