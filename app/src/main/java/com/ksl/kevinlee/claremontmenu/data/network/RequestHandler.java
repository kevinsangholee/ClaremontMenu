package com.ksl.kevinlee.claremontmenu.data.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kevinlee on 12/30/16.
 */

public class RequestHandler {

    public static final String LOG_TAG = RequestHandler.class.getSimpleName();

    /**
     * Method for adding food using the POST request.
     * @param requestURL Add food url
     * @param postDataParams Key value pairs for the food item.
     * @return not sure yet
     */
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;

        StringBuilder sb = new StringBuilder();
        try {
            // URL initialization
            url = new URL(requestURL);

            // Creating url connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Connection properties
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Create output stream
            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response;
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // Get request with a string array of all foods to search
    public String sendGetRequestWithStringArray(String requestURL, int school_id, ArrayList<String> foodNames) {
        StringBuilder sb = new StringBuilder();
        try {
            StringBuilder newURL = new StringBuilder(requestURL);
            newURL.append(URLEncoder.encode(DBConfig.KEY_FOOD_SCHOOL, "UTF-8"));
            newURL.append("=").append(school_id).append("&");
            boolean first = true;
            for(String food : foodNames) {
                if(first) {
                    first = false;
                } else {
                    newURL.append("&");
                }
                newURL.append(URLEncoder.encode(DBConfig.KEY_FOOD_NAME, "UTF-8"));
                newURL.append("[]=");
                newURL.append(URLEncoder.encode(food, "UTF-8"));
            }
            URL url = new URL(newURL.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequestImageSearch(String requestURL, String image) {
        StringBuilder sb = new StringBuilder();
        try {
            StringBuilder newURL = new StringBuilder(requestURL);
            newURL.append(URLEncoder.encode(image, "UTF-8"));
            URL url = new URL(newURL.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequestBingImageAPI(String image) {
        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("https")
                    .host("api.cognitive.microsoft.com")
                    .addPathSegment("bing")
                    .addPathSegment("v5.0")
                    .addPathSegment("images")
                    .addPathSegment("search")
                    .addQueryParameter("q", image)
                    .addQueryParameter("size", "Medium")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("Ocp-Apim-Subscription-Key", "b3085bfab26b404bac134a76acf64cfb")
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendGetRequestSchoolFood(String requestURL, int school_id) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + school_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // Get request with id (All reviews)
    public String sendGetRequestReviews(String requestURL, int food_id) {
        StringBuilder sb = new StringBuilder();
        try {
            StringBuilder newURL = new StringBuilder(requestURL);
            newURL.append(URLEncoder.encode(DBConfig.KEY_REVIEW_FOOD_ID, "UTF-8"));
            newURL.append("=");
            newURL.append(URLEncoder.encode(String.valueOf(food_id), "UTF-8"));
            URL url = new URL(newURL.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequestSingleFood(String requestURL, int food_id) {
        StringBuilder sb = new StringBuilder();
        try {
            StringBuilder newURL = new StringBuilder(requestURL);
            newURL.append(food_id);
            URL url = new URL(newURL.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequestAspcAll(String requestURL, int school_id) {
        String JSONResponse = "";
        try {
            StringBuilder newURL = new StringBuilder(requestURL);
            newURL.append(URLEncoder.encode(DBConfig.ASPC_DINING_HALL, "UTF-8"));
            switch(school_id) {
                case DBConfig.FRANK:
                    newURL.append(URLEncoder.encode("/frank", "UTF-8"));
                    break;
                case DBConfig.FRARY:
                    newURL.append(URLEncoder.encode("/frary", "UTF-8"));
                    break;
                case DBConfig.OLDENBORG:
                    newURL.append(URLEncoder.encode("/oldenborg", "UTF-8"));
                    break;
                case DBConfig.CMC:
                    newURL.append(URLEncoder.encode("/cmc", "UTF-8"));
                    break;
                case DBConfig.SCRIPPS:
                    newURL.append(URLEncoder.encode("/scripps", "UTF-8"));
                    break;
                case DBConfig.PITZER:
                    newURL.append(URLEncoder.encode("/pitzer", "UTF-8"));
                    break;
                case DBConfig.MUDD:
                    newURL.append(URLEncoder.encode("/mudd", "UTF-8"));
                    break;
                default:
                    break;
            }
            newURL.append(DBConfig.ASPC_AUTH_TOKEN);
            URL url = new URL(newURL.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            JSONResponse = readFromStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONResponse;
    }

    public String sendGetRequestAspcToday(String requestURL, int school_id, int meal) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfWeek = sdf.format(d);
        String shortDay = "";
        switch(dayOfWeek) {
            case "Sunday":
                shortDay = "sun";
                break;
            case "Monday":
                shortDay = "mon";
                break;
            case "Tuesday":
                shortDay = "tue";
                break;
            case "Wednesday":
                shortDay = "wed";
                break;
            case "Thursday":
                shortDay = "thu";
                break;
            case "Friday":
                shortDay = "fri";
                break;
            case "Saturday":
                shortDay = "sat";
                break;
            default:
                break;
        }
        String JSONResponse = "";
        try {
            StringBuilder newURL = new StringBuilder(requestURL);
            newURL.append(URLEncoder.encode(DBConfig.ASPC_DINING_HALL, "UTF-8"));
            switch(school_id) {
                case DBConfig.FRANK:
                    newURL.append(URLEncoder.encode("/frank", "UTF-8"));
                    break;
                case DBConfig.FRARY:
                    newURL.append(URLEncoder.encode("/frary", "UTF-8"));
                    break;
                case DBConfig.OLDENBORG:
                    newURL.append(URLEncoder.encode("/oldenborg", "UTF-8"));
                    break;
                case DBConfig.CMC:
                    newURL.append(URLEncoder.encode("/cmc", "UTF-8"));
                    break;
                case DBConfig.SCRIPPS:
                    newURL.append(URLEncoder.encode("/scripps", "UTF-8"));
                    break;
                case DBConfig.PITZER:
                    newURL.append(URLEncoder.encode("/pitzer", "UTF-8"));
                    break;
                case DBConfig.MUDD:
                    newURL.append(URLEncoder.encode("/mudd", "UTF-8"));
                    break;
                default:
                    break;
            }
            newURL.append("/");
            newURL.append(URLEncoder.encode(DBConfig.ASPC_DAY, "UTF-8"));
            newURL.append("/");
            newURL.append(URLEncoder.encode(shortDay, "UTF-8"));
            newURL.append("/");
            newURL.append(URLEncoder.encode(DBConfig.ASPC_MEAL, "UTF-8"));
            newURL.append("/");
            switch(meal) {
                case DBConfig.BREAKFAST:
                    newURL.append(URLEncoder.encode("breakfast", "UTF-8"));
                    break;
                case DBConfig.LUNCH:
                    newURL.append(URLEncoder.encode("lunch", "UTF-8"));
                    break;
                case DBConfig.DINNER:
                    newURL.append(URLEncoder.encode("dinner", "UTF-8"));
                    break;
                case DBConfig.BRUNCH:
                    newURL.append(URLEncoder.encode("brunch", "UTF-8"));
                    break;
            }
            newURL.append(DBConfig.ASPC_AUTH_TOKEN);
            URL url = new URL(newURL.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            JSONResponse = readFromStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONResponse;
    }

    // JSON parameters for the database
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
