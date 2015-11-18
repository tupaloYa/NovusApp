package com.newtest.novusapp.fragments.receivedOffer;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.newtest.novusapp.Contents;
import com.newtest.novusapp.QRCodeEncoder;
import com.newtest.novusapp.R;
import com.newtest.novusapp.util.SQLiteHandler;
import com.newtest.novusapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by ROOT-PC on 15.10.2015.
 */

public class DiscountFragment extends Fragment {

    private ImageView offerProductImage;
    private TextView offerProductName;
    private TextView discountText;
    private Button saveCodeBtn;

    public static String myOfferProductName;
    public static String myOfferDiscount;
    public static int offerCnt = 0;

    public static final String MY_OFFER = "myoffer";
    public static final String DISCOUNT = "ratio";
    public static final String NAME="name";
    public static final String OFFER_ID="offer_id";
    public static final String IMG_URL = "img_url";
    public static final String BYTE_ARRAY = "qrbyte";


    public static String offerProdName;
    public static String offerDiscount;
    public static String offerCode;


    JSONArray myOfferJSONArray = null;

    public static String url = "http://192.168.1.250/generate_my_offer.php?email=";


    private SQLiteHandler dbHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.received_offer_discount_fragment, container,
                false);
//        (MainActivity) getActivity().setActionBarTitle(R.string.mycards);

        offerProductImage = (ImageView) getActivity().findViewById(R.id.offerProductImageView);
        offerProductName = (TextView) getActivity().findViewById(R.id.offerProductTextView);
        discountText = (TextView) getActivity().findViewById(R.id.discountTextView);
        saveCodeBtn = (Button) getActivity().findViewById(R.id.saveOfferCodeBtn);

        dbHandler = new SQLiteHandler(getActivity().getApplicationContext());
        HashMap<String,String> userEmail;
        userEmail = dbHandler.getUserEmail();

        String email = userEmail.get("email");
        String urlEmail = url.concat(email);
        Log.d("email request",urlEmail);
        url = urlEmail;

        new GeneratePersonalOfferTask().execute();

        saveCodeBtn = (Button) getActivity().findViewById(R.id.saveOfferCodeBtn);
        saveCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick","btn clicked");
            }
        });


        return rootView;
    }

    private class GeneratePersonalOfferTask extends AsyncTask<Void,Void,HashMap<String,Object>> {

        @Override
        protected HashMap<String,Object> doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = null;

            Log.d("email url", url);
            try {

                jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i("Response: ", "> " + jsonStr);

            if (jsonStr != null) {

                do {
                    try {
                        jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
                        Log.d("jsonStr if err - ",jsonStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                while (jsonStr.equals("error"));

                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

//                     Getting JSON Array node
                        myOfferJSONArray = jsonObj.getJSONArray(MY_OFFER);

//                     looping through
                        for (int i = 0; i < myOfferJSONArray.length(); i++) {

                            JSONObject c = myOfferJSONArray.getJSONObject(i);

                            String discount = c.getString(DISCOUNT);
                            String name = c.getString(NAME);
                            String offerId = c.getString(OFFER_ID);
                            String imgUrl = c.getString(IMG_URL);
                            String discountStr =  getResources().getString(R.string.discount);
                            Log.d("imgUrl=", imgUrl);
                            Double discountPerCent = Double.parseDouble(discount);
                            DecimalFormat df = new DecimalFormat("%");
                            String discountPerCentStr = df.format(discountPerCent);

                            // tmp hashmap for single purchase
                            HashMap<String, Object> discountHMap = new HashMap<String, Object>();

                            // adding each child node to HashMap key => value
                            discountHMap.put(NAME, name);
                            discountHMap.put(DISCOUNT, discountStr.concat(": ").concat(discountPerCentStr));
                            discountHMap.put(OFFER_ID, offerId);
                            discountHMap.put(IMG_URL, imgUrl);

                            Log.d("discount", name);

                            return discountHMap;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

             else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String,Object> discountHMap) {
            String offerName = (String) discountHMap.get(NAME);
            String discount = (String) discountHMap.get(DISCOUNT);
            String offerId = (String) discountHMap.get(OFFER_ID);
            String imgurl = (String) discountHMap.get(IMG_URL);

            Log.d("A name",offerName);
            Log.d("Discount",discount);
            Log.d("Offr id",offerId);
            Log.d("IMG_URL",imgurl);

            offerProdName = offerName;
            offerDiscount = discount;

            TextView offerNameTextView = (TextView ) getActivity().findViewById(R.id.offerProductTextView);
            TextView discountText = (TextView ) getActivity().findViewById(R.id.discountTextView);
            TextView codeText = (TextView ) getActivity().findViewById(R.id.codeTextView);

            offerNameTextView.setText(offerName);
            discountText.setText(discount);
            codeText.setText(offerId);

            offerCode = offerId;
            new DownloadImageTask().execute(discountHMap);

        }
    }

    private class DownloadImageTask extends AsyncTask<HashMap<String, Object>, Void,Bitmap>{

        File qrOutputFile;
        FileOutputStream outputStream;

        @Override
        protected Bitmap doInBackground(HashMap<String, Object>... offerHashMap) {
            Bitmap bitmap = null;
            String imgUrl = (String) offerHashMap[0].get(IMG_URL);
            Log.d("imgUrl",imgUrl);
            try{
                bitmap = downloadUrl(imgUrl);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return bitmap;

        }
        @Override
        protected void onPostExecute(Bitmap result) {

            ImageView iView = (ImageView) getActivity().findViewById(R.id.offerProductImageView);

            /** Displaying the downloaded image */
            iView.setImageBitmap(result);

            //Encode with a QR Code image
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(offerCode,
                    null,
                    Contents.Type.OFFER_CODE,
                    BarcodeFormat.QR_CODE.toString(),
                    256);
            try {

                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                ImageView myImage = (ImageView) getActivity().findViewById(R.id.qrOfferCodeImage);
                myImage.setImageBitmap(bitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                byte[] bitmapdata = bos.toByteArray();

//                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                    //handle case of no SDCARD present
//                    Log.d("oops","no external");
//                } else {
//                    String filename = "myofferqr";
//                    Log.d("file"," creted");
//                    String dir = Environment.getExternalStorageDirectory()+File.separator+"myoffer";
//                    //create folder
//                    File folder = new File(dir); //folder name
//                    folder.mkdirs();
//
//                    Integer ofCnt = offerCnt;
//                    String cnt = ofCnt.toString();
//
//                    String format = ".png";
//                    String filenameQr = filename.concat(cnt).concat(format);
//                    //create file
//                    File outputFile = new File(dir,filenameQr);
//                    FileOutputStream fos = new FileOutputStream(outputFile);
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//
//                    ofCnt++;
//                }

                HashMap<String,Object> personalDiscountHMap = new HashMap<String,Object>();
                personalDiscountHMap.put(NAME,offerProdName);
                personalDiscountHMap.put(DISCOUNT,offerDiscount);
//                personalDiscountHMap.put(BYTE_ARRAY,);


                UploadPersonalOfferToDBTask uploadTask = new UploadPersonalOfferToDBTask();
                uploadTask.execute();

            } catch (WriterException e) {
                e.printStackTrace();
            }  finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private Bitmap downloadUrl(String strUrl) throws IOException{
        Bitmap bitmap=null;
        InputStream iStream = null;
        try{
            URL url = new URL(strUrl);
            /** Creating an http connection to communcate with url */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            /** Connecting to url */
            urlConnection.connect();

            /** Reading data from url */
            iStream = urlConnection.getInputStream();

            /** Creating a bitmap from the stream returned from the url */
            bitmap = BitmapFactory.decodeStream(iStream);

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }finally{
            iStream.close();
        }

        return bitmap;
    }

    private class UploadPersonalOfferToDBTask extends AsyncTask<HashMap<String,Object>,Void,String>{

        @Override
        protected String doInBackground(HashMap<String, Object>... params) {




            return null;
        }
    }

}
