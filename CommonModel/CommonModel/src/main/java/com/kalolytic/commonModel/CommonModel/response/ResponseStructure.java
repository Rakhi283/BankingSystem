package com.kalolytic.commonModel.CommonModel.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStructure<T> {

    private String message;
    private String httpstatus;
    private Object data;

}
