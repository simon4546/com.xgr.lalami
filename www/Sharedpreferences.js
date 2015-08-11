    var exec = require('cordova/exec');
    var platform = require('cordova/platform');
    function _Boolean (arg) {
        if(arg==='true')return true;
        return false;
    }
    function _String(arg){
        if(arg){
            return 'true';
        }
        return 'false';
    }
    module.exports = {
        putString: function (key, string, successCallback, errorCallback) {
            if (navigator.connection.type != 'unknown')
                exec(successCallback, errorCallback, 'Sharedpreferences', 'put', [key, string])
            else
                localStorage.setItem(key, string)
        },
        getString: function (key, successCallback, errorCallback) {
            if (navigator.connection.type != 'unknown')
                exec(successCallback, errorCallback, 'Sharedpreferences', 'get', [key])
            else {
                localStorage.getItem(key) ? successCallback(localStorage.getItem(key)) : errorCallback();
            }
        },
        putBoolean: function (key, value, successCallback, errorCallback) {
            if (navigator.connection.type != 'unknown')
                exec(successCallback, errorCallback, 'Sharedpreferences', 'put', [key, _String(value)])
            else
                localStorage.setItem(key, value)
        },
        getBoolean: function (key, successCallback, errorCallback) {
            if (navigator.connection.type != 'unknown'){
                exec(function(val){
                    successCallback(_Boolean(val))
                }, errorCallback, 'Sharedpreferences', 'get', [key])
            }
            else {
                var value = localStorage.getItem(key);
                if (typeof value === 'string') {
                    value = value === 'true' ? true : false;
                }
                if (value != undefined) {
                    successCallback(value);
                }
                else {
                    errorCallback();
                }
            }
        },
        remove: function (key, successCallback, errorCallback) {
            if (navigator.connection.type != 'unknown')
                exec(successCallback, errorCallback, 'Sharedpreferences', 'remove', [key])
            else {
                localStorage.removeItem(key);
                successCallback()
            }
        },
        clear: function (successCallback, errorCallback) {
            exec(successCallback, errorCallback, 'Sharedpreferences', 'clear', ["null"])
        }
    };