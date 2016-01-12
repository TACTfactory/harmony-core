<@header?interpret />
package ${project_namespace}.harmony.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.base.Strings;
import ${project_namespace}.${project_name?cap_first}Application;
import ${project_namespace}.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

public class ImageUtils {
    /** Image key value for JSON. */
    public static final String IMAGE_KEY_JSON = "file";
    /** Image photo from Google. */
    public static final String GOOGLE_CONTENT_URI = "content://com.google.android.apps.photos.content";
    /** Image photo from local. */
    public static final String CONTENT_URI = "content://";

    /**
     * Get a full system path from a given uri.
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromUri(final Context context, final Uri uri ) {
        String result = "";

        android.database.Cursor cursor = null;

        String url = uri.toString();
        if (url.startsWith(CONTENT_URI)) {
            result = url;
        } else {
            try {
                String[] proj = { MediaStore.Images.Media.DATA };
                cursor = context.getContentResolver().query(uri, proj, null, null, null);

                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();
                result = cursor.getString(index);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        if (Strings.isNullOrEmpty(result)) {
            ImageUtils.openToastNullImage(context);
            result = "";
        }

        return result;
    }

    /**
     * Get a full http url for a given image path.
     * @param context
     * @param path
     * @return
     */
    public static String getImageUrl(Context context, String path) {
        String result = path;

        if (checkPathImageURL(path) && !path.startsWith("http")) {
            int configId = R.string.image_url_prefix_prod;

            if (${project_name?cap_first}Application.DEBUG) {
                configId = R.string.image_url_prefix_dev;
            }

            result = String.format("%s%s", context.getString(configId), path);
        }

        return result;
    }

    /**
     * Check if the image path is not an url from the server.
     * @param path Path of the image
     * @return true if the path is an url
     */
    public static boolean checkPathImageURL(String path) {
        boolean result = true;

        if (path == null || path.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath())) {
            result = false;
        }

        return result;
    }

    /**
     * Get a readable uri for a given image path.
     * @param context
     * @param path
     * @return
     */
    public static String getImageUri(Context context, String path) {
        String result = path;

        if (!checkPathImageURL(path)) {
            result = String.format("file://%s", path);
        } else {
            result = getImageUrl(context, path);
        }

        return result;
    }

    /**
     * Resize an image.
     * @param file The file image
     * @param maxSize Max size for height or width
     * @return Resized image as {@link Bitmap}
     */
    public static Bitmap resize(File file, int maxSize) {
        return resize(file.getAbsolutePath(), maxSize);
    }

    /**
     * Resize an image.
     * @param filePath The absolute image path
     * @param maxSize Max size for height or width
     * @return Resized image as {@link Bitmap}
     */
    public static Bitmap resize(String filePath, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int inWidth = options.outWidth;
        int inHeight = options.outHeight;
        int outWidth = inWidth;
        int outHeight = inHeight;

        if (inWidth > maxSize || inHeight > maxSize) {
            if(inWidth > inHeight){
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth;
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight;
            }
        }

        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath), outWidth, outHeight, false);
    }

    /**
     * Encode an image to Base64.
     * @param fileName Image path
     * @return
     * @throws IOException
     */
    public static String encodeImageToBase64Binary(String fileName) throws IOException {
        Bitmap bitmap = ImageUtils.resize(fileName, 1024);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 85 /*ignored for PNG*/, bos);
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] bitmapData = bos.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bitmapData);

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bytes = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        inputStream.close();

        return encodedString;
    }

    /**
     * Open a toast when the image is null.
     * @param context
     */
    public static void openToastNullImage(final Context context) {
        Toast.makeText(context, context.getText(R.string.image_error_null), Toast.LENGTH_SHORT).show();
    }
}
