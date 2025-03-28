package com.litblc.shiro.controller.base;

import com.litblc.shiro.common.Result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController extends BaseController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     * 兜底error路由的错误返回
     * @return
     */
    @GetMapping("/error")
    public ResponseEntity<Result<String>> error() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.fail(HttpStatus.NOT_FOUND.value(), "访问错误"));
    }
}
