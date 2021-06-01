package com.xb.member.feign;

import com.xb.phone.service.PhoneService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @description:手機服務feign客戶端，需要配置在服務的調用方。
 * @author: yyf
 * @version: 1.1
 * @date: xxxx.xx.xx
 */
@Component //泛指組件，当组件不好归类时（不属于@controller、@service等），可以使用此注解将类实例化到容器
@FeignClient("phone-server")
public interface PhoneServiceFeign extends PhoneService {

    /*@PostMapping("phone/api/v1/sendRegCode")
    String sendRegCode(@RequestParam("mobile") String mobile); //@RequestParam 用于将指定的请求参数赋值给方法中的形参。
*/
}
