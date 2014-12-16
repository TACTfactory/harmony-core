<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <LinearLayout
        android:orientation="vertical"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content">
        
        <include layout="@layout/toolbar" />

        <LinearLayout
            android:id="@+id/homeLayout"
            android:orientation="vertical"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:padding="4dp">
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="You have generated your first Android project with Harmony."
                android:paddingBottom="16dp"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="Step 1"
                android:textStyle="bold"
                android:textColor="?attr/colorPrimary"  />
            <FrameLayout
                android:layout_width="fill_parent"
                android:layout_height="1dp"
                android:background="@android:color/darker_gray"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="Now you can create your models in the entity package of the project."
                android:paddingBottom="4dp"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="File : /src/com/compagny/android/entity/Post"
                android:textSize="12sp"
                android:textColor="@android:color/darker_gray"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="Package com.compagny.android.entity; public class Post { }"
                android:textSize="12sp"
                android:textColor="@android:color/darker_gray"
                android:paddingBottom="8dp"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="Step 2"
                android:textStyle="bold"
                android:textColor="?attr/colorPrimary"  />
            <FrameLayout
                android:layout_width="fill_parent"
                android:layout_height="1dp"
                android:background="@android:color/darker_gray"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="After creating your models, run Harmony CLI Entities and CRUD view generator."
                android:paddingBottom="4dp"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="script/console orm:generate:entities"
                android:textSize="12sp"
                android:textColor="@android:color/darker_gray"
                android:paddingBottom="8dp"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="script/console orm:generate:crud"
                android:textSize="12sp"
                android:textColor="@android:color/darker_gray"
                android:paddingBottom="8dp"  />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="Step 3"
                android:textStyle="bold"
                android:textColor="?attr/colorPrimary" />
            <FrameLayout
                android:layout_width="fill_parent"
                android:layout_height="1dp"
                android:background="@android:color/darker_gray" />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="Refresh, clean and run your project again..."
                android:paddingBottom="24dp" />
            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="More documentation at http://support.tactfactory.com/harmony/"  />
            <#list entities?values as entity>            
                    <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
            <Button
                android:id="@+id/${entity.name?lower_case}_list_button"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="List all ${entity.name}" />
                    </#if>
            </#list>
        </LinearLayout>
    </LinearLayout>
</ScrollView>

