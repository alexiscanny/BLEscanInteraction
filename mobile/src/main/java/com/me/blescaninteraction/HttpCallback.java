// Copyright 2015 Google Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.me.blescaninteraction;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A wrapper around OkHttp's Callback class that runs its methods on the UI thread.
 */
class HttpCallback implements Callback {
  private final Callback delegate;
  private final Handler handler;

  public HttpCallback(Callback delegate) {
    this.delegate = delegate;
    this.handler = new Handler(Looper.getMainLooper());
  }

  /**
   * Called when the request could not be executed due to cancellation, a connectivity problem or
   * timeout. Because networks can fail during an exchange, it is possible that the remote server
   * accepted the request before the failure.
   *
   * @param call
   * @param e
   */
  @Override
  public void onFailure(final Call call, final IOException e) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        delegate.onFailure(call, e);
      }
    });

  }

  /**
   * Called when the HTTP response was successfully returned by the remote server. The callback may
   * proceed to read the response body with {@link Response#body}. The response is still live until
   * its response body is {@linkplain ResponseBody closed}. The recipient of the callback may
   * consume the response body on another thread.
   * <p/>
   * <p>Note that transport-layer success (receiving a HTTP response code, headers and body) does
   * not necessarily indicate application-layer success: {@code response} may still indicate an
   * unhappy HTTP response code like 404 or 500.
   *
   * @param response
   */
  @Override
  public void onResponse(Call call, final Response response) throws IOException {

    /*handler.post(new Runnable() {
      @Override
      public void run() {
        try {
          delegate.onResponse(response);
        } catch (IOException e) {
          delegate.onFailure(null, e);
        }
      }
    });*/

  }
}
