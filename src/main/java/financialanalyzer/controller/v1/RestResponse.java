/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.controller.v1;

/**
 *
 * @author pldor
 */
public class RestResponse {
    public static final int CODE_SUCCESS = 100;
    public static final int CODE_FAILURE = 200;
    public static final int CODE_NOTFOUND = 201;
    public static final int CODE_UNAUTHORIZED = 202;
    public static final int CODE_REQUIRESLOGIN = 203;
    public static final int CODE_PARTIALFAILURE = 204;

    
    public int code;
    //public String response;
    public String message;
    public Object object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    //public String getResponse() {
    //    return response;
    //}

    //public void setResponse(String response) {
    //    this.response = response;
    //}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
