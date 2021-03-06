/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.metatron.discovery.common.saml;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomSecurityContextLogoutHandler extends SecurityContextLogoutHandler {

  private boolean clearCookie = true;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    super.logout(request, response, authentication);

    //Logout 시점에 Cookie 삭제
    if(clearCookie){
      String[] cookies = new String[]{"LOGIN_TOKEN", "LOGIN_TOKEN_TYPE", "REFRESH_LOGIN_TOKEN", "LOGIN_USER_ID", "PERMISSION"};

      Arrays.stream(cookies).forEach(cookieName -> {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
      });
    }
  }

  public void setClearCookie(boolean clearCookie) {
    this.clearCookie = clearCookie;
  }
}
