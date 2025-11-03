package com.kalolytic.accountService.AccountService.ExceptionHandler;


import com.kalolytic.commonModel.CommonModel.exception.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignClientsEncoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404 || response.status() == 400) {
            return new ResourceNotFoundException("Customer not found for given ID");
        }

        return defaultDecoder.decode(methodKey, response);
    }
}

