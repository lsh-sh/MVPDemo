package com.bagevent.http;

import com.bagevent.BuildConfig;
import com.bagevent.app.Constant;
import com.bagevent.utils.LogUtil;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class HttpClient {
    private static HttpClient httpClient;
    public ServiceStore service;

    public static HttpClient getInstance() {
        if (httpClient == null) {
            synchronized (HttpClient.class) {
                if (httpClient == null) {
                    httpClient = new HttpClient();
                }
            }
        }
        return httpClient;
    }

    private HttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.CONNECT_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.WRITE_TIME, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(ServiceApi.BASE_URL)
                .build();

        service = retrofit.create(ServiceStore.class);
    }


    /**
     * 日志输出类
     */
    private Interceptor logInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            if (BuildConfig.DEBUG) {
                LogUtil.e(request.url().toString());
                String body = readResponseStr(response);
                LogUtil.eL(body);
            }
            return response;
        }

        /**
         * 读取Response返回String内容
         * @param response
         * @return
         */
        private String readResponseStr(Response response) {
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            try {
                source.request(Long.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            MediaType contentType = body.contentType();
            Charset charset = Charset.forName("UTF-8");
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String s = null;
            Buffer buffer = source.buffer();
            if (isPlaintext(buffer)) {
                s = buffer.clone().readString(charset);
            }
            return s;
        }

        private boolean isPlaintext(Buffer buffer) {
            try {
                Buffer prefix = new Buffer();
                long byteCount = buffer.size() < 64 ? buffer.size() : 64;
                buffer.copyTo(prefix, 0, byteCount);
                for (int i = 0; i < 16; i++) {
                    if (prefix.exhausted()) {
                        break;
                    }
                    int codePoint = prefix.readUtf8CodePoint();
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false;
                    }
                }
                return true;
            } catch (EOFException e) {
                return false; // Truncated UTF-8 sequence.
            }
        }
    };
}
